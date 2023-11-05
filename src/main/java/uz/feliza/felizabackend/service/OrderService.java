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

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductSizeVariantRepository productSizeVariantRepository;
    private final ProductRepository productRepository;
    private final BillzCRMService billzCRMService;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        AddressRepository addressRepository,
                        CartItemRepository cartItemRepository,
                        ProductSizeVariantRepository productSizeVariantRepository,
                        ProductRepository productRepository,
                        BillzCRMService billzCRMService){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.cartItemRepository = cartItemRepository;
        this.productSizeVariantRepository = productSizeVariantRepository;
        this.productRepository = productRepository;
        this.billzCRMService = billzCRMService;
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

        Long orderCost = 0L;
        List<CartItem> cartItemList = new ArrayList<>();
        for (Long cartItemId : orderDto.getCartItemIdList()){
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
            if (optionalCartItem.isEmpty()){
                return new ApiResponse("Savatcha topilmadi", false);
            }
            CartItem cartItem = optionalCartItem.get();
            cartItemList.add(cartItem);

            //Find product price and addition price to orderCost
            Optional<ProductSizeVariant> optionalProductSizeVariant = productSizeVariantRepository.findById(cartItem.getProductSizeVariant().getId());
            if (optionalProductSizeVariant.isEmpty())
                return new ApiResponse("Mahsulot topiladi", false);
            ProductSizeVariant productSizeVariant = optionalProductSizeVariant.get();
            Optional<Product> optionalProduct = productRepository.findById(productSizeVariant.getProduct().getId());
            if (optionalProduct.isEmpty())
                return new ApiResponse("Mahsulot topilmadi", false);
            Product product = optionalProduct.get();
            orderCost += product.getSellPrice() * cartItem.getQuantity();
        }

        Optional<Address> optionalAddress = addressRepository.findById(orderDto.getAddressId());
        if (optionalAddress.isEmpty())
            return new ApiResponse("Manzil topilmadi", false);
        Address address = optionalAddress.get();

        Order order = new Order(
                customer,
                cartItemList,
                orderDto.getOrderNumber(),
                null,
                LocalDate.now(),
                orderDto.getPaymentMethod(),
                orderCost,
                OrderStatusType.NEW,
                orderDto.getShippingStatus(),
                orderDto.getDeliveryDays(),
                LocalDate.now().plusDays(orderDto.getDeliveryDays()),
                address,false);
        orderRepository.save(order);

        //SEND REQUEST TO BILLZ API FOR CREATE ORDER
        billzCRMService.sendOrderToCRM(order);

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
        customer.setSaleSum(customer.getSaleSum() + orderCost);
        customerRepository.save(customer);


        return new ApiResponse("Buyurtma qabul qilindi", true);
    }

    public ApiResponse editOrderStatusType(Long orderId, EditOrderStatusDto orderStatusDto){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            return new ApiResponse("Buyurtma topilmadi", false);
        Order order = optionalOrder.get();

        order.setPostSendNumber(orderStatusDto.getPostSendNumber());
        order.setOrderStatusType(orderStatusDto.getOrderStatusType());
        orderRepository.save(order);
        return new ApiResponse("Buyurtma statusi o'zgartirildi", true);
    }

    public ApiResponse getByPostSendNumber(String postSendNumber){
        Optional<Order> byPostSendNumber = orderRepository.findByPostSendNumber(postSendNumber);
        if (byPostSendNumber.isEmpty())
            return new ApiResponse("Buyurtma topilmadi", false);

        Order order = byPostSendNumber.get();
        return new ApiResponse("Siz izlagan buyurtma: ", true, order);
    }
}
