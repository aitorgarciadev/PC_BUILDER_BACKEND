package dev.aitor.pc_builder.carts_item;

import dev.aitor.pc_builder.cart.CartDTO;
import dev.aitor.pc_builder.product.ProductDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long id;
    private CartDTO cart;
    private ProductDTO product;
    private int quantity;
    private float price;
}
