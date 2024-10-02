package dev.aitor.pc_builder.category;

import dev.aitor.pc_builder.category.category_exceptions.CategoryException;
import dev.aitor.pc_builder.category.category_exceptions.CategoryNotFoundException;
import dev.aitor.pc_builder.category.category_exceptions.TooManyCategoriesSelectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
        return convertToDTO(category);
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        int selectedCategoriesCount = categoryRepository.countByHighlights(true);

        if (selectedCategoriesCount >= 2 && categoryDTO.isHighlights()) {
            throw new TooManyCategoriesSelectedException("No more than 2 categories can be selected.");
        }

        Category existingCategory = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new CategoryException("Category not found"));

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setImageHash(categoryDTO.getImageHash());
        existingCategory.setHighlights(categoryDTO.isHighlights());

        Category updatedCategory = categoryRepository.save(existingCategory);

        return convertToDTO(updatedCategory);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getImageHash(), category.isHighlights());
    }
}
