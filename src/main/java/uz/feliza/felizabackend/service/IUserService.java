package uz.feliza.felizabackend.service;

import uz.feliza.felizabackend.entity.User;
import uz.feliza.felizabackend.request.RegisterRequest;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
}
