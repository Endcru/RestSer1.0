import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;




public class Main {
    public static void main(String[] args) throws IOException {
        URL url;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Вас приветсвует система Заказов Ресторана.");
            System.out.println("Введите слово Регистрация, если хотите зарегестрироваться.");
            System.out.println("Введите слово Авторизация, если хотите войти в приложение с помощью электорнной почты");
            System.out.println("Введите слово Выход, если хотите выйти из системы");
            String inputMessage = scanner.nextLine();
            if (inputMessage.equals("Авторизация")) {
                String login;
                String password;
                System.out.println("Введите почту пользователя");
                while (true) {
                    inputMessage = scanner.nextLine();
                    if (!inputMessage.contains("@")) {
                        System.out.println("В вашей почте отсутсвует @");
                    } else {
                        login = inputMessage;
                        break;
                    }
                }
                System.out.println("Введите пароль пользователя");
                password = scanner.nextLine();
                JSONObject obj = new JSONObject();
                obj.put("login", login);
                obj.put("password", password);
                String json = obj.toString();
                HttpClient httpClient = HttpClientBuilder.create().build();
                StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
                HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/auth/login");
                postMethod.setEntity(requestEntity);
                HttpResponse response = httpClient.execute(postMethod);
                JSONObject JSAnswer = ResponseToJSON.convertHttpResponseToJSON(response);
                if(JSAnswer.isNull("error")){
                    Work.startWork(JSAnswer.getString("refreshToken"), login);
                } else if (JSAnswer.getString("error").equals("login")) {
                    System.out.println("Аккаунта с такой почтой не существует.");
                } else if (JSAnswer.getString("error").equals("password")) {
                    System.out.println("Вы ввели неправильный пароль.");
                }
            } else if (inputMessage.equals("Регистрация")){
                String email, username, password, role;
                while (true){
                    System.out.println("Введите почту пользователя");
                    while (true) {
                        inputMessage = scanner.nextLine();
                        if (!inputMessage.contains("@")) {
                            System.out.println("В вашей почте отсутсвует @");
                        } else {
                            break;
                        }
                    }
                    JSONObject obj = new JSONObject();
                    obj.put("login", inputMessage);
                    String json = obj.toString();
                    HttpClient httpClient = HttpClientBuilder.create().build();
                    StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
                    HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/reg/login");
                    postMethod.setEntity(requestEntity);
                    HttpResponse response = httpClient.execute(postMethod);
                    JSONObject JSAnswer = ResponseToJSON.convertHttpResponseToJSON(response);
                    if(JSAnswer.isNull("error")){
                        email = inputMessage;
                        break;
                    }
                    System.out.println("Такая почта уже существует в системе.");
                }
                while (true){
                    System.out.println("Введите имя пользователя");
                    inputMessage = scanner.nextLine();
                    JSONObject obj = new JSONObject();
                    obj.put("username", inputMessage);
                    String json = obj.toString();
                    HttpClient httpClient = HttpClientBuilder.create().build();
                    StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
                    HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/reg/name");
                    postMethod.setEntity(requestEntity);
                    HttpResponse response = httpClient.execute(postMethod);
                    JSONObject JSAnswer = ResponseToJSON.convertHttpResponseToJSON(response);
                    if(JSAnswer.isNull("error")){
                        username = inputMessage;
                        break;
                    }
                    System.out.println("Такое имя уже существует в системе.");
                }
                System.out.println("Введите пароль");
                password = scanner.nextLine();
                System.out.println("Введите секретный пароль к вашей роли.\n" +
                        "В обратном случае вам будет присвоена роль пользователя.");
                inputMessage = scanner.nextLine();
                if(inputMessage.equals("12345")){
                    role = "chef";
                } else if(inputMessage.equals("54321")){
                    role = "manager";
                } else{
                    role = "customer";
                }
                JSONObject obj = new JSONObject();
                obj.put("username", username);
                obj.put("login", email);
                obj.put("password", password);
                obj.put("role", role);
                String json = obj.toString();
                HttpClient httpClient = HttpClientBuilder.create().build();
                StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
                HttpPost postMethod = new HttpPost("http://localhost:8080/api/RestSer/reg/make");
                postMethod.setEntity(requestEntity);
                httpClient.execute(postMethod);
                System.out.println("Вы успешно зарегестрировались.");
            } else if(inputMessage.equals("Выход")){
                break;
            }
        }
    }
}