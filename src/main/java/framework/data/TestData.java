package framework.data;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by IlieV on 02.07.2015
 */
public class TestData {

    public static String DOCUMENT_LOCATION;

    public static String generateRandomSuffix() {
        return String.format("_%d%s", System.currentTimeMillis(), getRandomString(4));
    }

    public static String getFullDocPath(String fileName) {
        if (fileName != null) {

            String docPath = Paths.get(DOCUMENT_LOCATION.concat("/" + fileName)).toString();
            InputStream inputStream = TestData.class.getResourceAsStream("/TestFiles/" + fileName);
            File outputFolder = new File(DOCUMENT_LOCATION);

            File document = new File(docPath);

            if (!document.exists()) {
                try {
                    outputFolder.mkdirs();
                    OutputStream outputStream = new FileOutputStream(docPath);
                    IOUtils.copy(inputStream, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return docPath;
        }

        return null;
    }

    public static String getRandomIfNeeded(String value, Boolean randomize, String suffix) {
        return (value != null && randomize) ? value + suffix : value;
    }

    private static String getRandomString(int stringLength) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < stringLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
