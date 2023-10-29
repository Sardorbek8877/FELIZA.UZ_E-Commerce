package uz.feliza.felizabackend.service;

import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.CompatibleProducts;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.CompatibleProductsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompatibleProductsService {

    private final CompatibleProductsRepository compatibleProductsRepository;
    public CompatibleProductsService(CompatibleProductsRepository compatibleProductsRepository){
        this.compatibleProductsRepository = compatibleProductsRepository;
    }

    public ApiResponse addCompatibleProductsList(){

        List<Long> compatibleProductsIdList = new ArrayList<>();
        CompatibleProducts compatibleProducts = new CompatibleProducts();
        compatibleProducts.setProductList(compatibleProductsIdList);
        compatibleProductsRepository.save(compatibleProducts);

        return new ApiResponse("Yangi Turkum Mahsulotlar qatori yaratildi", true, compatibleProducts.getId());
    }

    public ApiResponse updateCompatibleProductList(Long compatibleProductId, Long productId){
        Optional<CompatibleProducts> optionalCompatibleProducts = compatibleProductsRepository.findById(compatibleProductId);
        if (optionalCompatibleProducts.isEmpty())
            return new ApiResponse("Bunday turkum topilmadi", false);

        CompatibleProducts compatibleProducts = optionalCompatibleProducts.get();
        compatibleProducts.getProductList().add(productId);
        compatibleProductsRepository.save(compatibleProducts);
        return new ApiResponse("Mahsulot turkum sahifasiga qo'shildi", true);
    }
}
