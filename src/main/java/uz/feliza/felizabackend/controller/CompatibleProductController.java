package uz.feliza.felizabackend.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.CompatibleProductDto;
import uz.feliza.felizabackend.service.CompatibleProductsService;

@RestController
@RequestMapping("/api/compatibleProduct")
public class CompatibleProductController {

    private final CompatibleProductsService compatibleProductsService;
    public CompatibleProductController(CompatibleProductsService compatibleProductsService){
        this.compatibleProductsService = compatibleProductsService;
    }

    @PostMapping
    public HttpEntity<?> addCompatibleProduct(){
        ApiResponse apiResponse = compatibleProductsService.addCompatibleProductsList();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/update")
    public HttpEntity<?> updateCompatibleProductList(@RequestBody CompatibleProductDto compatibleProductDto){
        ApiResponse apiResponse = compatibleProductsService.updateCompatibleProductList(compatibleProductDto.getCompatibleProductId(), compatibleProductDto.getProductId());
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
