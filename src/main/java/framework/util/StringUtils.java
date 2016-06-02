package framework.util;


import com.google.common.io.Resources;
import org.apache.commons.io.Charsets;

import java.io.IOException;
import java.net.URL;

/**
 * Created by IlieV on 7/24/2015.
 * <p>
 * class used to generate strings, like md5 login string.
 */
public class StringUtils {

    public static String getStringFromResourceFile(String file) {
        URL url = Resources.getResource(file);
        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
