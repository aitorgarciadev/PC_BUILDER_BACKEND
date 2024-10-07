package dev.aitor.pc_builder.order;

import dev.aitor.pc_builder.order.order_exceptions.OrderNotFoundException;
import dev.aitor.pc_builder.order_items.OrderItem;
import dev.aitor.pc_builder.order_items.OrderItemDTO;
import dev.aitor.pc_builder.order_items.OrderItemRepository;
import dev.aitor.pc_builder.order_items.order_items_exception.OrderItemNotFoundException;
import dev.aitor.pc_builder.product.Product;
import dev.aitor.pc_builder.product.ProductDTO;
import dev.aitor.pc_builder.product.ProductRepository;
import dev.aitor.pc_builder.product.product_exceptions.ProductNotFoundException;
import dev.aitor.pc_builder.tracking.Tracking;
import dev.aitor.pc_builder.tracking.TrackingDTO;
import dev.aitor.pc_builder.tracking.TrackingRepository;
import dev.aitor.pc_builder.user.User;
import dev.aitor.pc_builder.user.UserDTO;
import dev.aitor.pc_builder.user.UserRepository;
import dev.aitor.pc_builder.user.user_exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TrackingRepository trackingRepository;

    public OrderDTO addOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + orderDTO.getUser().getId()));

        Order order = new Order();
        order.setStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setTotalItems(orderDTO.getTotalItems());
        order.setPaid(orderDTO.isPaid());
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);

        if (orderDTO.getOrderItems() != null) {
            List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                    .map(orderItemDTO -> mapToOrderItem(orderItemDTO, savedOrder))
                    .collect(Collectors.toList());
            orderItemRepository.saveAll(orderItems);
            savedOrder.setOrderItems(orderItems);
        }

        if (orderDTO.getTracking() != null) {
            Tracking tracking = new Tracking();
            tracking.setOrder(savedOrder);
            tracking.setTrackingNumber(orderDTO.getTracking().getTrackingNumber());
            trackingRepository.save(tracking);
            savedOrder.setTracking(tracking);
        }

        return mapToDTO(orderRepository.save(savedOrder));
    }

    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        order.setStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setTotalItems(orderDTO.getTotalItems());
        order.setPaid(orderDTO.isPaid());

        if (orderDTO.getOrderItems() != null) {
            List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                    .map(orderItemDTO -> mapToOrderItem(orderItemDTO, order))
                    .collect(Collectors.toList());
            orderItemRepository.saveAll(orderItems);
            order.setOrderItems(orderItems);
        }

        if (orderDTO.getTracking() != null) {
            Tracking tracking = order.getTracking() != null ? order.getTracking() : new Tracking();
            tracking.setOrder(order);
            tracking.setTrackingNumber(orderDTO.getTracking().getTrackingNumber());
            trackingRepository.save(tracking);
            order.setTracking(tracking);
        }

        return mapToDTO(orderRepository.save(order));
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            orderItemRepository.deleteAll(order.getOrderItems());
        }

        if (order.getTracking() != null) {
            trackingRepository.delete(order.getTracking());
        }

        orderRepository.delete(order);
    }

    public List<OrderDTO> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OrderItem mapToOrderItem(OrderItemDTO dto, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setOrder(order);

        if (dto.getProduct() != null) {
            Product product = productRepository.findById(dto.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));
            orderItem.setProduct(product);
        }

        return orderItem;
    }

    private OrderDTO mapToDTO(Order order) {
        List<OrderItemDTO> orderItemsDTO = (order.getOrderItems() != null) ? order.getOrderItems().stream()
                .map(this::mapToOrderItemDTO)
                .collect(Collectors.toList()) : Collections.emptyList();

        TrackingDTO trackingDTO = order.getTracking() != null ? new TrackingDTO(
                order.getTracking().getId(),
                order.getTracking().getTrackingNumber(),
                null) : null;

        UserDTO userDTO = null;
        if (order.getUser() != null) {
            userDTO = new UserDTO(
                    order.getUser().getId(),
                    order.getUser().getEmail(),
                    order.getUser().getPassword(),
                    null, null, null, null, null, null);
        }

       return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getTotalItems(),
                order.isPaid(),
                userDTO,
                // new UserDTO(order.getUser().getId(),
                // order.getUser().getEmail(),
                // order.getUser().getPassword(),
                // null,
                // null, null, null, null, null),
                orderItemsDTO,
                trackingDTO);
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        
        float discountedPrice = product.getPrice();  
    
        if (product.getDiscount() > 0 && product.getDiscount() <= 100) {
            float discountMultiplier = 1 - (product.getDiscount() / 100.0f);
            discountedPrice = product.getPrice() * discountMultiplier;
        }
    
        ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getName(),
                // Optional.ofNullable(product.getImageHash()),
                product.getDescription(),
                product.getPrice(),           
                discountedPrice,             
                product.getStock(),
                product.getCreatedAt(),        
                null,                        
                product.getDiscount()          
        );
    
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getQuantity(),
                null,    
                productDTO, 
                null      
        );
    }
    

    public OrderItemDTO addOrderItemToOrder(Long orderId, OrderItemDTO orderItemDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        Product product = productRepository.findById(orderItemDTO.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDTO.getQuantity());

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        return mapToOrderItemDTO(savedOrderItem);
    }

    public void removeOrderItemFromOrder(Long orderId, Long orderItemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<>());
        }

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found"));

        if (!order.getOrderItems().contains(orderItem)) {
            throw new IllegalStateException("Order does not contain this item");
        }

        order.getOrderItems().remove(orderItem);
        orderItemRepository.delete(orderItem);
        orderRepository.save(order);
    }

}
