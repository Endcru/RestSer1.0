package serv.server.api;

import lombok.NonNull;
import serv.model.User;
import serv.server.dto.JwtInfoUserRequestDto;

public interface InfService {
    User getInformationAbout(@NonNull JwtInfoUserRequestDto authRequest);
}
