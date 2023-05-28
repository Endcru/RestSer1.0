package serv.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "session")
@RequiredArgsConstructor
@AllArgsConstructor
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id", nullable = false, unique = true)
    private  Integer userId;
    @Column(name = "session_token", nullable = false)
    private String sessionToken;
    @Column(name = "expires_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;
}
