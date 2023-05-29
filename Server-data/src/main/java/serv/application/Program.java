package serv.application;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import serv.model.Dish;
import serv.model.Order;
import serv.sql.HibernateSessionFactory;

public class Program {
    public static void main(String[] args){
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Dish dish = Dish.builder().name("Золотая курочка").description("Курица из золота").price(5.67).quantity(10).available(true).build();
        session.save(dish);
        dish = Dish.builder().name("Золотое Яблоко").description("Яблоко из золота").price(3.45).quantity(5).available(true).build();
        session.save(dish);
        session.getTransaction().commit();
    }
    public static Session  getSession(){
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
        return sessionFactory.openSession();
    }
}
