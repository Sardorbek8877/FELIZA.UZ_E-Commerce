package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import uz.feliza.felizabackend.entity.*;
import uz.feliza.felizabackend.payload.*;
import uz.feliza.felizabackend.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Autowired
    ProductImagesService productImagesService;
    @Autowired
    S3Service s3Service;

    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productList){
            ProductResponseDto productResponseDto = new ProductResponseDto();

            // FIND ALL COMPATIBLE PRODUCTS AND ADD IDS TO LIST
            List<Long> compatibleProductsIdList = new ArrayList<>();
            for (Product compatibleProduct : product.getCompatibleProducts()){
                compatibleProductsIdList.add(compatibleProduct.getId());
            }


            //FIND ALL PRODUCT SIZE VARIANTS
            List<ProductSizeVariant> allByProductId = productSizeVariantRepository.findAllByProductId(product.getId());

            //FIND ALL IMAGES
            List<ProductImages> allProductImagesById = productImagesRepository.findAllByProductId(product.getId());

            //CREATE PRODUCT AND SET ALL
            productResponseDto.setProduct(product);
            productResponseDto.setCompatibleProducts(compatibleProductsIdList);
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

        // FIND ALL COMPATIBLE PRODUCTS AND ADD IDS TO LIST
        List<Long> compatibleProductsIdList = new ArrayList<>();
        for (Product compatibleProduct : product.getCompatibleProducts()){
            compatibleProductsIdList.add(compatibleProduct.getId());
        }

        //FIND ALL PRODUCT SIZE VARIANTS
        List<ProductSizeVariant> allByProductId = productSizeVariantRepository.findAllByProductId(product.getId());

        //FIND ALL IMAGES
        List<ProductImages> allProductImagesById = productImagesRepository.findAllByProductId(product.getId());

        //CREATE PRODUCT AND SET ALL
        productResponseDto.setProduct(product);
        productResponseDto.setCompatibleProducts(compatibleProductsIdList);
        productResponseDto.setProductSizeVariantList(allByProductId);
        productResponseDto.setProductImagesList(allProductImagesById);

        return productResponseDto;
    }

    public ApiResponse addProduct(ProductDto productDto, MultipartFile[] files){ //,

        if (productRepository.existsByReferenceNumber(productDto.getReferenceNumber()))
            return new ApiResponse("Bunday Reference raqamli mahsulot allaqachon mavjud!", false);

        Product product = new Product();

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

//        CREATE COMPATIBLE PRODUCTS LIST AND ADD PRODUCT
        List<Product> compatibleProductList = new ArrayList<>();
        for (ProductIdDto productIdDto : productDto.getCompatibleProductIdList()) {
            Optional<Product> optionalProduct = productRepository.findById(productIdDto.getId());

            if (optionalProduct.isEmpty())
                return new ApiResponse("Bunday mahsulot topilmadi", false);

            Product compatibleProduct = optionalProduct.get();

            compatibleProductList.add(compatibleProduct);
        }

//        CREATE LIST<PRODUCTSIZEVARIANT>
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

        //CREATE LIST<PRODUCTIMAGE>
        List<ProductImages> productImagesList = new ArrayList<>();

        for (MultipartFile imageFile : files){

//          Create a unique key for the image in S3 (e.g., using UUID)
            String s3Key = "product-images/" + UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
            String imageUrl = "https://feliza-files.s3.ap-southeast-1.amazonaws.com/" + s3Key;

            //Upload the image to AWS S3
            PutObjectResponse putObjectResponse = s3Service.uploadImage2(imageFile, s3Key);

            if (putObjectResponse.sdkHttpResponse().isSuccessful()){
                ProductImages productImages = new ProductImages();
                productImages.setProduct(product);
                productImages.setURL(imageUrl);
                productImagesList.add(productImages);
            }
            else {
                return new ApiResponse("Failed to upload image to S3", false);
            }
        }

        product.setNameUZB(productDto.getNameUZB());
        product.setNameRUS(productDto.getNameRUS());
        product.setDescriptionUZB(productDto.getDescriptionUZB());
        product.setDescriptionRUS(productDto.getDescriptionRUS());
        product.setReferenceNumber(productDto.getReferenceNumber());
        product.setPrice(productDto.getPrice());
        product.setSale(productDto.getSale());
        product.setBrand(brand);
        product.setCategory(categoryList);
        product.setColor(color);
        product.setCompatibleProducts(compatibleProductList);
        product.setProductSizeVariantList(productSizeVariantList);
        product.setProductImages(productImagesList);

        productRepository.save(product);
        return new ApiResponse("Mahsulot saqlandi!", true, product.getId());
    }
}