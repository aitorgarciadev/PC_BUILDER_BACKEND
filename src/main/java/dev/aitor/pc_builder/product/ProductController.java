package dev.aitor.pc_builder.product;

import dev.aitor.pc_builder.product.product_exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("${api-endpoint}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDto) {
        Long categoryId = productDto.getCategory() != null ? productDto.getCategory().getId() : null;

        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(productDto, categoryId));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @PageableDefault(size = 8, sort = {"categoryId", "name"}) Pageable pageable) {
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        try {
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 8, sort = {"categoryId", "name"}) Pageable pageable) {
        Page<ProductDTO> products = productService.getProductsByCategory(categoryId, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<Page<ProductDTO>> getProductsByKeyword(
            @PathVariable String keyword,
            @PageableDefault(size = 8, sort = {"categoryId", "name"}) Pageable pageable) {
        Page<ProductDTO> products = productService.searchProductsByKeyword(keyword, pageable);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
            @Valid @RequestBody ProductDTO productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/discounted")
    public ResponseEntity<List<ProductDTO>> getDiscountedProducts() {
        List<ProductDTO> products = productService.getDiscountedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/new")
    public ResponseEntity<List<ProductDTO>> getNewProducts() {
        List<ProductDTO> products = productService.getNewProducts();
        return ResponseEntity.ok(products);   
    }
}
