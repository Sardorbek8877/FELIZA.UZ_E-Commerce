package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.ProductSizeVariant;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.OrderDetailsRepository;
import uz.feliza.felizabackend.repository.OrderRepository;

@Service
public class OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepository;


}
