package serv.server.api;

import org.springframework.lang.NonNull;
import serv.server.dto.JwtOrderRequestDto;
import serv.server.dto.JwtResponseOrderDto;

public interface OrderService {
    public JwtResponseOrderDto makeOrder(@NonNull JwtOrderRequestDto orderRequest);
    public JwtResponseOrderDto addDishToOrder(@NonNull JwtOrderRequestDto orderRequest);
}
