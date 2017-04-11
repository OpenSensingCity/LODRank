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
        String link = "http://bibliographica.org/entry/BB2682246?a=1&b=2";

        //LinkLibrary.addLink(link, Types.Role.Subject);
        //LinkLibrary.serialize("/home/bakerally/Downloads/testlinks/");
        String cmdStr = "echo "+1000+" > /home  /bakerally/Downloads/testlinks/counter";


        try {
            Process output = null;
            try {
                output = Runtime.getRuntime().exec(cmdStr);
                output.waitFor();
                System.out.println(output.exitValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
            output.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
