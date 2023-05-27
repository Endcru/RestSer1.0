package serv.server.exception;

public class AuthException extends RuntimeException{
    private static final String USER_NOT_FOUND = "Пользователь не найден";
    private static final String INVALID_PASSWORD = "Неправильный пароль";
    private static final String INVALID_TOKEN = "Неправильный токен";

    private AuthException(String message) {super(message);}

    public static AuthException userNotFound(){return new AuthException(USER_NOT_FOUND);}
    public static AuthException invalidPassword(){return new AuthException(INVALID_PASSWORD);}
    public static AuthException invalidToken(){return new AuthException(INVALID_TOKEN);}

}
