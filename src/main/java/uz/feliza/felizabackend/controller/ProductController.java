package uz.feliza.felizabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Product;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.ProductDto;
import uz.feliza.felizabackend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public HttpEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody ProductDto productDto){
        ApiResponse apiResponse = productService.addProduct(productDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

}
