package fr.opensensingcity.urlstructurevectorgeneration.Link;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bakerally on 4/10/17.
 */
public  class LinkLibrary {
    static Map<String,LinkGroup> linkGroupMaps = new HashMap<>();
    public static void addLink(String link, Types.Role role){
        Link lnk = LinkFactory.createLink(link,role);
        String key = lnk.toString();
        if (linkGroupMaps.containsKey(key)){
            linkGroupMaps.get(key).addLink(link,role);
        } else {
            LinkGroup linkGroup = new LinkGroup();
            linkGroup.setId(linkGroupMaps.keySet().size()+1);
            linkGroup.addLink(link,role);
            linkGroup.setSampleLink(LinkFactory.createLink(link,role));
            linkGroupMaps.put(lnk.toString(),linkGroup);
        }
    }

    public  static void serialize(String directory) throws FileNotFoundException, UnsupportedEncodingException {
        String filename = directory + "linkgroups";
        PrintWriter writer = new PrintWriter(filename, "UTF-8");

        for (String linkGroupKey:linkGroupMaps.keySet()){
            LinkGroup linkGroup = linkGroupMaps.get(linkGroupKey);
            Link sampleLink = linkGroup.getSampleLink();
            String line = "";
            line = line  + linkGroup.getId() + ",";
            line = line  + sampleLink.getNamespace().replace(",","") + ",";
            line = line  + sampleLink.getSeparator() + ",";
            line = line  + sampleLink.getSeparatorType() + ",";
            line = line  + sampleLink.getQueryParamTemplate() + ",";
            line = line  + linkGroup.getNumSub() + ",";
            line = line  + linkGroup.getNumPred() + ",";
            line = line  + linkGroup.getNumObj() + ",";
            line = line + linkGroup.links.get(0);
            //write to first file here
            //System.out.println(line);
            writer.println(line);

            //linkGroup.serialize(directory);
        }
        writer.close();
    }
}
