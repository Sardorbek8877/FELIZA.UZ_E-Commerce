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

    private final ProductRepository productRepository;
    private final ProductSizeVariantRepository productSizeVariantRepository;
    private final ProductImagesRepository productImagesRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final CompatibleProductsService compatibleProductsService;
    private final S3Service s3Service;
    private final BillzCRMService billzCRMService;
    public ProductService(ProductRepository productRepository,
                          ProductSizeVariantRepository productSizeVariantRepository,
                          ProductImagesRepository productImagesRepository,
                          CategoryRepository categoryRepository,
                          BrandRepository brandRepository,
                          ColorRepository colorRepository,
                          S3Service s3Service,
                          BillzCRMService billzCRMService,
                          CompatibleProductsService compatibleProductsService){
        this.productRepository = productRepository;
        this.productSizeVariantRepository = productSizeVariantRepository;
        this.productImagesRepository = productImagesRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.s3Service = s3Service;
        this.billzCRMService = billzCRMService;
        this.compatibleProductsService = compatibleProductsService;
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productList){
            ProductResponseDto productResponseDto = new ProductResponseDto();

            // FIND ALL COMPATIBLE PRODUCTS AND ADD IDS TO LIST
//            List<Long> compatibleProductsIdList = new ArrayList<>();
//            for (Product compatibleProduct : product.getCompatibleProducts()){
//                compatibleProductsIdList.add(compatibleProduct.getId());
//            }

            //FIND ALL PRODUCT SIZE VARIANTS
            List<ProductSizeVariant> allByProductId = productSizeVariantRepository.findAllByProductId(product.getId());

            //FIND ALL IMAGES
            List<ProductImages> allProductImagesById = productImagesRepository.findAllByProductId(product.getId());

            //CREATE PRODUCT AND SET ALL
            productResponseDto.setProduct(product);
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

//        // FIND ALL COMPATIBLE PRODUCTS AND ADD IDS TO LIST
//        List<Long> compatibleProductsIdList = new ArrayList<>();
//        for (Product compatibleProduct : product.getCompatibleProducts()){
//            compatibleProductsIdList.add(compatibleProduct.getId());
//        }

        //FIND ALL PRODUCT SIZE VARIANTS
        List<ProductSizeVariant> allByProductId = productSizeVariantRepository.findAllByProductId(product.getId());

        //FIND ALL IMAGES
        List<ProductImages> allProductImagesById = productImagesRepository.findAllByProductId(product.getId());

        //CREATE PRODUCT AND SET ALL
        productResponseDto.setProduct(product);
        productResponseDto.setProductSizeVariantList(allByProductId);
        productResponseDto.setProductImagesList(allProductImagesById);

        return productResponseDto;
    }

    public ApiResponse addProduct(ProductDto productDto, MultipartFile[] files){

        if (productRepository.existsByReferenceNumber(productDto.getReferenceNumber()))
            return new ApiResponse("Bunday Reference raqamli mahsulot allaqachon mavjud!", false);

        Product product = new Product();

        //CREATE BRAND AND SET TO PRODUCT
        Optional<Brand> optionalBrand = brandRepository.findById(productDto.getBrandId());
        if (optionalBrand.isEmpty())
            return new ApiResponse("Bunday Brend topilmadi!", false);
        Brand brand = optionalBrand.get();

        //CREATE CATEGORY LIST AND SET TO PRODUCT
        List<Category> categoryList = new ArrayList<>();
        for (Long categoryId : productDto.getCategoryId()){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isEmpty())
                return new ApiResponse("Bunday kategoriya topilmadi!", false);
            categoryList.add(optionalCategory.get());
        }

        //CREATE COLOR AND SET TO PRODUCT
        Optional<Color> optionalColor = colorRepository.findById(productDto.getColorId());
        if (optionalColor.isEmpty())
            return new ApiResponse("Bunday rang topilmadi!", false);
        Color color = optionalColor.get();

////        CREATE COMPATIBLE PRODUCTS LIST AND ADD PRODUCT
//        List<Product> compatibleProductList = new ArrayList<>();
//        if (!productDto.getCompatibleProductIdList().isEmpty()){
//            for (Long compatibleProductId : productDto.getCompatibleProductIdList()) {
//                Optional<Product> optionalProduct = productRepository.findById(compatibleProductId);
//
//                if (optionalProduct.isEmpty()){
//                    System.out.println("Bunday mahsulot topilmadi");
//                }
//                else {
//                    Product compatibleProduct = optionalProduct.get();
//                    compatibleProductList.add(compatibleProduct);
//                }
//            }
//        }

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

            //Create a unique key for the image in S3 (e.g., using UUID)
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
        product.setImportPrice(productDto.getImportPrice());
        product.setSellPrice(productDto.getSellPrice());
        product.setSale(productDto.getSale());
        product.setBrand(brand);
        product.setCategory(categoryList);
        product.setColor(color);
        product.setCompatibleProducts(productDto.getCompatibleProductsId());
        product.setProductImages(productImagesList);
        product.setProductSizeVariantList(productSizeVariantList);

        productRepository.save(product);

