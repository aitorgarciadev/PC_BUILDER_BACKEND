package dev.aitor.pc_builder.tracking;

import dev.aitor.pc_builder.order.OrderDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDTO {
    
    private Long id;
    private String trackingNumber;
    private OrderDTO order;
}
