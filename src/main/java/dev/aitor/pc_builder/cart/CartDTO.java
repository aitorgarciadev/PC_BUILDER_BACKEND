package dev.aitor.pc_builder.cart;

import java.util.List;

import dev.aitor.pc_builder.cart_item.CartItemDTO;
import dev.aitor.pc_builder.user.UserDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    
    private Long id;
    private UserDTO user;
    private List<CartItemDTO> cartItems;
    private float totalPrice;
}
