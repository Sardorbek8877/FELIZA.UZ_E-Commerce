package uz.feliza.felizabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Color;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.service.ColorService;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {

    @Autowired
    ColorService colorService;

    @GetMapping
    public HttpEntity<?> getAllColors(){
        List<Color> allColor = colorService.getAllColor();
        return ResponseEntity.ok(allColor);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getColorById(@PathVariable Long id){
        ApiResponse apiResponse = colorService.getColorById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    @PostMapping("/add")
    public HttpEntity<?> addColor(@RequestBody Color color){
        ApiResponse apiResponse = colorService.addColor(color);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editColor(@PathVariable Long id, @RequestBody Color color){
        ApiResponse apiResponse = colorService.editColor(id, color);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteColor(@PathVariable Long id){
        ApiResponse apiResponse = colorService.deleteColor(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }
}
