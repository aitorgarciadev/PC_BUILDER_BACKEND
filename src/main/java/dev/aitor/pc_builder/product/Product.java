package dev.aitor.pc_builder.product;

import java.util.List;

import dev.aitor.pc_builder.category.Category;
import dev.aitor.pc_builder.order_items.OrderItem;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @NonNull
    private String name;
    @Column(name = "image")
    private String imageHash;
    @NonNull
    private String description;
    private float price;
    private int stock;
   
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @Column(nullable = true)
    private int discount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE) 
    private Date createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

}
