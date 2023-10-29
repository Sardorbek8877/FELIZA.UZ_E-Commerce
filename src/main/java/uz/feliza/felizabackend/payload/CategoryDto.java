package uz.feliza.felizabackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String nameUZB;

    private String nameRUS;

    private Long parentCategoryId;

    public CategoryDto(String nameUZB, String nameRUS) {
        this.nameUZB = nameUZB;
        this.nameRUS = nameRUS;
    }
}
