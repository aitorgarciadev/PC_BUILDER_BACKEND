package dev.aitor.pc_builder.category;

import dev.aitor.pc_builder.product.Product;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @NonNull
    private String name;
    private String imageHash;

    @Column(name = "highlights", nullable = false)
    @Builder.Default
    private boolean highlights = false;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

}
