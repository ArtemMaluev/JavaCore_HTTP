import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {

    private static final String API_KEY = "DRd5pxLBoC3pMfWEghvnjOCoN60fGDcSYhppwZn5";
    private static final String ADDRESS = "https://api.nasa.gov/planetary/apod?api_key=";

    public static void main(String[] args) {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        try {
            HttpGet request = new HttpGet(ADDRESS + API_KEY);
            CloseableHttpResponse response = httpClient.execute(request);
            Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

            String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("\n" + body);

            Post informationResponse = jsonToObject(body);
            String fieldUrl = informationResponse.getUrl();
            System.out.println(fieldUrl + "\n");

            request = new HttpGet(fieldUrl);
            response = httpClient.execute(request);
            Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

            body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("\n" + body);

            imageFile(fieldUrl);
            responseFileBody(fieldUrl, body);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Post jsonToObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Post post = gson.fromJson(json, Post.class);
        return post;
    }

    public static void responseFileBody(String fieldUrl, String body) {
        String[] arrayStr = fieldUrl.split("/");
        String fileName = arrayStr[arrayStr.length - 1].replace("jpg", "txt");;

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] bytes = body.getBytes();
            fos.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imageFile(String fieldUrl) {
        String[] arrayStr = fieldUrl.split("/");
        String fileName = arrayStr[arrayStr.length - 1];

        try {
            URL urlImage = new URL(fieldUrl);
            InputStream inputStream = urlImage.openStream();
            OutputStream outputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[2048];

            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

