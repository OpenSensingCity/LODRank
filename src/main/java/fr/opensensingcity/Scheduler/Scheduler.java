package fr.opensensingcity.Scheduler;

import fr.opensensingcity.representationanalysis.RDFRepSummary;
import org.apache.commons.io.FilenameUtils;
import org.apache.jena.sparql.core.Quad;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bakerally on 4/15/17.
 */
public class Scheduler {
    static Map <String,RDFRepSummary> resources = new HashMap<>();
    public static int numResouces = 0;
    public static void processQuad(Quad quad){
        addResource(quad.getSubject().toString());
        addResource(quad.getObject().toString());

    }
    public static void addResource(String resource){
        String ext2 = FilenameUtils.getExtension(resource);
        if (ext2.length() == 0){
            if (!resources.containsKey(resource)){
                resources.put(resource,null);
                numResouces++;
            }
        }

    }

    public static void printResources(){
        for (String resource:resources.keySet()){
            System.out.println(resource);
        }
    }
}
