package dev.aitor.pc_builder.order;

import java.util.List;

import dev.aitor.pc_builder.order_item.OrderItem;
import dev.aitor.pc_builder.tracking.Tracking;
import dev.aitor.pc_builder.user.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String status;
    @Column(name = "total_price")
    private float totalPrice;
    @Column(name = "total_items")
    private int totalItems;
    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Tracking tracking;
    
    
}
