package fr.opensensingcity.urlstructurevectorgeneration.Link;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bakerally on 3/31/17.
 */
public class Types {
    public enum Role {Subject,Predicate,Object}
    public enum DataType { alpha, numeric, string, date, uri, nul,filename}

    public static DataType getType(String data){
        //check if string is a URL first
        try {
            URL url = new URL(data);
           return DataType.uri;
        } catch (MalformedURLException exception) {
            //for cases where the data is not a URL

            if (data.contains(".")){
                String extPart = data.substring(data.indexOf("."),data.length());
                if (extPart.matches("\\.[1-9]+")){
                    return DataType.string;
                } else {
                    return DataType.filename;
                }
            }

            //check if data is a number
            if (NumberUtils.isNumber(data)){
                return DataType.numeric;
            }

            /*//check if data is a alpha
            if (StringUtils.isAlpha(data)){
                return DataType.alpha;
            }*/

            //check if data is an alphanumeric
            if (StringUtils.isAlphanumeric(data)){
                return DataType.string;
            } else {
                if (StringUtils.isAlphanumeric(data.replace("-","").replace("_",""))){
                    return DataType.string;
                }
            }
        }
        if (data.length() > 0){
            return DataType.string;
        }
        return DataType.nul;
    }
}
