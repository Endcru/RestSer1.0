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
    private static JSONObject takeAllInformation(String accessToken, String login) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("login", login);
        obj.put("accessToken", accessToken);
        String json = obj.toString();
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/info/user");
        postMethod.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(postMethod);
        return ResponseToJSON.convertHttpResponseToJSON(response);
    }
    public static void startWork(String token, String login) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вы успешно зашли в систему!");
        String refreshToken = token;
        String accessToken = getAccessToken(refreshToken);
        JSONObject userJson = takeAllInformation(accessToken, login);
        int userId = userJson.getInt("id");
        String inputMessage;
        while (true){
            System.out.println("Для получения информации о текущем пользоввателе напишите Информация.");
            System.out.println("Для выхода из сессии напишите Выход.");
            System.out.println("Для показа меню введите Меню.");
            System.out.println("Для того чтобы начать заказ введите Заказ.");
            inputMessage = scanner.nextLine();
            if(inputMessage.equals("Информация")){
                accessToken = getAccessToken(refreshToken);
                JSONObject JSAnswer = takeAllInformation(accessToken, login);
                System.out.println(JSAnswer.toString());
            } else if (inputMessage.equals("Выход")) {
                System.out.println("Всего вам доброго");
                break;
            } else if (inputMessage.equals("Меню")){
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/info/menu");
                HttpResponse response = httpClient.execute(postMethod);
                JSONObject JSAnswer = ResponseToJSON.convertHttpResponseToJSON(response);
                System.out.println(JSAnswer.toString());
            } else if (inputMessage.equals("Заказ")){
                System.out.println("Введите пожелание к заказу");
                inputMessage = scanner.nextLine();
                JSONObject obj = new JSONObject();
                obj.put("userId", userId);
                obj.put("text", inputMessage);
                String json = obj.toString();
                HttpClient httpClient = HttpClientBuilder.create().build();
                StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
                HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/order/start");
                postMethod.setEntity(requestEntity);
                HttpResponse response = httpClient.execute(postMethod);
                JSONObject JSAnswer = ResponseToJSON.convertHttpResponseToJSON(response);
                int orderId = JSAnswer.getInt("orderId");
                while (true){
                    System.out.println("Введите Закончить, чтобы завершить заказ.");
                    System.out.println("Введите Блюдо, чтобы добавить ещё одно блюдо");
                    inputMessage = scanner.nextLine();
                    if(inputMessage.equals("Закончить")){
                        break;
                    } else if(inputMessage.equals("Блюдо")){
                        System.out.println("Введите ID блюда в меню.");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Введите количество блюд, которые вы хотите купить");
                        int num = Integer.parseInt(scanner.nextLine());
                        obj = new JSONObject();
                        obj.put("userId", userId);
                        obj.put("dishId", id);
                        obj.put("orderId", orderId);
                        obj.put("num", num);
                        json = obj.toString();
                        httpClient = HttpClientBuilder.create().build();
                        requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
                        postMethod = new HttpPost("http://localhost:8080/api/RestSer/order/add");
                        postMethod.setEntity(requestEntity);
                        response = httpClient.execute(postMethod);
                        JSAnswer = ResponseToJSON.convertHttpResponseToJSON(response);
                        if (JSAnswer.isNull("error")){
                            System.out.println("Блюдо успешно заказано.");
                        } else if (JSAnswer.getString("error").equals("id")){
                            System.out.println("Блюда с таким id нет в меню");
                        } else if (JSAnswer.getString("error").equals("not")){
                            System.out.println("Данное блюдо недоступно в данный момент");
                        } else if (JSAnswer.getString("error").equals("num")){
                            System.out.println("На данный момент ресторан не способен изготовить столько выбранных вами блюд");
                        }
                    }
                }
            }
        }
    }
}
