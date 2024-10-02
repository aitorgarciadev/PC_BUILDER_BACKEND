package dev.aitor.pc_builder.order;

import dev.aitor.pc_builder.order_items.OrderItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api-endpoint}/orders")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO order) {
        OrderDTO createdOrder = orderService.addOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO order) {
        OrderDTO updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

     @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItemDTO> addOrderItemToOrder(@PathVariable Long orderId, @RequestBody OrderItemDTO orderItemDTO) {
        OrderItemDTO addedOrderItem = orderService.addOrderItemToOrder(orderId, orderItemDTO);
        return new ResponseEntity<>(addedOrderItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}/items/{orderItemId}")
    public ResponseEntity<Void> removeOrderItemFromOrder(@PathVariable Long orderId, @PathVariable Long orderItemId) {
        orderService.removeOrderItemFromOrder(orderId, orderItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
