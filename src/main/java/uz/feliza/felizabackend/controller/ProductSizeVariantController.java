package uz.feliza.felizabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.ProductSizeVariant;
import uz.feliza.felizabackend.service.ProductSizeVariantService;


import java.util.List;

@RestController
@RequestMapping("/api/productSizeVariant")
public class ProductSizeVariantController {

    @Autowired
    ProductSizeVariantService productSizeVariantService;

    @GetMapping
    public HttpEntity<?> getAllProductSizeVariant(){
        return ResponseEntity.ok(productSizeVariantService.getAllProductSizeVariant());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProductSizeVariantById(@PathVariable Long id){
        return ResponseEntity.ok(productSizeVariantService.getProductSizeVariantById(id));
    }

    @PostMapping("/{id}")
    public HttpEntity<?> checkProductSizeVariantQuantity(@PathVariable Long id, @RequestBody int quantity){
        return ResponseEntity.ok(productSizeVariantService.checkProductSizeVariantQuantity(id, quantity));
    }

//    @PostMapping
//    public HttpEntity<?> addProductSizeVariant(@RequestBody ProductSizeVariantDto productSizeVariantDto){
//        ApiResponse apiResponse = productSizeVariantService.addProductSizeVariant(productSizeVariantDto);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
//    }
}
