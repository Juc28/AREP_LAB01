package edu.escuelaing.arem.ASE.app;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpServerTest {
    String jsonTest = "{\"Title\":\"Bride Wars\",\"Year\":\"2009\",\"Rated\":\"PG\",\"Released\":\"09 Sep 2009\",\"Runtime\":\"89 min\",\"Genre\":\"Comedy, Romance\",\"Director\":\"Gary Winick\",\"Writer\":\"Greg DePaul, Casey Wilson, June Diane Raphael\",\"Actors\":\"Kate Hudson, Anne Hathaway, Candice Bergen\",\"Plot\":\"Two best friends become rivals when they schedule their respective weddings on the same day\",\"Language\":\"English\",\"Country\":\"United States, Canada\",\"Awards\":\"1 win & 8 nominations\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTUyNTg2OTUwN15BMl5BanBnXkFtZTgwNzEzMzg5MTI@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"5.5/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"10%\"},{\"Source\":\"Metacritic\",\"Value\":\"24/100\"}],\"Metascore\":\"24\",\"imdbRating\":\"5.5\",\"imdbVotes\":\"114,071\",\"imdbID\":\"tt0901476\",\"Type\":\"movie\",\"DVD\":\"01 Mar 2013\",\"BoxOffice\":\"$58,715,510\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}";
    @Test
    public void ShouldReplacePercentEncodingWithBlankSpaces(){
        HttpServer httpServer = new HttpServer();
        String codificationName = "The%20Chronicles%20of%20Narnia";
        String rightOne = "The Chronicles of Narnia";
        codificationName = httpServer.nameFixer(codificationName);
        assertEquals(rightOne,codificationName);
    }
    @Test
    public void ShouldCorrectlyParseMovieDataFromJSON() {
        HttpServer httpServer = new HttpServer();
        httpServer.movieSetter(jsonTest);
        HashMap<String, Object> map = httpServer.getMovieData();
        assertTrue(map.containsKey("Year"));
    }

    @Test
    public void ShouldGenerateAnHTMLListRepresentationOfMovieData(){
        HttpServer httpServer = new HttpServer();

        httpServer.movieSetter(jsonTest);
        String list = httpServer.listMader();
        assertTrue(list.startsWith("<ul>\n"));
    }
    @Test
    public void ShouldConstructAValidHTMLResponse(){
        HttpServer httpServer = new HttpServer();
        httpServer.movieSetter(jsonTest);
        String list = httpServer.listMader();
        String httpResponse = httpServer.htmlResponse(list);
        assertTrue(httpResponse.startsWith("HTTP/1.1 200 OK"));
    }
    @Test
    public void ShouldRetrieveMovieInformationAndFormatHTMLResponse(){
        HttpServer httpServer = new HttpServer();
        String uri = "/movie?name=Cars";
        String movie = httpServer.getMovie(uri);
        assertTrue(movie.startsWith("HTTP/1.1 200 OK\r\n"));
    }

}
