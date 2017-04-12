package fr.opensensingcity.urlstructurevectorgeneration.Link;

import eu.wdaqua.lodrank.loader.QuadLoader;
import fr.opensensingcity.Utils;
import fr.opensensingcity.representationanalysis.RepresentationAnalyser;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.core.Quad;

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
        String link = "http://liblists.sussex.ac.uk/items/B4FF9586-0C16-0852-8350-5BE2EEAF15CF#test";

        System.out.println("Types:"+Types.getType("B4FF9586-0C16-0852-8350-5BE2EEAF15CF"));


        System.out.println("AlphaNumeric:"+ StringUtils.isAlphanumeric("B4FF9586-0C16-0852-8350-5BE2EEAF15CF"));
        Link lnk = LinkFactory.createLink(link, Types.Role.Subject);
        System.out.println(lnk.getSeparatorType());
        System.out.println(lnk.toString());

        System.out.println(Utils.getHostPart("http://dbpedia.org/data2/yago/DutchMuayThaiPractitioners.rdf"));



        //LinkLibrary.addLink(link, Types.Role.Subject);
        //LinkLibrary.serialize("/home/bakerally/Downloads/testlinks/");

        /*String cmdStr = "echo "+1000+" > /home/bakerally/counter";
        String [] commands = { "bash", "-c", cmdStr };
        try {
            Runtime.getRuntime().exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //Test QUad
        /*Node g = NodeFactory.createURI("http://bibliographica.org/entry/BB2682246.nt");
        Node s = NodeFactory.createURI("http://bibliographica.org/entry/BB2682246");
        Node p = NodeFactory.createURI("http://xmlns.com/foaf/0.1/isPrimaryTopicOf");
        Node o = NodeFactory.createURI("http://bibliographica.org/entry/BB2682246");
        Quad quad1 = Quad.create(g, s, p, o);

        RepresentationAnalyser.processQuad(quad1);
        System.out.println(RepresentationAnalyser.getString());*/

    }



}
