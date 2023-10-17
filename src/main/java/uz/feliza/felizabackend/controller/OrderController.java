package uz.feliza.felizabackend.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Order;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.EditOrderStatusDto;
import uz.feliza.felizabackend.payload.OrderDto;
import uz.feliza.felizabackend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    final private OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public HttpEntity<?> getAllOrders(){
        List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/getNewOrders")
    public HttpEntity<?> getAllNewOrders(){
        List<Order> allNewOrders = orderService.getAllNewOrders();
        return ResponseEntity.ok(allNewOrders);
    }

    @GetMapping("/getSendOrders")
    public HttpEntity<?> getAllSendOrders(){
        List<Order> allSendOrders = orderService.getAllSendOrders();
        return ResponseEntity.ok(allSendOrders);
    }

    @GetMapping("/getReachedOrders")
    public HttpEntity<?> getAllReachedOrders(){
        List<Order> allReachedOrders = orderService.getAllReachedOrders();
        return ResponseEntity.ok(allReachedOrders);
    }

    @GetMapping("/getAllByCustomerId/{customerId}")
    public HttpEntity<?> getAllByCustomerId( @PathVariable Long customerId){
        List<Order> getAllByCustomerId = orderService.getAllByCustomerId(customerId);
        return ResponseEntity.ok(getAllByCustomerId);
    }

    @GetMapping("/getByPostSendNumber")
    public HttpEntity<?> getByPostSendNumber( @RequestBody String postSendNumber){
        ApiResponse byPostSendNumber = orderService.getByPostSendNumber(postSendNumber);
        return ResponseEntity.ok(byPostSendNumber);
    }

    @PostMapping("/addOrder")
    public HttpEntity<?> addOrder(@RequestBody OrderDto orderDto){
        ApiResponse apiResponse = orderService.addOrder(orderDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200: 409).body(apiResponse);
    }

    @PostMapping("/editOrderStatusType/{orderId}")
    public HttpEntity<?> editOrderStatusType(@PathVariable Long orderId, @RequestBody EditOrderStatusDto orderStatus){
        ApiResponse apiResponse = orderService.editOrderStatusType(orderId, orderStatus);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200: 409).body(apiResponse);
    }

}
