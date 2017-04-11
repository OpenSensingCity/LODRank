package fr.opensensingcity.representationanalysis;

import org.apache.jena.graph.Triple;

/**
 * Created by bakerally on 4/11/17.
 */
public class RDFRepSummary {
    String graphIRI;
    int aTriples;
    int oTriples;
    int gTriples;
    int sTriples;

    public RDFRepSummary(String graphIRI) {
        this.graphIRI = graphIRI;
    }

    public void processTriple(Triple triple) {
        String subject = triple.getSubject().toString();
        String object = triple.getObject().toString();

        if (subject == graphIRI){
            sTriples++;
        } else if (object == graphIRI){
            oTriples++;
        } else {
            //GT Triples
            gTriples++;
        }
        aTriples++;
    }

    public String serialize(){
        String line = "";
        line = graphIRI + "," + aTriples + "," + sTriples + "," + oTriples + "," + gTriples;
        return line;
    }

}
