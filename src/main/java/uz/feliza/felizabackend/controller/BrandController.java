package uz.feliza.felizabackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandService brandService;
    public BrandController(BrandService brandService){
        this.brandService = brandService;
    }

    // Endpoint to retrieve a list of all brands.
    @GetMapping
    public HttpEntity<?> getAllBrands(){
        List<Brand> allBrands = brandService.getAllBrands();
        return ResponseEntity.ok(allBrands);
    }

    // Endpoint to retrieve a Brand by its unique identifier (ID).
    @GetMapping("/{id}")
    public HttpEntity<?> getBrandById(@PathVariable Long id){
        ApiResponse apiResponse = brandService.getBrandById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    @PostMapping("/add")
    public HttpEntity<?> addBrand(@Valid @RequestBody Brand brand){
        ApiResponse apiResponse = brandService.addBrand(brand);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    // Endpoint to edit the name of an existing Brand by its ID.
    @PutMapping("/{id}")
    public HttpEntity<?> editBrand(@Valid @PathVariable Long id, @RequestBody Brand brand){
        ApiResponse apiResponse = brandService.editBrand(id, brand);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:404).body(apiResponse);
    }

    // Endpoint to delete a Brand by its ID.
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteBrand(@PathVariable Long id){
        ApiResponse apiResponse = brandService.deleteBrand(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:404).body(apiResponse);
    }
}
