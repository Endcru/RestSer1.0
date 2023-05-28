package serv.service;

import com.sun.istack.NotNull;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import serv.application.Program;
import serv.model.User;

public class FindUser {
    public static User findUserByMail(@NotNull String mail){
        Session session = Program.getSession();
        session.getTransaction();
        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("email", mail)).uniqueResult();
        return user;
    }
    public static User findUserByUsername(@NotNull String name){
        Session session = Program.getSession();
        session.getTransaction();
        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("username", name)).uniqueResult();
        return user;
    }
}
