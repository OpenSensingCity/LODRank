package fr.opensensingcity.urlstructurevectorgeneration.Link;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by bakerally on 3/31/17.
 */
public class LinkFactory {
    public static Link createLink(String link, Types.Role role)  {
        URI uri = null;
        Link lnk;
        try {
            uri = new URI(link);
            lnk = new Link(uri);
            lnk.setRole(role);
            String namespace = null;

            if (uri.getFragment() !=null){
                lnk.setSeparator("#");
                namespace = link.substring(0,link.lastIndexOf("#"));
                lnk.setSeparatorType(Types.getType(uri.getFragment()));

            } else {
                lnk.setSeparator("/");
                namespace = link.substring(0,link.lastIndexOf("/")+1);
                if (uri.getRawQuery()==null){
                    String afterSep = link.substring(link.lastIndexOf("/")+1,link.length());
                    lnk.setSeparatorType(Types.getType(afterSep));
                } else {
                    String afterSep = link.substring(link.lastIndexOf("/"),link.lastIndexOf("?"));
                    lnk.setSeparatorType(Types.getType(afterSep));
                }
            }

            lnk.setNamespace(namespace);

            String queryParamStr = uri.getRawQuery();
            if (queryParamStr!=null){
                String queryParams[] = queryParamStr.split("&");
                for (String queryParam:queryParams){
                    String parts [] = queryParam.split("=");
                    String qKey = parts[0];
                    String qVal = "";
                    if (parts.length > 1){
                        qVal = parts[1];
                    }
                    lnk.addQueryParameter(qKey,Types.getType(qVal));
                }
            }



        } catch (URISyntaxException e) {
           return null;
        }
        return lnk;
    }
}
