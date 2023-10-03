package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Category;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.BrandRepository;
import uz.feliza.felizabackend.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    // Method to retrieve a list of all categories.
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    // Method to retrieve a category by its unique identifier (ID).
    public ApiResponse getCategoryById(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        // Check if a category with the given ID exists and return an appropriate response.
        return optionalCategory.map(category -> new ApiResponse(id + " IDli kategoriya: ", true, category))
                .orElseGet(() -> new ApiResponse("Kategoriya topilmadi", false));
    }

    // Method to add a new category if a category with the same name doesn't already exist.
    public ApiResponse addCategory(Category category){
        if (categoryRepository.existsByName(category.getName()))
            return new ApiResponse("Bunday kategoriya mavjud!", false);

        // Create and save a new category.
        Category category1 = new Category(category.getName());

        categoryRepository.save(category1);
        return new ApiResponse("Kategoriya yaratildi", true);
    }

    // Method to edit the name of an existing category by its ID.
    public ApiResponse editCategory(Long id, Category category){
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        // Check if a category with the given ID exists.
        if (optionalCategory.isEmpty())
            return new ApiResponse("Kategoriya topilmadi", false);
        Category editingCategory = optionalCategory.get();
        editingCategory.setName(category.getName());
        categoryRepository.save(editingCategory);
        return new ApiResponse("Kategoriya o'zgartirildi", true);
    }

    // Method to delete a category by its ID.
    public ApiResponse deleteCategory(Long id){
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Kategoriya o'chirildi", true);
        }
        catch (Exception e){
            return new ApiResponse("Kategoriya topilmadi", false);
        }

    }
}
