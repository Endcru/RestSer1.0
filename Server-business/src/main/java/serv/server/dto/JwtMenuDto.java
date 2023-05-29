package serv.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import serv.model.Dish;

import java.util.List;


@Getter
@AllArgsConstructor
public class JwtMenuDto {
    private List<Dish> menu;
}
