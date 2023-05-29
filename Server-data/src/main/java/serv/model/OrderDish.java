package serv.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "order_dish")
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_id", nullable = false)
    private  Integer orderId;
    @Column(name = "dish_id", nullable = false)
    private  Integer dishId;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "price", nullable = false)
    private Double price;
}