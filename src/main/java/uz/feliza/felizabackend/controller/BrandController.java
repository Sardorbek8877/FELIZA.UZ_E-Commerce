package uz.feliza.felizabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Color;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.service.BrandService;
import uz.feliza.felizabackend.service.ColorService;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @PostMapping
    public HttpEntity<?> addBrand(@RequestBody Brand brand){
        ApiResponse apiResponse = brandService.addBrand(brand);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }
}
