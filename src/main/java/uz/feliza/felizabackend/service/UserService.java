package uz.feliza.felizabackend.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.AbstractPastInstantBasedValidator;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.User;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.UserDto;
import uz.feliza.felizabackend.repository.RoleRepository;
import uz.feliza.felizabackend.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public ApiResponse getUserById(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            return new ApiResponse("Hodim topilmadi", false);
        User user = optionalUser.get();
        return new ApiResponse("Hodim: ", true, user);
    }

    public ApiResponse addUser(UserDto userDto){
        Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
        if (byEmail.isPresent())
            return new ApiResponse("Bunday emailli hodim allaqachon mavjud", false);

        Set<Role> roles = new HashSet<>();
        for (Long roleId : userDto.getRoles()){
            Optional<Role> optionalRole = roleRepository.findById(roleId);
            if (optionalRole.isEmpty())
                return new ApiResponse("Bunday Role topilmadi", false);
            roles.add(optionalRole.get());
        }

        User user = new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getBirthDate(),
                userDto.getPhoneNumber(),
                userDto.getPassword(),
                userDto.getEmail(),
                null,
                userDto.isEnabled(),
                roles);

        userRepository.save(user);
        return  new ApiResponse("Hodim akkounti yaratildi", true, user);
    }

    //changeUsernameAndPassword
}
