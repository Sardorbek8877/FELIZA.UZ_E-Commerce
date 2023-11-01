package uz.feliza.felizabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.feliza.felizabackend.service.ProductImagesService;
import uz.feliza.felizabackend.service.S3Service;

import java.util.UUID;

@RestController
@RequestMapping("/api/productImages")
public class ProductImageController {
//bu hozircha bo'lishi kerakmasmi?
    @Autowired
    S3Service s3Service;

    @PostMapping
    public String uploadImage(@RequestBody MultipartFile[] files){
        for (MultipartFile f : files) {
            //Create a unique key for the image in S3 (e.g., using UUID)
            String s3Key = "product-images/" + UUID.randomUUID() + "-" + f.getOriginalFilename();
            String imageUrl = "https://feliza-files.s3.ap-southeast-1.amazonaws.com/" + s3Key;
            s3Service.uploadImage2(f, s3Key);
        }
        return "Rasmlar yaratildi";
    }


}
