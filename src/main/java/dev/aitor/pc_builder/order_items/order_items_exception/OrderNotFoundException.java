package dev.aitor.pc_builder.order_items.order_items_exception;

public class OrderNotFoundException extends OrderException {

    public OrderNotFoundException(String message) {
        super(message);
    }
    
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
