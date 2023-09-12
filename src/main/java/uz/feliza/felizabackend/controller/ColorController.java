package uz.feliza.felizabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.feliza.felizabackend.entity.Color;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.ProductDto;
import uz.feliza.felizabackend.service.ColorService;

@RestController
@RequestMapping("/api/color")
public class ColorController {

    @Autowired
    ColorService colorService;

    @PostMapping
    public HttpEntity<?> addColor(@RequestBody Color color){
        ApiResponse apiResponse = colorService.addColor(color);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }
}
