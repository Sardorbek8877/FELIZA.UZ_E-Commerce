package uz.feliza.felizabackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.feliza.felizabackend.entity.Product;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.ProductDto;
import uz.feliza.felizabackend.payload.ProductResponseDto;
import uz.feliza.felizabackend.payload.ProductSizeVariantDto;
import uz.feliza.felizabackend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
//    @RolesAllowed({"CUSTOMER"})
    public ResponseEntity<?> getAllProducts(){
        List<ProductResponseDto> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProductById(@PathVariable Long id){
        ProductResponseDto productResponse = productService.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createProduct(@RequestParam("files") MultipartFile[] files, @RequestParam("productDto") String productDto) {
        try {
            ProductDto productDto1 = new ObjectMapper().readValue(productDto, ProductDto.class);
            ApiResponse apiResponse = productService.addProduct(productDto1, files);

            return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating the product");
        }
    }

//    @PostMapping(value = "/add/test", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public HttpEntity<?> addProduct(@Valid @RequestPart ProductDto productDto , @RequestBody MultipartFile[] files){
//        ApiResponse apiResponse = productService.addProduct(productDto, files);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
//    }

//    @PostMapping(value = "/add/test", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public HttpEntity<?> addTestProduct(@Valid @RequestParam("nameUZB") String nameUZB,
//                                    @Valid @RequestParam("nameRUS") String nameRUS,
//                                    @Valid @RequestParam("descriptionUZB") String descriptionUZB,
//                                    @Valid @RequestParam("descriptionRUS") String descriptionRUS,
//                                    @Valid @RequestParam("referenceNumber") String referenceNumber,
//                                    @Valid @RequestParam("price") Long price,
//                                    @Valid @RequestParam("sale") Integer sale,
//                                    @Valid @RequestParam("brandId") Long brandId,
//                                    @Valid @RequestParam("categoryId") List<Long> categoryId,
//                                    @Valid @RequestParam("colorId") Long colorId,
//                                    @Valid @RequestParam("compatibleProductIdList") List<Long> compatibleProductIdList,
//                                    @Valid @RequestPart(value = "productSizeVariantDtoList") List<ProductSizeVariantDto> productSizeVariantDtoList,
//                                    @RequestBody MultipartFile[] files){
//        ApiResponse apiResponse = productService.addTestProduct(nameUZB, nameRUS, descriptionUZB, descriptionRUS, referenceNumber,
//                price, sale, brandId, categoryId, colorId, compatibleProductIdList, productSizeVariantDtoList, files);//
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
//    }
}
