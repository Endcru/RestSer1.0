package serv.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import serv.server.dto.JwtOrderRequestDto;
import serv.server.dto.JwtResponseOrderDto;

@RequestMapping("/order")
public interface OrderApi {
    @PostMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    JwtResponseOrderDto makeOrder(@RequestBody JwtOrderRequestDto orderRequest);

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    JwtResponseOrderDto addDishToOrder(@RequestBody JwtOrderRequestDto orderRequest);
}
