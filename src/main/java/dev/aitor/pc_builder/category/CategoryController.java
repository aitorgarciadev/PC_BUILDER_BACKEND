package dev.aitor.pc_builder.category;

import dev.aitor.pc_builder.profile.profile_exceptions.ProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("${api-endpoint}/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.updateCategory(categoryDTO);
            return ResponseEntity.ok().build();
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

