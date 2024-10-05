package dev.aitor.pc_builder.favorite;

import lombok.*;

import dev.aitor.pc_builder.product.ProductDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDTO {
    
    private Long id;
    private ProductDTO product;
    private Long user;
}
