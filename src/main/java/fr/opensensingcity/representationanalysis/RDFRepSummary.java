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
    int aTriples =0;
    int oTriples =0;
    int gTriples = 0 ;
    int sTriples = 0;

    boolean hasExt = false;
    int extSTriples = 0;
    int extOTriples = 0;
    int extGTriples = 0;

    int lnPOTriples =0;
    int lnPGTriples = 0 ;
    int lnPSTriples = 0;

    int lnOTriples =0;
    int lnGTriples = 0 ;
    int lnSTriples = 0;

    int linkTriple = 0;



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
        if ( Utils.isValidURI(subject) && subject.equals(graphIRI)){
            sTriples++;
            occurs = true;
        }
        if (Utils.isValidURI(object) && object.equals(graphIRI)){
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
            if ( Utils.isValidURI(subject) && subject.equals(partGraphIRI)){
                extSTriples++;
                occurs = true;
            }
            if (Utils.isValidURI(object) && object.equals(partGraphIRI)){
                extOTriples++;
                occurs = true;
            }
            if (!occurs){
                //GT Triples
                extGTriples++;
            }
        }

        //checking localname only
        occurs=false;
        if (Utils.isValidURI(subject) && Utils.getResource(subject).equals(Utils.getResource(graphIRI))){
            lnSTriples++;
            occurs = true;
        }
        if (Utils.isValidURI(object) && Utils.getResource(object).equals(Utils.getResource(graphIRI))){
            lnOTriples++;
            occurs = true;
        }
        if (!occurs){
            //GT Triples
            lnGTriples++;
        }

        //checking localname & scheme://Host only
        occurs = false;
        if ( Utils.isValidURI(subject) && Utils.getHostPart(graphIRI).equals(Utils.getHostPart(subject))){
            if (Utils.getResource(subject).equals(Utils.getResource(graphIRI))){
                lnPSTriples++;
                occurs = true;
            }
        }
        if (Utils.isValidURI(object) && Utils.getHostPart(graphIRI).equals(Utils.getHostPart(object))){
            if (Utils.getResource(object).equals(Utils.getResource(graphIRI))){
                lnPOTriples++;
                occurs = true;
            }
        }
        if (!occurs){
            //GT Triples
            lnPGTriples++;
        }


        //check link triple
        String subjectResourceName = Utils.getResource(subject);
        String objectResourceName = Utils.getResource(object);
        String graphResourceName = Utils.getResource(graphIRI);
        if ( Utils.isValidURI(subject) && Utils.isValidURI(object) && (subject.equals(graphIRI) &&
                graphResourceName.equals(objectResourceName) || graphResourceName.equals(subjectResourceName) && object.equals(graphIRI))){
            linkTriple++;
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
        line = graphIRI.replace(",","") + "," + aTriples + "," + sTriples + "," + oTriples + "," + gTriples + "," + extSTriples
        + ","+ extOTriples + "," + extGTriples + "," + lnPSTriples + "," + lnPOTriples + "," + lnPGTriples + "," +lnSTriples
                + "," + lnOTriples + "," + lnGTriples + ","+ linkTriple;
        return line;
    }
}
