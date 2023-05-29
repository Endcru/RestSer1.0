package serv.server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtOrderRequestDto {
    private int userId;
    private int dishId;
    private int orderId;
    private int num;
    private String text;
}
