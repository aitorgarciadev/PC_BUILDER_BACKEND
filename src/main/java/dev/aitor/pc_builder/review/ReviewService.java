package dev.aitor.pc_builder.review;

import dev.aitor.pc_builder.category.Category;
import dev.aitor.pc_builder.category.CategoryDTO;
import dev.aitor.pc_builder.order_items.OrderItem;
import dev.aitor.pc_builder.order_items.OrderItemDTO;
import dev.aitor.pc_builder.order_items.OrderItemRepository;
import dev.aitor.pc_builder.product.Product;
import dev.aitor.pc_builder.product.ProductDTO;
import dev.aitor.pc_builder.user.User;
import dev.aitor.pc_builder.user.UserDTO;
import dev.aitor.pc_builder.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        OrderItem orderItem = orderItemRepository.findById(reviewDTO.getOrderItem().getId())
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));

        User user = userRepository.findById(reviewDTO.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setOrderItem(orderItem);
        review.setUser(user);

        Review savedReview = reviewRepository.save(review);
        return mapToDTO(savedReview);
    }

    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return mapToDTO(review);
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        OrderItem orderItem = orderItemRepository.findById(reviewDTO.getOrderItem().getId())
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));

        User user = userRepository.findById(reviewDTO.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        review.setRating(reviewDTO.getRating());
        review.setOrderItem(orderItem);
        review.setUser(user);

        Review updatedReview = reviewRepository.save(review);
        return mapToDTO(updatedReview);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        reviewRepository.delete(review);
    }

    private ReviewDTO mapToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getRating(),
                review.getOrderItem() != null ? mapToOrderItemDTO(review.getOrderItem()) : null,
                review.getUser() != null ? new UserDTO(
                        review.getUser().getId(),
                        review.getUser().getEmail(),
                        review.getUser().getPassword(),
                        null, null, null, null, null, null) : null
        );
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        
        float discountedPrice = product.getPrice();  
    
        if (product.getDiscount() > 0 && product.getDiscount() <= 100) {
            float discountMultiplier = 1 - (product.getDiscount() / 100.0f);
            discountedPrice = product.getPrice() * discountMultiplier;
        }
    
        Category category = product.getCategory();
        CategoryDTO categoryDTO = null;
        if (category != null) {
            categoryDTO = new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getImageHash(), 
                category.isHighlights() 
            );
        }
    
        ProductDTO productDTO = new ProductDTO(
            product.getId(),
            product.getName(),
            product.getImageHash(),
            product.getDescription(),
            product.getPrice(),           
            discountedPrice,             
            product.getStock(), 
            product.getCreatedAt(),       
            categoryDTO,                  
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
    
    

    
}
