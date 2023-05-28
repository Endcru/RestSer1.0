import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class Work {
    private static String getAccessToken(String token) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("refreshToken", token);
        String json = obj.toString();
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/auth/token");
        postMethod.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(postMethod);
        JSONObject JSAnswer = ResponseToJSON.convertHttpResponseToJSON(response);
        if(JSAnswer.isNull("accessToken")){
            return null;
        }else{
            return JSAnswer.getString("accessToken");
        }
    }
    public static void startWork(String token, String login) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вы успешно зашли в систему!");
        String refreshToken = token;
        String accessToken = getAccessToken(refreshToken);
        String inputMessage;
        while (true){
            System.out.println("Для получения информации о текущем пользоввателе напишите Информация.");
            System.out.println("Для выхода из сессии напишите Выход.");
            inputMessage = scanner.nextLine();
            if(inputMessage.equals("Информация")){
                accessToken = getAccessToken(refreshToken);
                JSONObject obj = new JSONObject();
                obj.put("login", login);
                obj.put("accessToken", accessToken);
                String json = obj.toString();
                HttpClient httpClient = HttpClientBuilder.create().build();
                StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
                HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/info/user");
                postMethod.setEntity(requestEntity);
                HttpResponse response = httpClient.execute(postMethod);
                JSONObject JSAnswer = ResponseToJSON.convertHttpResponseToJSON(response);
                System.out.println(JSAnswer.toString());
            } else if (inputMessage.equals("Выход")) {
                System.out.println("Всего вам доброго");
                break;
            }
        }
    }
}
