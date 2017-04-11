package fr.opensensingcity.urlstructurevectorgeneration.Link;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by bakerally on 3/31/17.
 */
public class Main {
    public static void main(String [] args)  {
        String link = "http://bibliographica.org/entry/BB2682246.n3";



        Link lnk = LinkFactory.createLink(link, Types.Role.Subject);
        //System.out.println(lnk.getSeparatorType());
        System.out.println(lnk.toString());



        //LinkLibrary.addLink(link, Types.Role.Subject);
        //LinkLibrary.serialize("/home/bakerally/Downloads/testlinks/");

        /*String cmdStr = "echo "+1000+" > /home/bakerally/counter";
        String [] commands = { "bash", "-c", cmdStr };
        try {
            Runtime.getRuntime().exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
