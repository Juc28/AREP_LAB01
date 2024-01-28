package edu.escuelaing.arem.ASE.app;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class HttpServer {

    private static HttpConection apiConection = new HttpConection();
    private static ConcurrentHashMap<String,String> cache = new ConcurrentHashMap<>();

    private static HashMap<String,Object> movieData = new HashMap<>();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {

            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean fLine = true;
            String uriS = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (fLine) {
                    fLine = false;
                    uriS = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
            }

            if (uriS.startsWith("/movie?")) {
                outputLine = getMovie(uriS);
            }else if(uriS.startsWith("/moviepost")){
                outputLine = getMovie(uriS);
            }else if(uriS.startsWith("/movie?name=Close") || uriS.startsWith("/moviepost?name=Close")){
                running = false;
                outputLine = getIndexResponse();
            }
            else {
                outputLine = getIndexResponse();
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Retrieves movie information, either from cache or by making an API request.
     *
     * @param uri The URI containing the movie name.
     * @return An HTML response containing the movie information, or an error message if retrieval fails.
     */
    public static String getMovie(String uri) {
        String name = nameFixer(uri.split("=")[1]);
        String message;
        String httpList;

        // Comprobar si el resultado está en caché
        if (cache.containsKey(name.toUpperCase())) {
            message = cache.get(name.toUpperCase());
            movieSetter(message);
            httpList = listMader();
            return htmlResponse(httpList);
        }

        // Si no está en caché, realizar la solicitud a la API
        apiConection.movieNameSetter(name);

        try {
            apiConection.execute();
            message = apiConection.getMessage();
            movieSetter(message);
            httpList = listMader();
            cache.put(name.toUpperCase(), message);
            return htmlResponse(httpList);
        } catch (IOException x) {
            // Manejar el error de manera más específica
            System.err.println("Error al obtener la información del movie: " + x.getMessage());
            return "Error 404";
        }
    }

    public static String getIndexResponse() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Movie Search API-RES</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "       <style>\n" +
                "           body{\n" +
                "               background-image: url(https://media.tenor.com/kGvsHT-BZ6IAAAAi/vn.gif) ;\n" +
                "               background-color: #D8E4F1;\n" +
                "               background-repeat: no-repeat;" +
                "               background-position: 90% 100%;"+
                "               font-family: \"Ubuntu\",monospace;\n" +
                "           }\n" +
                "           h1 {\n" +
                "               margin-top: 40px; \n" +
                "           }\n" +
                "           input {" +
                "                border-radius: 5px; " +
                "                background-color: #F2EAF7}"+
                "           label, input[type=\"text\"],input[type=\"button\"]{\n" +
                "               display: block;\n" +
                "           }"+
                "       </style>" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>ELIGE UNA PELICULA PARA BUSCAR CON GET</h1>\n" +
                "        <form action=\"/movie\">\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"Bride Wars\"><br><br>\n" +
                "            <input type=\"button\" value=\"Buscar\"  onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/movie?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "        <h1>ELIGE UNA PELICULA PARA BUSCAR CON POST</h1>\n" +
                "        <form action=\"/moviepost\">\n" +
                "            <label for=\"postname\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"Anne Hathaway\"><br><br>\n" +
                "            <input type=\"button\" value=\"Buscar\" onclick=\"loadPostMsg(postname)\">\n" +
                "        </form>\n" +
                "        \n" +
                "        <div id=\"postrespmsg\"></div>\n" +
                "        \n" +
                "        <script>\n" +
                "            function loadPostMsg(name){\n" +
                "                let url = \"/moviepost?name=\" + name.value;\n" +
                "\n" +
                "                fetch (url, {method: 'POST'})\n" +
                "                    .then(x => x.text())\n" +
                "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
    }


    /**
     * Replaces percent-encoded spaces in a URI name with actual spaces.
     *
     * @param uriName The URI name to fix.
     * @return The fixed URI name.
     */
    public static String nameFixer(String uriName){
        return uriName.replace("%20", " ");
    }


    /**
     * Parses a JSON string and stores the movie data in a map.
     *
     * @param jsonString The JSON string containing movie data.
     */
    public static void movieSetter(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            System.err.println("Error: jsonString es nulo o vacío.");
            return;
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            Iterator<String> keys = jsonObj.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObj.get(key);
                movieData.put(key, value);
            }
        } catch (JSONException x) {
            // Manejar el error de manera más específica
            System.err.println("Error al parsear el JSON: " + x.getMessage());
        } catch (Exception x) {
            // Manejar otros errores que puedan ocurrir
            System.err.println("Error al procesar la información del movie: " + x.getMessage());
        }
    }


    /**
     * Creates an HTML list of movie information.
     *
     * @return The HTML list as a String.
     */
    public static String listMader() {
        StringBuilder html = new StringBuilder();
        html.append("<ul>\n");

        String title = getStringValue("Title");
        String year = getStringValue("Year");
        String genre = getStringValue("Genre");
        String director = getStringValue("Director");
        String sinopsis = getStringValue("Plot");

        if (title != null) {
            html.append("  <li> Title: ").append(title).append("</li>\n");
        }
        if (year != null) {
            html.append("  <li> Year: ").append(year).append("</li>\n");
        }
        if (genre != null) {
            html.append("  <li> Genre: ").append(genre).append("</li>\n");
        }
        if (director != null) {
            html.append("  <li> Director: ").append(director).append("</li>\n");
        }
        if (sinopsis != null) {
            html.append("  <li> Sinopsis: ").append(sinopsis).append("</li>\n");
        }
        html.append("</ul>\n");
        return html.toString();
    }


    /**
     * Retrieves a string value from the movie data map.
     *
     * @param key The key of the value to retrieve.
     * @return The string value, or null if not found.
     */
    private static String getStringValue(String key) {
        Object value = movieData.get(key);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * Retrieves the movie data map.
     *
     * @return The movie data map.
     */
    public static HashMap<String, Object> getMovieData() {
        return movieData;
    }

    /**
     * Constructs an HTML response with the given content.
     *
     * @param httpList The content to include in the response.
     * @return The complete HTML response as a String.
     */
    public static String htmlResponse(String httpList){
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type:  text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + " <head>\n"
                + "     <meta charset=\"UTF-8\">\n"
                + "     <title>List</title> \n"
                + " </head>\n"
                + " <body>\n"
                + "     " + httpList
                + " </body>\n"
                +"</html>";
    }
}
