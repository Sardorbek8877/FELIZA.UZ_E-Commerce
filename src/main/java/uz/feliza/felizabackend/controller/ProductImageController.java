package uz.feliza.felizabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.feliza.felizabackend.service.ProductImagesService;
import uz.feliza.felizabackend.service.S3Service;

@RestController
@RequestMapping("/api/productImages")
public class ProductImageController {

    @Autowired
    ProductImagesService productImagesService;
    @Autowired
    S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("files")MultipartFile files){
        return productImagesService.uploadImage("key", files).eTag();
    }
}
