package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.CartItem;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.ProductSizeVariant;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.CartItemDto;
import uz.feliza.felizabackend.repository.CartItemRepository;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.repository.ProductSizeVariantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    final private CartItemRepository cartItemRepository;
    final private CustomerRepository customerRepository;
    final private ProductSizeVariantRepository productSizeVariantRepository;
    public CartItemService(CartItemRepository cartItemRepository,
                           CustomerRepository customerRepository,
                           ProductSizeVariantRepository productSizeVariantRepository){
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productSizeVariantRepository = productSizeVariantRepository;
    }

    public List<CartItem> getAllByCustomerId(Long customerId){
        return cartItemRepository.findAllByCustomerId(customerId);
    }

    public ApiResponse getCartItemById(Long cartItemId){
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isEmpty())
            return new ApiResponse("Savatcha topilmadi", false);
        return new ApiResponse(cartItemId + " IDli savatcha", true, optionalCartItem.get());
    }

    public ApiResponse addCartItem(CartItemDto cartItemDto){

        Optional<ProductSizeVariant> optionalProductSizeVariant = productSizeVariantRepository.findById(cartItemDto.getProductSizeVariantId());
        if (optionalProductSizeVariant.isEmpty())
            return new ApiResponse("Mahsulot topilmadi", false);
        ProductSizeVariant productSizeVariant = optionalProductSizeVariant.get();

        boolean exists = cartItemRepository.existsByProductSizeVariantId(cartItemDto.getProductSizeVariantId());
        if (exists){
            Optional<CartItem> optionalCartItem = cartItemRepository.findByProductSizeVariant(productSizeVariant);
            CartItem cartItem = optionalCartItem.get();

            cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.getQuantity());
            cartItemRepository.save(cartItem);
            return new ApiResponse("Mahsulot soni ko'paytirildi", true);
        }

        Optional<Customer> optionalCustomer = customerRepository.findById(cartItemDto.getCustomerId());
        if (optionalCustomer.isEmpty())
            return new ApiResponse("Haridor topilmadi", false);
        Customer customer = optionalCustomer.get();

        CartItem cartItem = new CartItem(customer, productSizeVariant, cartItemDto.getQuantity());
        cartItemRepository.save(cartItem);
        return new ApiResponse("Mahsulot savatchaga qo'shildi)", true);
    }

    public ApiResponse editCartItem(Long id, CartItemDto cartItemDto){
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
        if (optionalCartItem.isEmpty())
            return new ApiResponse("Savatcha topilmadi", false);
        CartItem cartItem = optionalCartItem.get();

        cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.getQuantity());

        cartItemRepository.save(cartItem);
        return new ApiResponse("Savatcha o'zgartirildi", true);
    }

    public ApiResponse deleteCartItem(Long id){
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
        if (optionalCartItem.isEmpty())
            return new ApiResponse("Savatcha topilmadi", false);
        cartItemRepository.deleteById(id);
        return new ApiResponse("Savatcha o'chirildi", true);
    }
}
