package dev.aitor.pc_builder.order_items;

import dev.aitor.pc_builder.order.Order;
import dev.aitor.pc_builder.product.Product;
import dev.aitor.pc_builder.review.Review;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @OneToOne(mappedBy = "orderItem", cascade = CascadeType.ALL)
    private Review review;

}
