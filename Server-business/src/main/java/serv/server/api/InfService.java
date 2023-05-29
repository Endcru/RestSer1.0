package serv.server.api;

import lombok.NonNull;
import serv.model.User;
import serv.server.dto.JwtInfoUserRequestDto;
import serv.server.dto.JwtMenuDto;

public interface InfService {
    User getInformationAbout(@NonNull JwtInfoUserRequestDto authRequest);
    JwtMenuDto getMenu();
}
