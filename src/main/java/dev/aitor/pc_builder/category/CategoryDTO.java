package dev.aitor.pc_builder.category;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String imageHash;
    private boolean highlights;

    public CategoryDTO(Long id) {
        this.id = id;
    }
    
}
