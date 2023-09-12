package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.*;
import uz.feliza.felizabackend.repository.*;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ColorRepository colorRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public ApiResponse addProduct(ProductDto productDto){

        if (productRepository.existsByReferenceNumber(productDto.getReferenceNumber()))
            return new ApiResponse("Bunday Reference raqamli mahsulot allaqachon mavjud!", false);

        //CREATE BRAND AND ADD PRODUCT
        Optional<Brand> optionalBrand = brandRepository.findById(productDto.getBrandId());
        if (optionalBrand.isEmpty())
            return new ApiResponse("Bunday Brend topilmadi!", false);
        Brand brand = optionalBrand.get();



        //CREATE CATEGORY LIST AND ADD PRODUCT
        List<Category> categoryList = new ArrayList<>();
        for (Long categoryId : productDto.getCategoryId()){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isEmpty())
                return new ApiResponse("Bunday kategoriya topilmadi!", false);
            categoryList.add(optionalCategory.get());
        }

        //CREATE COLOR AND ADD PRODUCT
        Optional<Color> optionalColor = colorRepository.findById(productDto.getColorId());
        if (optionalColor.isEmpty())
            return new ApiResponse("Bunday rang topilmadi!", false);
        Color color = optionalColor.get();

//        //CREATE COMPATIBLE PRODUCTS LIST AND ADD PRODUCT
//        List<Product> compatibleProductList = new ArrayList<>();
//        for (Long itemProduct : productDto.getCompatibleProductIds()) {
//            Optional<Product> optionalProduct = productRepository.findById(itemProduct);
//            if (optionalProduct.isEmpty())
//                return new ApiResponse("Bunday mahsulot topilmadi", false);
//            compatibleProductList.add(optionalProduct.get());
//        }

//        //CREATE IMAGES LIST AND ADD PRODUCT
//        List<ProductImages> productImagesList = new ArrayList<>();
//        for (Long itemImages : productDto.getImageIdList()) {
//            Optional<ProductImages> optionalProductImages = productImagesRepository.findById(itemImages);
//            if (optionalProductImages.isEmpty())
//                return new ApiResponse("Bunday rasm topilmadi", false);
//            productImagesList.add(optionalProductImages.get());
//        }

        Product product = new Product(
                productDto.getNameUZB(),
                productDto.getNameRUS(),
                productDto.getDescriptionUZB(),
                productDto.getDescriptionRUS(),
                productDto.getReferenceNumber(),
                productDto.getPrice(),
                productDto.getSale(),
                brand,
                categoryList,
                color);
//                compatibleProductList);

        productRepository.save(product);
        return new ApiResponse("Mahsulot saqlandi!", true, product.getId());
    }
}
