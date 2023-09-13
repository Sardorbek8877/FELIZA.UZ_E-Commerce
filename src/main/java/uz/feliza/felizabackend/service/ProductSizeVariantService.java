package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.ProductSizeVariant;
import uz.feliza.felizabackend.repository.ProductRepository;
import uz.feliza.felizabackend.repository.ProductSizeVariantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSizeVariantService {

    @Autowired
    ProductSizeVariantRepository productSizeVariantRepository;
    @Autowired
    ProductRepository productRepository;

    public List<ProductSizeVariant> getAllProductSizeVariant(){
        return productSizeVariantRepository.findAll();
    }

    public ProductSizeVariant getProductSizeVariantById(Long id){
        Optional<ProductSizeVariant> optionalProductSizeVariant = productSizeVariantRepository.findById(id);
        return optionalProductSizeVariant.orElseGet(ProductSizeVariant::new);

    }

//    public ApiResponse addProductSizeVariant(ProductSizeVariantDto productSizeVariantDto){
//
//        if (productSizeVariantRepository.existsByBarCode(productSizeVariantDto.getBarCode()))
//            return new ApiResponse("Bunda Barcode li mahsulot mavjud!", false);
//
//        //CREATE PRODUCT AND ADD ProductSizeVariant
//        Optional<Product> optionalProduct = productRepository.findById(productSizeVariantDto.getProductId());
//        if (optionalProduct.isEmpty())
//            return new ApiResponse("Bunday mahsulot topilmadi!", false);
//        Product product = optionalProduct.get();
//
//        ProductSizeVariant productSizeVariant = new ProductSizeVariant(
//                productSizeVariantDto.getBarCode(),
//                productSizeVariantDto.getQuantity(),
//                productSizeVariantDto.getSize(),
//                product);
//
//        productSizeVariantRepository.save(productSizeVariant);
//        return new ApiResponse(productSizeVariantDto.getSize() + " o'lchamli mahsulot yaratildi",
//                true, productSizeVariant.getId());
//    }
}
