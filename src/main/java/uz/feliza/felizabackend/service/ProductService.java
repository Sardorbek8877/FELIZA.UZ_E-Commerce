package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.*;
import uz.feliza.felizabackend.payload.*;
import uz.feliza.felizabackend.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductSizeVariantRepository productSizeVariantRepository;
    @Autowired
    ProductImagesRepository productImagesRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ColorRepository colorRepository;

    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productList){
            ProductResponseDto productResponseDto = new ProductResponseDto();

            //FIND ALL PRODUCT SIZE VARIANTS
            List<ProductSizeVariant> allByProductId = productSizeVariantRepository.findAllByProductId(product.getId());

            //FIND ALL IMAGES
            List<ProductImages> allProductImagesById = productImagesRepository.findAllByProductId(product.getId());

            //CREATE PRODUCT AND SET ALL
            productResponseDto.setProduct(product);
            productResponseDto.setCompatibleProducts(product.getCompatibleProductsId());
            productResponseDto.setProductSizeVariantList(allByProductId);
            productResponseDto.setProductImagesList(allProductImagesById);

            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }


    public ProductResponseDto getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            return new ProductResponseDto();
        Product product = optionalProduct.get();

        ProductResponseDto productResponseDto = new ProductResponseDto();

        //FIND ALL PRODUCT SIZE VARIANTS
        List<ProductSizeVariant> allByProductId = productSizeVariantRepository.findAllByProductId(product.getId());

        //FIND ALL IMAGES
        List<ProductImages> allProductImagesById = productImagesRepository.findAllByProductId(product.getId());

        //CREATE PRODUCT AND SET ALL
        productResponseDto.setProduct(product);
        productResponseDto.setCompatibleProducts(product.getCompatibleProductsId());
        productResponseDto.setProductSizeVariantList(allByProductId);
        productResponseDto.setProductImagesList(allProductImagesById);

        return productResponseDto;
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

        //CREATE COMPATIBLE PRODUCTS LIST AND ADD PRODUCT
        List<Long> compatibleProductIdList = new ArrayList<>();
        for (ProductReferenceNumberAndColorDto itemProductRefNumAndColor : productDto.getCompatibleProductIds()) {

            Optional<Product> optionalProduct = productRepository.findByReferenceNumberAndColorId(
                    itemProductRefNumAndColor.getReferenceNumber(), itemProductRefNumAndColor.getId());

            if (optionalProduct.isEmpty())
                return new ApiResponse("Bunday mahsulot topilmadi", false);
            compatibleProductIdList.add(optionalProduct.get().getId());
        }

        //CREATE PRODUCT AND ADD ALL PARAMETERS
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
                color,
                compatibleProductIdList);

        //CREATE LIST<PRODUCTSIZEVARIANT>
        List<ProductSizeVariant> productSizeVariantList = new ArrayList<>();
        for (ProductSizeVariantDto productSizeVariantDto : productDto.getProductSizeVariantDtoList()){
            if (productSizeVariantRepository.existsByBarCode(productSizeVariantDto.getBarCode()))
                return new ApiResponse("Bunday Barcode li mahsulot mavjud!", false);

            ProductSizeVariant productSizeVariant = new ProductSizeVariant(
                    productSizeVariantDto.getBarCode(),
                    productSizeVariantDto.getQuantity(),
                    productSizeVariantDto.getSize(),
                    product);
            productSizeVariantList.add(productSizeVariant);
        }
        product.setProductSizeVariantList(productSizeVariantList);

        //CREATE LIST<PRODUCTIMAGE>
        List<ProductImages> productImagesList = new ArrayList<>();
        for (ProductImagesDto productImagesDto : productDto.getProductImagesDtoList()){
            ProductImages productImages = new ProductImages(
                    product,
                    productImagesDto.getUrl());
            productImagesList.add(productImages);
        }
        product.setProductImages(productImagesList);

        productRepository.save(product);
        return new ApiResponse("Mahsulot saqlandi!", true, product.getId());
    }
}