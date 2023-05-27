package serv.server.service;

import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import serv.server.api.UserService;
import serv.server.domain.Role;
import serv.server.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Profile("local")
public class MockUserService implements UserService {
    private final List<User> users = new ArrayList<>();

    public MockUserService() {
        users.addAll(
                List.of(new User("Endcru", "1234", "Никита", "Константинов", Collections.singleton(Role.USER)))
        );
    }
    @Override
    public Optional<User> getByLogin(@NonNull String login) {
        return users.stream().filter(user -> login.equals(user.getLogin())).findFirst();
    }
}
