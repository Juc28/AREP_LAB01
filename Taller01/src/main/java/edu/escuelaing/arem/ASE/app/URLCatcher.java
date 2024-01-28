package edu.escuelaing.arem.ASE.app;

import java.net.MalformedURLException;
import java.net.URL;

public class URLCatcher {

    public static void  main(String[] arg) throws MalformedURLException {
        URL myurl = new URL("https://campusvirtual.escuelaing.edu.co:5678/moodle/course/view.php?val=892#grafico");
        System.out.println("Host "+ myurl.getHost());
        System.out.println("Authority "+ myurl.getAuthority());
        System.out.println("Path "+ myurl.getPath());
        System.out.println("Protocol "+ myurl.getProtocol());
        System.out.println("Port "+ myurl.getPort());
        System.out.println("Query "+ myurl.getQuery());
        System.out.println("Ref "+ myurl.getRef());
        System.out.println("File "+ myurl.getFile());


    }

}
