package framework.util;

import framework.data.TestData;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.Set;


public class FileDownloader {

    private WebDriver driver;
    private String downloadPath = TestData.DOCUMENT_LOCATION;
    private String fileName;

    public FileDownloader(WebDriver driverObject) {
        this.driver = driverObject;
    }

    public String downloadFile(WebElement element, String fileName) throws Exception {
        this.fileName = fileName;
        return downloader(element, "href");
    }

    public String downloadImage(WebElement element) throws Exception {
        return downloader(element, "src");
    }

    public String downloader(WebElement element, String attribute) throws Exception {
        //Assuming that getAttribute does some magic to return a fully qualified URL
        String downloadLocation = element.getAttribute(attribute);
        if (downloadLocation.trim().equals("")) {
            throw new Exception("The element you have specified does not link to anything!");
        }

        CookieStore cookieStore = seleniumCookiesToCookieStore();

        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build());

        CloseableHttpClient client = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setSSLSocketFactory(sslsf)
                .build();

        File downloadPathFolder = new File(downloadPath);
        if (!downloadPathFolder.isDirectory()) {
            downloadPathFolder.mkdirs();
        }

        File file = new File(Paths.get(downloadPath + "/" + fileName).toString());

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        downloadLocation = getEncodedQueryParams(downloadLocation);

        HttpGet httpget = new HttpGet(downloadLocation);

        try (CloseableHttpResponse response = client.execute(httpget)) {
            fileOutputStream.write(EntityUtils.toByteArray(response.getEntity()));
            fileOutputStream.close();
        } finally {
            client.close();
        }
        return file.getAbsolutePath();
    }

    private String getEncodedQueryParams(String downloadLocation) {
        try {
            String[] queryParams = downloadLocation.split("\\?")[1].split("&");
            String queryParamEncoded = "";
            for (String queryParam : queryParams) {
                String queryParamName = queryParam.split("=")[0];
                String queryParamValue = URLEncoder.encode(queryParam.split("=")[1], "UTF-8");
                queryParamEncoded = queryParamEncoded + "&" + queryParamName + "=" + queryParamValue;
            }
            downloadLocation = downloadLocation.split("\\?")[0] + "?" + queryParamEncoded;
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("URL could not be encoded", e.getCause());
        }

        return downloadLocation;
    }

    private CookieStore seleniumCookiesToCookieStore() {
        Set<org.openqa.selenium.Cookie> seleniumCookies = driver.manage().getCookies();
        BasicCookieStore cookieStore = new BasicCookieStore();

        for (org.openqa.selenium.Cookie seleniumCookie : seleniumCookies) {
            BasicClientCookie basicClientCookie =
                    new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            basicClientCookie.setDomain(seleniumCookie.getDomain());
            basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
            basicClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie(basicClientCookie);
        }

        return cookieStore;

    }
}



