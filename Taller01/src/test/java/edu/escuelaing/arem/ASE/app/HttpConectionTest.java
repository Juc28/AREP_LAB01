package edu.escuelaing.arem.ASE.app;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpConectionTest {
    @Test
    public void ShouldFormatTheMovieName(){
        HttpConection httpConection = new HttpConection();
        httpConection.movieNameSetter("Fast and Furious");
        String name = httpConection.getMovieName();
        assertEquals("Fast+and+Furious",name);
    }
    @Test
    public void ShouldMkeTheRightUrl(){
        HttpConection httpConection = new HttpConection();
        httpConection.movieNameSetter("Fast" + " " + "and" + " " + "Furious");
        String url = httpConection.fullUrlBuilder();
        assertEquals("http://www.omdbapi.com/?t=Fast+and+Furious&apikey=1f9a7e66", url);
    }

    @Test
    public void ShouldReturnTheResponseString() throws Exception {
        HttpConection httpConection = new HttpConection();
        String response = httpConection.getMessage();
        assertEquals("Fire", response);
    }

}
