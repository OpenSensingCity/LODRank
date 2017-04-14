package fr.opensensingcity.representationanalysis;

import fr.opensensingcity.Utils;
import org.apache.jena.graph.Triple;

import java.net.URI;

/**
 * Created by bakerally on 4/11/17.
 */
public class RDFRepSummary {
    String partGraphIRI;
    String graphIRI;



    boolean hasExt = false;
    int numPrimaryTopic = 0;
    int numIsPrimaryTopicOf = 0;
    int numTriples = 0;


    public RDFRepSummary(String graphIRI) {
        this.graphIRI = graphIRI;
        if (graphIRI.contains(".")){
            this.hasExt = true;
            this.partGraphIRI = graphIRI.substring(0,graphIRI.lastIndexOf("."));
        }
    }

    public void processTriple(Triple triple) {
        numTriples++;
        String subject = triple.getSubject().toString();
        String object = triple.getObject().toString();
        String predicate = triple.getPredicate().toString().toLowerCase();

        if (predicate.contains("isprimarytopicof") && object.equals(graphIRI)){
            if ( Utils.isValidURI(subject) && Utils.getHostPart(graphIRI).equals(Utils.getHostPart(subject))){
                if (Utils.getResource(subject).equals(Utils.getResource(graphIRI))){
                    numIsPrimaryTopicOf++;
                }
            }
        }
        if (predicate.contains("primarytopic") && subject.equals(graphIRI)) {
            if (Utils.isValidURI(object) && Utils.getHostPart(graphIRI).equals(Utils.getHostPart(object))){
                if (Utils.getResource(object).equals(Utils.getResource(graphIRI))){
                    numPrimaryTopic++;
                }
            }
        }
    }

    public String serialize(){
        String line = "";

        /*System.out.println("ATriples:"+aTriples);
        System.out.println("STriples:"+sTriples);
        System.out.println("OTriples:"+oTriples);
        System.out.println("GTriples:"+gTriples);
        System.out.println("extSTriples:"+extSTriples);
        System.out.println("extOTriples:"+extOTriples);
        System.out.println("extGTriples:"+extGTriples);*/
        line = graphIRI.replace(",","") + "," + numTriples + ","+numPrimaryTopic+","+numIsPrimaryTopicOf;
        return line;
    }
}
