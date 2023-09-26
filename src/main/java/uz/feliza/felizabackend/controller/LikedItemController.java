package uz.feliza.felizabackend.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.LikedItem;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.LikedItemDto;
import uz.feliza.felizabackend.service.LikedItemService;

import java.util.List;

@RestController
@RequestMapping("/api/likedItem")
public class LikedItemController {

    final private LikedItemService likedItemService;
    public LikedItemController(LikedItemService likedItemService){
        this.likedItemService = likedItemService;
    }

    @GetMapping("/getByCustomerId/{id}")
    public HttpEntity<?> getAllByCustomerId(@PathVariable Long id){
        List<LikedItem> allByCustomerId = likedItemService.getAllByCustomerId(id);
        return ResponseEntity.ok(allByCustomerId);
    }

    @PostMapping
    public HttpEntity<?> addLikedItem(@RequestBody LikedItemDto likedItemDto){
        ApiResponse apiResponse = likedItemService.addLikedItem(likedItemDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteLikedItem(@PathVariable Long id){
        ApiResponse apiResponse = likedItemService.deleteLikedItem(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }
}
