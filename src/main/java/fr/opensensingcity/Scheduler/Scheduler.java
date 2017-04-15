package fr.opensensingcity.Scheduler;

import fr.opensensingcity.Utils;
import fr.opensensingcity.representationanalysis.RDFRepSummary;
import org.apache.commons.io.FilenameUtils;
import org.apache.jena.sparql.core.Quad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by bakerally on 4/15/17.
 */
public class Scheduler {

    static ConcurrentMap <String,List<String>> resources =
            new ConcurrentHashMap<String,List<String>>();
    static Map <String,String> resourcesStatus = new HashMap<>();

    public static int numResouces = 0;
    public static int downloadFail;
    public static int loadFail;

    public static void processQuad(Quad quad){
        addResource(quad.getSubject().toString());
        addResource(quad.getObject().toString());
    }
    public static void addResource(String resource){

        URL url;
        try {
            url = new URL(resource);
            if (!url.getProtocol().contains("http")){
                return;
            }
        } catch (MalformedURLException e) {
            return;
        }


            String key =url.getHost();

            String ext2 = FilenameUtils.getExtension(resource);
            if (ext2.length() == 0){
                if (!resources.containsKey(key)){
                    List <String> resourceList = new ArrayList<String>();
                    resourceList.add(resource);
                    resources.put(key,resourceList);
                    resourcesStatus.put(resource,"Added");
                } else {
                    if (!resourcesStatus.containsKey(resource)){
                        resources.get(key).add(resource);
                        resourcesStatus.put(resource,"Added");
                    }
                }
                numResouces++;
            }

    }

    public static void printResources(){
        for (String resource:resources.keySet()){
            System.out.println(resource);
        }
    }


    public static void serialize(String directory) throws IOException {
        String filename = directory + "restats";
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        String line = "";
        int code = 1;
        for (String cResource:resources.keySet()){

            String crfilename = directory + code;
            Path out = Paths.get(crfilename);

            List<String> rList = resources.get(cResource);
            Files.write(out,rList, Charset.defaultCharset());

            writer.write(cResource + ","+code+","+rList.size() +"\n");
            code++;
        }
        writer.close();
    }
}
