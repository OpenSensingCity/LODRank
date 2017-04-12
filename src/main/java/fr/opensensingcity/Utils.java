package fr.opensensingcity;

import java.io.IOException;
import java.net.URI;

/**
 * Created by bakerally on 4/11/17.
 */
public class Utils {
    public static void echoToFile(Object txt,String filename){
        String cmdStr = "echo "+txt+" > "+filename;
        String [] commands = { "bash", "-c", cmdStr };
        try {
            Runtime.getRuntime().exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getHostPart(String link){
        URI uri = URI.create(link);
        String scheme = uri.getScheme();
        String host = uri.getHost();
        return scheme+"://"+host;
    }

    public static String getResource(String link){
        String par1 =  link.substring(link.lastIndexOf("/")+1,link.length());
        if (par1.contains(".")){
            String par2 = par1.substring(0,par1.indexOf("."));
            return par2;
        }
        return par1;
    }

    public static boolean isValidURI(String uri){
        try {
            URI url = URI.create(uri);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }
}
