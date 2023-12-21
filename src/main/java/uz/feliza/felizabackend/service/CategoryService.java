package uz.feliza.felizabackend.service;

import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Category;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.CategoryRepository;

import java.util.ArrayList;
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

    public List<Category> getParentCategories(){
        List<Category> parentCategories = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();
        for (Category category : categoryList){
            if (category.getParentCategoryUZ().isEmpty() && category.getParentCategoryRU().isEmpty()){
                parentCategories.add(category);
            }
        }
        return parentCategories;
    }

    public List<Category> getSubCategories(){
        List<Category> subCategories = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();
        for (Category category : categoryList){
            if (!category.getParentCategoryUZ().isEmpty() && !category.getParentCategoryRU().isEmpty()){
                subCategories.add(category);
            }
        }
        return subCategories;
    }

    // Method to add a new category if a category with the same name doesn't already exist.
    public ApiResponse addCategory(Category category){

        if (categoryRepository.existsByNameUZB(category.getNameUZB())
                || categoryRepository.existsByNameRUS(category.getNameRUS()))
            return new ApiResponse("Bunday kategoriya mavjud!", false);

        //Create new Category
        Category newCategory = new Category();

        if (category.getParentCategoryUZ() != null || category.getParentCategoryRU() != null){

            newCategory.setNameUZB(category.getNameUZB());
            newCategory.setNameRUS(category.getNameRUS());
            newCategory.setParentCategoryUZ(category.getParentCategoryUZ());
            newCategory.setParentCategoryRU(category.getParentCategoryRU());
        }
        else {
            newCategory.setNameUZB(category.getNameUZB());
            newCategory.setNameRUS(category.getNameRUS());
            newCategory.setParentCategoryUZ(null);
            newCategory.setParentCategoryRU(null);
        }

        categoryRepository.save(newCategory);
        return new ApiResponse("Kategoriya yaratildi", true);
    }

    // Method to edit the name of an existing category by its ID.
    public ApiResponse editCategory(Long id, Category category){
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        // Check if a category with the given ID exists.
        if (optionalCategory.isEmpty())
            return new ApiResponse("Kategoriya topilmadi", false);
        Category editingCategory = optionalCategory.get();

        if (categoryRepository.existsByNameUZB(category.getNameUZB())
                || categoryRepository.existsByNameRUS(category.getNameRUS()))
            return new ApiResponse("Bunday kategoriya mavjud!", false);

        editingCategory.setNameUZB(category.getNameUZB());
        editingCategory.setNameRUS(category.getNameRUS());
        editingCategory.setParentCategoryUZ(category.getParentCategoryUZ());
        editingCategory.setParentCategoryRU(category.getParentCategoryRU());
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
