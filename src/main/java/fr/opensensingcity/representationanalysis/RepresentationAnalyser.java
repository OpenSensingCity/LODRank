package fr.opensensingcity.representationanalysis;

import org.apache.jena.sparql.core.Quad;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bakerally on 4/11/17.
 */
public class RepresentationAnalyser {
    static Map<String,RDFRepSummary> rdfGraphs = new HashMap<>();

    public static void processQuad(Quad quad) {

        RDFRepSummary currentRep;
        String graphIRI = quad.getGraph().toString();
        if (!rdfGraphs.containsKey(graphIRI)){
            currentRep = new RDFRepSummary(graphIRI);
            rdfGraphs.put(graphIRI,currentRep);
        }
        rdfGraphs.get(graphIRI).processTriple(quad.asTriple());

    }

    public static void serialize(String directory) throws FileNotFoundException, UnsupportedEncodingException {
        String filename = directory + "repanalysis";
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        String line = "";
        for (String cgi:rdfGraphs.keySet()){
            line = rdfGraphs.get(cgi).serialize();
            writer.println(line);
        }
        writer.close();
    }

    public static String getString()  {
        String line = "";
        for (String cgi:rdfGraphs.keySet()){
            line = line + rdfGraphs.get(cgi).serialize() + "\n";
        }
        return line;
    }

}
