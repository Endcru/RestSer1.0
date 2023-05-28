package serv.service;

import com.sun.istack.NotNull;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import serv.application.Program;
import serv.model.User;
import serv.model.UserSession;

import java.util.Date;

public class AddSession {
    public static void addUserSession(@NotNull int userId, String sessionToken, Date expires){
        Session session = Program.getSession();
        session.beginTransaction();
        UserSession userSession = UserSession.builder().userId(userId).sessionToken(sessionToken).expires(expires).build();
        session.save(userSession);
        session.getTransaction().commit();
    }
}
