package fr.opensensingcity.urlstructurevectorgeneration.Link;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bakerally on 4/10/17.
 */
public class LinkGroup {
    String key;
    int id;
    int numSub;
    int numObj;
    int numPred;
    List<String> links;
    Link sampleLink;

    public LinkGroup(){
        links = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumSub() {
        return numSub;
    }

    public void setNumSub(int numSub) {
        this.numSub = numSub;
    }

    public int getNumObj() {
        return numObj;
    }

    public void setNumObj(int numObj) {
        this.numObj = numObj;
    }

    public int getNumPred() {
        return numPred;
    }

    public void setNumPred(int numPred) {
        this.numPred = numPred;
    }

    public void addLink(String linkStr, Types.Role role){
        if (links.contains(linkStr)){
            //links.add(linkStr);
        }
        if (role == Types.Role.Subject){
            numSub++;
        }
        if (role == Types.Role.Predicate){
            numPred++;
        }
        if (role == Types.Role.Object){
            numObj++;
        }
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    void serialize(String directory) throws FileNotFoundException, UnsupportedEncodingException {
        String fileName = directory + id;
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (String link:links){
            writer.println(link);
        }
        writer.close();
    }

    public Link getSampleLink() {
        return sampleLink;
    }

    public void setSampleLink(Link sampleLink) {
        this.sampleLink = sampleLink;
    }
}
