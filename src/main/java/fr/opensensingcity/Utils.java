package fr.opensensingcity;

import java.io.IOException;

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
}
