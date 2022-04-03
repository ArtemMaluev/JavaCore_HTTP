import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        try {
            HttpGet request = new HttpGet(REMOTE_SERVICE_URI);

            CloseableHttpResponse response = httpClient.execute(request);
            //Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

            String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            //System.out.println(body);

            List<Post> listFactsCats = jsonToList(body);
            listFactsCats.stream()
                    .filter(str -> str.getUpvotes() != 0 && str.getUpvotes() > 0)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Post> jsonToList(String json) {
        List<Post> list = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Object obj = parser.parse(json);
            JSONArray array = (JSONArray) obj;
            for (Object object : array) {
                list.add(gson.fromJson(object.toString(), Post.class));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
}
