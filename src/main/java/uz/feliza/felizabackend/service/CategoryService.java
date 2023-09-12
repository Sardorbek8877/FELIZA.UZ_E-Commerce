package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Category;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.BrandRepository;
import uz.feliza.felizabackend.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse addCategory(Category category){
        if (categoryRepository.existsByName(category.getName()))
            return new ApiResponse("Bunday kategoriya mavjud!", false);

        Category category1 = new Category(category.getName());

        categoryRepository.save(category1);
        return new ApiResponse("Kategoriya yaratildi", true);
    }
}
