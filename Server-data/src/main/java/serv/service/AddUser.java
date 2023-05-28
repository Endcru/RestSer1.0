package serv.service;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import serv.application.Program;
import serv.model.User;
import serv.model.UserSession;

import java.util.Date;

public class AddUser {
    public static void addUser(@NotNull String username, String email, String password, String role){
        Session session = Program.getSession();
        session.beginTransaction();
        User user = User.builder().username(username).email(email).passwordHash(String.valueOf(password.hashCode())).role(role).build();
        session.save(user);
        session.getTransaction().commit();
    }
}
