package dev.aitor.pc_builder.order;

import java.util.List;

import dev.aitor.pc_builder.order_items.OrderItemDTO;
import dev.aitor.pc_builder.tracking.TrackingDTO;
import dev.aitor.pc_builder.user.UserDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private String status;
    private float totalPrice;
    private int totalItems;
    private boolean isPaid;
    private UserDTO user;   
    private List<OrderItemDTO> orderItems;    
    private TrackingDTO tracking;
}
