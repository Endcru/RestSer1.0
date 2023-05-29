package serv.service;

import com.sun.istack.NotNull;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import serv.application.Program;
import serv.model.Dish;
import serv.model.User;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindDish {
    public static Dish findDish(@NotNull String name){
        Session session = Program.getSession();
        session.getTransaction();
        Criteria criteria = session.createCriteria(Dish.class);
        Dish dish = (Dish) criteria.add(Restrictions.eq("name", name)).uniqueResult();
        return dish;
    }
    public static Dish findDishById(@NotNull int id){
        Session session = Program.getSession();
        session.getTransaction();
        Criteria criteria = session.createCriteria(Dish.class);
        Dish dish = (Dish) criteria.add(Restrictions.eq("id", id)).uniqueResult();
        return dish;
    }
    public static void reduceDish(@NotNull Dish dish, int num){
        Session session = Program.getSession();
        session.beginTransaction();
        dish.setQuantity(dish.getQuantity() - num);
        if(dish.getQuantity() == 0){
            dish.setAvailable(false);
        }
        session.update(dish);
        session.getTransaction().commit();
    }
    public static List<Dish> findAllDish(){
        String hql = "from Dish";
        Session session = Program.getSession();
        Query query = session.createQuery(hql);
        List<Dish> menu = query.getResultList();
        return menu;
    }
}
