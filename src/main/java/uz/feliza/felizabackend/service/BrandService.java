package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Category;
import uz.feliza.felizabackend.entity.Color;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.BrandRepository;
import uz.feliza.felizabackend.repository.ColorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    // Method to retrieve a list of all brands.
    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    // Method to retrieve a Brand by its unique identifier (ID).
    public ApiResponse getBrandById(Long id){
        Optional<Brand> optionalBrand = brandRepository.findById(id);

        // Check if a Brand with the given ID exists and return an appropriate response.
        return optionalBrand.map(brand -> new ApiResponse(id + " IDli brend: ", true, brand))
                .orElseGet(() -> new ApiResponse("Brend topilmadi", false));
    }

    public ApiResponse addBrand(Brand brand){
        boolean existsByName = brandRepository.existsByName(brand.getName());
        if (existsByName)
            return new ApiResponse("Bunday nomli brend allaqachon mavjud", false);
        Brand brand1 = new Brand(brand.getName());

        brandRepository.save(brand1);
        return new ApiResponse("Brend yaratildi", true);
    }

    // Method to edit the name of an existing Brand by its ID.
    public ApiResponse editBrand(Long id, Brand brand){
        Optional<Brand> optionalBrand = brandRepository.findById(id);

        // Check if a Brand with the given ID exists.
        if (optionalBrand.isEmpty())
            return new ApiResponse("Brend topilmadi", false);
        Brand editingBrand = optionalBrand.get();
        editingBrand.setName(brand.getName());
        brandRepository.save(editingBrand);
        return new ApiResponse("Brand o'zgartirildi", true);
    }

    // Method to delete a Brand by its ID.
    public ApiResponse deleteBrand(Long id){
        try {
            brandRepository.deleteById(id);
            return new ApiResponse("Brand o'chirildi", true);
        }
        catch (Exception e){
            return new ApiResponse("Brand topilmadi", false);
        }

    }


}
