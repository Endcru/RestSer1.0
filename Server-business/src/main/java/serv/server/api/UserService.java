package serv.server.api;

import lombok.NonNull;
import serv.server.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByLogin(@NonNull String login);
}
