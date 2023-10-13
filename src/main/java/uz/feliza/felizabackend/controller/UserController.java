package uz.feliza.felizabackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.User;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.UserDto;
import uz.feliza.felizabackend.service.UserService;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("{userId}")
    public HttpEntity<?> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping
    public HttpEntity<?> addUser(@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }
}
