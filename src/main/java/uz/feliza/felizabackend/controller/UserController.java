package uz.feliza.felizabackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.feliza.felizabackend.entity.User;
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
}
