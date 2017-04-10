package fr.opensensingcity.urlstructurevectorgeneration.Link;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by bakerally on 3/31/17.
 */
public class Main {
    public static void main(String [] args) throws URISyntaxException, UnsupportedEncodingException, FileNotFoundException {
        String link = "http://bibliographica.org/entry/BB2682246?a=1&b=2";

        LinkLibrary.addLink(link, Types.Role.Subject);
        LinkLibrary.serialize("/home/bakerally/Downloads/testlinks/");
    }
}
