package uz.feliza.felizabackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.User;
import uz.feliza.felizabackend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
