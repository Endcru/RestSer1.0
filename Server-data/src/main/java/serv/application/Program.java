package serv.application;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import serv.model.User;
import serv.model.UserSession;
import serv.service.FindUser;
import serv.sql.ConnectionFactory;
import serv.sql.HibernateSessionFactory;

public class Program {
    public static void main(String[] args){
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        UserSession userSession = UserSession.builder().userId(3).sessionToken("sbwebwbweb").expires(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())).build();
        session.save(userSession);
        session.getTransaction().commit();
    }
    public static Session  getSession(){
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        return sessionFactory.openSession();
    }
}
