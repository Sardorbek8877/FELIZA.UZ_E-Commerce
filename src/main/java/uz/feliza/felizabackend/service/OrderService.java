package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
}
