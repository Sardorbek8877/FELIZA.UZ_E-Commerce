package uz.feliza.felizabackend.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.feliza.felizabackend.entity.CouponDetail;
import uz.feliza.felizabackend.service.CouponDetailService;

import java.util.List;

@RestController
@RequestMapping("/api/couponDetail")
public class CouponDetailController {

    CouponDetailService couponDetailService;
    public CouponDetailController(CouponDetailService couponDetailService){
        this.couponDetailService = couponDetailService;
    }

    @GetMapping("/{customerId}")
    public HttpEntity<?> getCouponsByCustomerId(@PathVariable Long customerId){
        List<CouponDetail> allCouponsByCustomerId = couponDetailService.getAllCouponsByCustomerId(customerId);
        return ResponseEntity.ok(allCouponsByCustomerId);
    }
}
