package dev.aitor.pc_builder.order_items;

import dev.aitor.pc_builder.order.OrderDTO;
import dev.aitor.pc_builder.product.ProductDTO;
import dev.aitor.pc_builder.review.ReviewDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long id;
    private int quantity;
    private OrderDTO order;
    private ProductDTO product;
    private ReviewDTO review;

}
