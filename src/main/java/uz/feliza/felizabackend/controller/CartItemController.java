package uz.feliza.felizabackend.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.CartItem;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.CartItemDto;
import uz.feliza.felizabackend.service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {

    final private CartItemService cartItemService;
    public CartItemController(CartItemService cartItemService){
        this.cartItemService = cartItemService;
    }

    @GetMapping("/byCustomerId/{customerId}")
    public HttpEntity<?> getAllByCustomerId( @PathVariable Long customerId){
        List<CartItem> allByCustomerId = cartItemService.getAllByCustomerId(customerId);
        return ResponseEntity.ok(allByCustomerId);
    }

    @GetMapping("/byCartId/{cartItemId}")
    public HttpEntity<?> getCartItemById(@PathVariable Long cartItemId){
        ApiResponse cartItemById = cartItemService.getCartItemById(cartItemId);
        return ResponseEntity.ok(cartItemById);
    }

    @PostMapping("/addCartItem")
    public HttpEntity<?> addCartItem(@RequestBody CartItemDto cartItemDto){
        ApiResponse apiResponse = cartItemService.addCartItem(cartItemDto);
        return ResponseEntity.status(apiResponse.isSuccess() ?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCartItem(@PathVariable Long id, @RequestBody CartItemDto cartItemDto){
        ApiResponse apiResponse = cartItemService.editCartItem(id, cartItemDto);
        return ResponseEntity.status(apiResponse.isSuccess() ?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCartItem(@PathVariable Long id){
        ApiResponse apiResponse = cartItemService.deleteCartItem(id);
        return ResponseEntity.status(apiResponse.isSuccess() ?200:409).body(apiResponse);
    }
}
