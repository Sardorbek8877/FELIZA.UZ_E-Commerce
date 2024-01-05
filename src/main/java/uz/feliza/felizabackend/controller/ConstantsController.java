//package uz.feliza.felizabackend.controller;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import uz.feliza.felizabackend.entity.Color;
//import uz.feliza.felizabackend.entity.Constants;
//import uz.feliza.felizabackend.payload.ApiResponse;
//import uz.feliza.felizabackend.service.ConstantsService;
//
//@RestController
//@RequestMapping("/api/constants")
//public class ConstantsController {
//
//    private final ConstantsService constantsService;
//
//    public ConstantsController(ConstantsService constantsService){
//        this.constantsService = constantsService;
//    }
//
//    @PostMapping("/add")
//    public HttpEntity<?> addConstants(@RequestBody Constants constants){
//        ApiResponse apiResponse = constantsService.addConstants(constants);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
//    }
//
//    @PutMapping("/updateBirthdayWish/{id}")
//    public HttpEntity<?> updateBirthdayWish(@PathVariable Integer id, @RequestParam String birthdayWish){
//        ApiResponse apiResponse = constantsService.updateBirthdayWishForCustomer(id, birthdayWish);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
//    }
//}
