package uz.feliza.felizabackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Category;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.service.BrandService;
import uz.feliza.felizabackend.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {


    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    // Endpoint to retrieve a list of all categories.
    @GetMapping
    public HttpEntity<?> getAllCategories(){
        List<Category> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(allCategory);
    }

    // Endpoint to retrieve a category by its unique identifier (ID).
    @GetMapping("{id}")
    public HttpEntity<?> getCategoryById(@PathVariable Long id){
        ApiResponse apiResponse = categoryService.getCategoryById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    // Endpoint to add a new category.
    @PostMapping("/add")
    public HttpEntity<?> addCategory( @RequestBody Category category){
        ApiResponse apiResponse = categoryService.addCategory(category);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:404).body(apiResponse);
    }

    // Endpoint to edit the name of an existing category by its ID.
    @PutMapping("{id}")
    public HttpEntity<?> editCategory(@PathVariable Long id, @RequestBody Category category){
        ApiResponse apiResponse = categoryService.editCategory(id, category);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:404).body(apiResponse);
    }

    // Endpoint to delete a category by its ID.
    @DeleteMapping("{id}")
    public HttpEntity<?> deleteCategory(@PathVariable Long id){
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:404).body(apiResponse);
    }
}
