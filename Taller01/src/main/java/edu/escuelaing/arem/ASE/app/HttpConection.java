package edu.escuelaing.arem.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HttpConection {

    private static final String USER_AGENT = "Google";
    private static final String API_KEY = "1f9a7e66";
    private static final String GET_URL = "http://www.omdbapi.com/";
    private static  String movieName = "Bride Wars";

    private static String responseString = "Fire" ;
    public HttpConection(){
        movieName = "Bride Wars";
    }

    public static void main(String[] args) throws IOException {
        String url = fullUrlBuilder();
        HttpURLConnection connection = establishConnection(url);
        int responseCode = sendRequest(connection);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String response = getResponseContent(connection);
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }

        System.out.println("GET DONE");
    }

    /**
     * Establishes a connection to the specified URL.
     *
     * @param url The URL to connect to.
     * @return The established HttpURLConnection.
     * @throws IOException If an I/O error occurs while establishing the connection.
     */
    private static HttpURLConnection establishConnection(String url) throws IOException {
        URL targetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        return connection;
    }

    /**
     * Sends a GET request to the specified connection and returns the response code.
     *
     * @param connection The HttpURLConnection to send the request to.
     * @return The HTTP response code.
     * @throws IOException If an I/O error occurs while sending the request.
     */
    private static int sendRequest(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode();
    }

    /**
     * Retrieves the response content from the specified connection.
     *
     * @param connection The HttpURLConnection to retrieve the content from.
     * @return The response content as a String.
     * @throws IOException If an I/O error occurs while retrieving the content.
     */
    private static String getResponseContent(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    /**
     * Executes a GET request to the API endpoint for the specified movie.
     *
     * @throws IOException If an I/O error occurs during the request.
     */
    public void execute() throws IOException {
        URL url = new URL(fullUrlBuilder());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line).append("\n");
                }
                responseString = response.toString();
            }
        } else {
            responseString = "GET request not worked: " + responseCode;
        }
    }

    /**
     * Constructs the complete URL for the API request, including movie name and API key.
     *
     * @return The constructed URL as a String.
     */
    public static String fullUrlBuilder(){
        return GET_URL + "?t=" + movieName + "&apikey=" + API_KEY;
    }

    /**
     * Encodes the given movie name and sets it for use in the API request.
     *
     * @param name The movie name to encode.
     */
    public static void movieNameSetter(String name) {
        try {
            movieName = URLEncoder.encode(name, String.valueOf(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the currently set movie name.
     *
     * @return The encoded movie name.
     */
    public String getMovieName(){
        return movieName;
    }


    /**
     * Retrieves the response message received from the API.
     *
     * @return The response message as a String.
     */
    public static String getMessage(){
        return responseString.toString();
    }

}
