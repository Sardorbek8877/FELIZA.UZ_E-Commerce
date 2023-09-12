package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Color;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.BrandRepository;
import uz.feliza.felizabackend.repository.ColorRepository;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    public ApiResponse addBrand(Brand brand){

        Brand brand1 = new Brand(brand.getName());

        brandRepository.save(brand1);
        return new ApiResponse("Brend yaratildi", true);
    }
}
