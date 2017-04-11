package fr.opensensingcity.representationanalysis;

import org.apache.jena.graph.Triple;

/**
 * Created by bakerally on 4/11/17.
 */
public class RDFRepSummary {
    String partGraphIRI;
    String graphIRI;
    int aTriples =0;
    int oTriples =0;
    int gTriples = 0 ;
    int sTriples = 0;

    boolean hasExt = false;
    int extSTriples = 0;
    int extOTriples = 0;
    int extGTriples = 0;

    public RDFRepSummary(String graphIRI) {
        this.graphIRI = graphIRI;
        if (graphIRI.contains(".")){
            this.hasExt = true;
            this.partGraphIRI = graphIRI.substring(0,graphIRI.lastIndexOf("."));
        }
    }

    public void processTriple(Triple triple) {
        String subject = triple.getSubject().toString();
        String object = triple.getObject().toString();

        boolean occurs = false;
        if (subject.equals(graphIRI)){
            sTriples++;
            occurs = true;
        }
        if (object.equals(graphIRI)){
            oTriples++;
            occurs = true;
        }
        if (!occurs){
            //GT Triples
            gTriples++;
        }
        aTriples++;

        //if graphIRI contains a file extension
        if (hasExt){
            occurs = false;
            if (subject.equals(partGraphIRI)){
                extSTriples++;
                occurs = true;
            }
            if (object.equals(partGraphIRI)){
                extOTriples++;
                occurs = true;
            }
            if (!occurs){
                //GT Triples
                extGTriples++;
            }
        }

    }

    public String serialize(){
        String line = "";

        /*System.out.println("extSTriples:"+extSTriples);
        System.out.println("extOTriples:"+extOTriples);
        System.out.println("extGTriples:"+extGTriples);*/
        line = graphIRI + "," + aTriples + "," + sTriples + "," + oTriples + "," + gTriples + "," + extSTriples
        + ","+ extOTriples + "," + extGTriples;
        return line;
    }

}
