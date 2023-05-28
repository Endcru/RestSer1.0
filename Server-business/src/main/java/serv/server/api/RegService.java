package serv.server.api;

import lombok.NonNull;
import serv.server.dto.JwtRegistrRequestDto;
import serv.server.dto.JwtRequestDto;
import serv.server.dto.JwtResponseDto;

public interface RegService {
    public JwtResponseDto proveName(@NonNull JwtRegistrRequestDto provNameRequest);

    public JwtResponseDto proveEmail(@NonNull JwtRegistrRequestDto proveEmailRequest);

    public JwtResponseDto register(@NonNull JwtRegistrRequestDto request);
}
