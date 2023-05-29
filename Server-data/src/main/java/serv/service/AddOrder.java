package serv.service;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import serv.application.Program;
import serv.model.Order;
import serv.model.OrderDish;
import serv.model.UserSession;

import java.util.Date;

public class AddOrder {
    public static int addOrder(@NotNull int user_id, String status, String request){
        Session session = Program.getSession();
        session.beginTransaction();
        Order order = Order.builder().userId(user_id).status(status).requests(request).build();
        session.save(order);
        session.getTransaction().commit();
        return order.getId();
    }
    public static int addOrderDish(@NotNull int order_id, int dish_id, int quantity, double price){
        Session session = Program.getSession();
        session.beginTransaction();
        OrderDish orderDish = OrderDish.builder().orderId(order_id).dishId(dish_id).quantity(quantity).price(price).build();
        session.save(orderDish);
        session.getTransaction().commit();
        return orderDish.getId();
    }
}