//        add Product to CompatibleProductList
        compatibleProductsService.updateCompatibleProductList(productDto.getCompatibleProductsId(), product.getId());

        //SEND REQUEST TO BILLZ API
//        for ( ProductSizeVariant productSV: productSizeVariantList ) {
//            billzCRMService.sendProductToCRM(productSV, product);
//        }

        return new ApiResponse("Mahsulot saqlandi!", true, product.getId());
    }

//    public ApiResponse addTestProduct(String nameUZB, String nameRUS, String descriptionUZB, String descriptionRUS,
//                                  String referenceNumber, Long price, Integer sale, Long brandId, List<Long> categoryIdList,
//                                  Long colorId, List<Long> compatibleProductIdList, List<ProductSizeVariantDto> productSizeVariantDtoList,
//                                  MultipartFile[] files){
//
//        if (productRepository.existsByReferenceNumber(referenceNumber))
//            return new ApiResponse("Bunday Reference raqamli mahsulot allaqachon mavjud!", false);
//
//        Product product = new Product();
//
//        //CREATE BRAND AND SET TO PRODUCT
//        Optional<Brand> optionalBrand = brandRepository.findById(brandId);
//        if (optionalBrand.isEmpty())
//            return new ApiResponse("Bunday Brend topilmadi!", false);
//        Brand brand = optionalBrand.get();
//
//        //CREATE CATEGORY LIST AND SET TO PRODUCT
//        List<Category> categoryList = new ArrayList<>();
//        for (Long categoryId : categoryIdList){
//            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
//            if (optionalCategory.isEmpty())
//                return new ApiResponse("Bunday kategoriya topilmadi!", false);
//            categoryList.add(optionalCategory.get());
//        }
//
//        //CREATE COLOR AND SET TO PRODUCT
//        Optional<Color> optionalColor = colorRepository.findById(colorId);
//        if (optionalColor.isEmpty())
//            return new ApiResponse("Bunday rang topilmadi!", false);
//        Color color = optionalColor.get();
//
////        CREATE COMPATIBLE PRODUCTS LIST AND ADD PRODUCT
//        List<Product> compatibleProductList = new ArrayList<>();
//        if (!compatibleProductIdList.isEmpty()){
//            for (Long compatibleProductId : compatibleProductIdList) {
//                Optional<Product> optionalProduct = productRepository.findById(compatibleProductId);
//
//                if (optionalProduct.isEmpty()){
//                    System.out.println("Bunday mahsulot topilmadi");
//                }
//                else {
//                    Product compatibleProduct = optionalProduct.get();
//                    compatibleProductList.add(compatibleProduct);
//                }
//            }
//        }
//
////        CREATE LIST<PRODUCTSIZEVARIANT>
//        List<ProductSizeVariant> productSizeVariantList = new ArrayList<>();
//        for (ProductSizeVariantDto productSizeVariantDto : productSizeVariantDtoList){
//            if (productSizeVariantRepository.existsByBarCode(productSizeVariantDto.getBarCode()))
//                return new ApiResponse("Bunday Barcode li mahsulot mavjud!", false);
//
//            ProductSizeVariant productSizeVariant = new ProductSizeVariant(
//                    productSizeVariantDto.getBarCode(),
//                    productSizeVariantDto.getQuantity(),
//                    productSizeVariantDto.getSize(),
//                    product);
//            productSizeVariantList.add(productSizeVariant);
//        }
//
//        //CREATE LIST<PRODUCTIMAGE>
//        List<ProductImages> productImagesList = new ArrayList<>();
//
//        for (MultipartFile imageFile : files){
//
////          Create a unique key for the image in S3 (e.g., using UUID)
//            String s3Key = "product-images/" + UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
//            String imageUrl = "https://feliza-files.s3.ap-southeast-1.amazonaws.com/" + s3Key;
//
//            //Upload the image to AWS S3
//            PutObjectResponse putObjectResponse = s3Service.uploadImage2(imageFile, s3Key);
//
//            if (putObjectResponse.sdkHttpResponse().isSuccessful()){
//                ProductImages productImages = new ProductImages();
//                productImages.setProduct(product);
//                productImages.setURL(imageUrl);
//                productImagesList.add(productImages);
//            }
//            else {
//                return new ApiResponse("Failed to upload image to S3", false);
//            }
//        }
//
//        product.setNameUZB(nameUZB);
//        product.setNameRUS(nameRUS);
//        product.setDescriptionUZB(descriptionUZB);
//        product.setDescriptionRUS(descriptionRUS);
//        product.setReferenceNumber(referenceNumber);
//        product.setPrice(price);
//        product.setSale(sale);
//        product.setBrand(brand);
//        product.setCategory(categoryList);
//        product.setColor(color);
//        product.setCompatibleProducts(compatibleProductList);
//        product.setProductSizeVariantList(productSizeVariantList);
//        product.setProductImages(productImagesList);
//
//        productRepository.save(product);
//        return new ApiResponse("Mahsulot saqlandi!", true, product.getId());
//    }
}