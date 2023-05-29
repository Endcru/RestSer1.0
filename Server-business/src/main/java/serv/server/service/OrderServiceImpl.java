package serv.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import serv.model.Dish;
import serv.server.api.OrderService;
import serv.server.dto.JwtOrderRequestDto;
import serv.server.dto.JwtResponseDto;
import serv.server.dto.JwtResponseOrderDto;
import serv.service.AddOrder;
import serv.service.FindDish;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Override
    public JwtResponseOrderDto makeOrder(JwtOrderRequestDto orderRequest) {
        int order_id = AddOrder.addOrder(orderRequest.getUserId(), "ожидается", orderRequest.getText());
        return new JwtResponseOrderDto(null, order_id);
    }

    @Override
    public JwtResponseOrderDto addDishToOrder(JwtOrderRequestDto orderRequest) {
        int order_id = orderRequest.getOrderId();
        Dish dish = FindDish.findDishById(orderRequest.getDishId());
        if(dish == null){
            return new JwtResponseOrderDto("id", order_id);
        }
        if(!dish.getAvailable()){
            return new JwtResponseOrderDto("not", order_id);
        }
        if(dish.getQuantity() < orderRequest.getNum()){
            return new JwtResponseOrderDto("num", order_id);
        }
        FindDish.reduceDish(dish, orderRequest.getNum());
        int orderDishId = AddOrder.addOrderDish(order_id, dish.getId(), orderRequest.getNum(), dish.getPrice());
        return new JwtResponseOrderDto(null, orderDishId);
    }
}
