package serv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import serv.api.OrderApi;
import serv.server.api.OrderService;
import serv.server.dto.JwtOrderRequestDto;
import serv.server.dto.JwtResponseOrderDto;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final OrderService orderService;
    @Override
    public JwtResponseOrderDto makeOrder(JwtOrderRequestDto orderRequest) {
        return orderService.makeOrder(orderRequest);
    }

    @Override
    public JwtResponseOrderDto addDishToOrder(JwtOrderRequestDto orderRequest) {
        return orderService.addDishToOrder(orderRequest);
    }
}
