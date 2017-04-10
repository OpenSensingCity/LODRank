package fr.opensensingcity.urlstructurevectorgeneration.Link;

import java.net.URI;
import java.util.*;

import fr.opensensingcity.urlstructurevectorgeneration.Link.Types.Role;
/**
 * Created by bakerally on 3/31/17.
 */


public class Link {
    URI link;
    String namespace;
    Role role;
    String separator;
    Types.DataType separatorType;
    Map<String,Types.DataType> queryParameters;

    public Link(URI link){
        this.link = link;
        queryParameters =  new HashMap<>();
    }



    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public URI getLink() {
        return link;
    }

    public void setLink(URI link) {
        this.link = link;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void addQueryParameter(String key, Types.DataType dataType){
        queryParameters.put(key,dataType);
    }

    public Map<String, Types.DataType> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, Types.DataType> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public Types.DataType getSeparatorType() {
        return separatorType;
    }

    public void setSeparatorType(Types.DataType separatorType) {
        this.separatorType = separatorType;
    }

    public String toString(){
        String hashVal = null;
        List <String> queryParams = new ArrayList<String>(queryParameters.keySet());
        Collections.sort(queryParams);

        String queryParamsStr = "";

        for (String queryParam:queryParams){
            queryParamsStr = queryParamsStr + queryParam + "=" + queryParameters.get(queryParam);
        }

        hashVal = namespace + separator + separatorType + queryParamsStr ;

        return hashVal;
    }

    public String getQueryParamTemplate(){
        String queryParamTemplate = "";
        for (String queryParam:queryParameters.keySet()){
            queryParamTemplate = queryParamTemplate + queryParam + "=" + queryParameters.get(queryParam);
            queryParamTemplate = queryParamTemplate + "&";
        }
        if (queryParamTemplate.length() > 0){
            return queryParamTemplate.substring(0,queryParamTemplate.length()-1);
        }
        return queryParamTemplate;
    }
}
