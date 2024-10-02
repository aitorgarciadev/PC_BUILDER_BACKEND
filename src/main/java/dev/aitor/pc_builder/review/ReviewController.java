package dev.aitor.pc_builder.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api-endpoint}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.addReview(reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        ReviewDTO reviewDTO = reviewService.getReviewById(id);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
