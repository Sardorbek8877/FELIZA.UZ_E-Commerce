package uz.feliza.felizabackend.service;

import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.*;
import uz.feliza.felizabackend.entity.enums.OrderStatusType;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.EditOrderStatusDto;
import uz.feliza.felizabackend.payload.OrderDto;
import uz.feliza.felizabackend.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    final private OrderRepository orderRepository;
    final private CustomerRepository customerRepository;
    final private AddressRepository addressRepository;
    final private CartItemRepository cartItemRepository;
    final private ProductSizeVariantRepository productSizeVariantRepository;
    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        AddressRepository addressRepository,
                        CartItemRepository cartItemRepository,
                        ProductSizeVariantRepository productSizeVariantRepository){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.cartItemRepository = cartItemRepository;
        this.productSizeVariantRepository = productSizeVariantRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getAllNewOrders(){
        return orderRepository.findAllByOrderStatusType(OrderStatusType.NEW);
    }

    public List<Order> getAllSendOrders(){
        return orderRepository.findAllByOrderStatusType(OrderStatusType.SEND);
    }

    public List<Order> getAllReachedOrders(){
        return orderRepository.findAllByOrderStatusType(OrderStatusType.REACHED);
    }

    public List<Order> getAllByCustomerId(Long customerId){
        return orderRepository.findAllByCustomerId(customerId);
    }

    public ApiResponse addOrder(OrderDto orderDto){
        Optional<Customer> optionalCustomer = customerRepository.findById(orderDto.getCustomerId());
        if (optionalCustomer.isEmpty())
            return new ApiResponse("Mijoz topilmadi", false);
        Customer customer = optionalCustomer.get();

        List<CartItem> cartItemList = new ArrayList<>();
        for (Long cartItemId : orderDto.getCartItemIdList()){
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
            if (optionalCartItem.isEmpty()){
                return new ApiResponse("Savatcha topilmadi", false);
            }
            CartItem cartItem = optionalCartItem.get();
            cartItemList.add(cartItem);
        }

        Optional<Address> optionalAddress = addressRepository.findById(orderDto.getAddressId());
        if (optionalAddress.isEmpty())
            return new ApiResponse("Manzil topilmadi", false);
        Address address = optionalAddress.get();

        Order order = new Order(
                customer,
                cartItemList,
                LocalDate.now(),
                orderDto.getPaymentMethod(),
                orderDto.getOrderCost(),
                OrderStatusType.NEW,
                orderDto.getShippingStatus(),
                orderDto.getDeliveryDays(),
                LocalDate.now().plusDays(orderDto.getDeliveryDays()),
                address);
        orderRepository.save(order);

        //decrease count of ProductSizeVariant
        for (CartItem cartItem : cartItemList){
            Optional<ProductSizeVariant> optionalProductSizeVariant = productSizeVariantRepository.findById(cartItem.getProductSizeVariant().getId());
            if (optionalProductSizeVariant.isEmpty())
                return new ApiResponse("Mahsulot topilmadi", false);
            ProductSizeVariant productSizeVariant = optionalProductSizeVariant.get();
            productSizeVariant.setQuantity(productSizeVariant.getQuantity() - cartItem.getQuantity());
            productSizeVariantRepository.save(productSizeVariant);
        }

        //Remove all Carts from Customer CartItemList
        for (Long cartItemId : orderDto.getCartItemIdList()){
            cartItemRepository.deleteById(cartItemId);
        }

        //Addition orderCost to Customer saleSum
        customer.setSaleSum(customer.getSaleSum() + orderDto.getOrderCost());
        customerRepository.save(customer);

        return new ApiResponse("Buyurtma qabul qilindi", true);
    }

    public ApiResponse editOrderStatusType(Long orderId, EditOrderStatusDto orderStatus){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            return new ApiResponse("Buyurtma topilmadi", false);
        Order order = optionalOrder.get();

        order.setOrderStatusType(orderStatus.getOrderStatusType());
        orderRepository.save(order);
        return new ApiResponse("Buyurtma statusi o'zgartirildi", true);
    }
}
