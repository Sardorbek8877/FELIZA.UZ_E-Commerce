package uz.feliza.felizabackend.service;

import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.LikedItem;
import uz.feliza.felizabackend.entity.Product;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.LikedItemDto;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.repository.LikedItemRepository;
import uz.feliza.felizabackend.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LikedItemService {

    final private LikedItemRepository likedItemRepository;
    final private CustomerRepository customerRepository;
    final private ProductRepository productRepository;
    public LikedItemService(LikedItemRepository likedItemRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository){
        this.likedItemRepository = likedItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }


    public List<LikedItem> getAllByCustomerId(Long customerId){
        return likedItemRepository.findAllByCustomerId(customerId);
    }

    public ApiResponse addLikedItem(LikedItemDto likedItemDto){

        Optional<Customer> optionalCustomer = customerRepository.findById(likedItemDto.getCustomerId());
        if (optionalCustomer.isEmpty())
            return new ApiResponse("Haridor topilmadi", false);
        Customer customer = optionalCustomer.get();

        Optional<Product> optionalProduct = productRepository.findById(likedItemDto.getProductId());
        if (optionalProduct.isEmpty())
            return new ApiResponse("Mahsulot topilmadi", false);
        Product product = optionalProduct.get();

        LikedItem likedItem = new LikedItem(customer, product);
        likedItemRepository.save(likedItem);
        return new ApiResponse("Mahsulot sevimlilar ro'yxatiga qo'shildi", true);
    }

    public ApiResponse deleteLikedItem(Long id){
        Optional<LikedItem> byId = likedItemRepository.findById(id);
        if (byId.isEmpty())
            return new ApiResponse("Mahsulot topilmadi", false);
        likedItemRepository.deleteById(id);
        return new ApiResponse("Mahsulot sevimlilar ro'yxatidan o'chirildi", true);
    }
}
