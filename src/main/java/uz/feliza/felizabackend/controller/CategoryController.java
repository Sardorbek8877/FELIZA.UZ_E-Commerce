package uz.feliza.felizabackend.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Category;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.CategoryDto;
import uz.feliza.felizabackend.service.BrandService;
import uz.feliza.felizabackend.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Endpoint to retrieve a list of all categories.
    /**
     * @RolesAllowed anotationi role enum bo'lsa ham String bo'lsa ham ishlayveradi.
     * Bizning projectda RoleEnum qilingan.
     *  Mijozlar online do'konga kirganda. Ularga kamma kategoriyalardagi narsalar ko'rinib turishi kerak.
     *  Shu sababli bu methodga shu roleni berdim
     */
    @GetMapping
    @RolesAllowed({"CUSTOMER"})
    public HttpEntity<?> getAllCategories(){
        List<Category> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(allCategory);
    }

    // Endpoint to retrieve a category by its unique identifier (ID).

    /**
     * Categorylarni id si bo'yicha ham barcha mijozlar ko'rishi mumkin.
     * Categoryni bosganda tagida baribir uni id si ketadi
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({"CUSTOMER"})
    public HttpEntity<?> getCategoryById(@PathVariable Long id){
        ApiResponse apiResponse = categoryService.getCategoryById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    // Endpoint to add a new category.

    /**
     * Categoriyalarni qo'shishni faqat adminning ruxsati bilan bo'ladi.
     * Shu sabali kategoriyalarni qo'shishni adminga berish kerak
     * @param category
     * @return
     */
    @PostMapping("/add")
    @RolesAllowed({"ADMIN"})
    public HttpEntity<?> addCategory( @RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:404).body(apiResponse);
    }

    // Endpoint to edit the name of an existing category by its ID.

    /**
     * Categoriyani faqat admin o'zgartirishi mumkin
     * @param id
     * @param category
     * @return
     */
    @PutMapping("/update/{id}")
    @RolesAllowed({"ADMIN"})
    public HttpEntity<?> editCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.editCategory(id, categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:404).body(apiResponse);
    }

    // Endpoint to delete a category by its ID.

    /**
     * Categoryni admin o'chiradi
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @RolesAllowed({"ADMIN"})
    public HttpEntity<?> deleteCategory(@PathVariable Long id){
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:404).body(apiResponse);
    }
}
