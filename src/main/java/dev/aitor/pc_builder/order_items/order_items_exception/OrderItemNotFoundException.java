package dev.aitor.pc_builder.order_items.order_items_exception;

public class OrderItemNotFoundException extends OrderException {

    public OrderItemNotFoundException(String message) {
        super(message);
    }
    
    public OrderItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
