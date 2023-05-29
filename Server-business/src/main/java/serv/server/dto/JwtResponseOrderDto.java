package serv.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponseOrderDto {
    private final String type = "Bearer";
    private String error;
    private int orderId;
}
