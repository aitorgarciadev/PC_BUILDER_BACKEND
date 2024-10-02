package dev.aitor.pc_builder.review;

import dev.aitor.pc_builder.order_items.OrderItemDTO;
import dev.aitor.pc_builder.user.UserDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    
    private Long id;
    private int rating;
    private OrderItemDTO orderItem;
    private UserDTO user;
}
