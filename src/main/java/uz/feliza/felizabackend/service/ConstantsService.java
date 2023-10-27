package uz.feliza.felizabackend.service;

import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Constants;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.ConstantsRepository;

import java.util.Optional;

@Service
public class ConstantsService {

    private final ConstantsRepository constantsRepository;

    public ConstantsService(ConstantsRepository constantsRepository){
        this.constantsRepository = constantsRepository;
    }

    public ApiResponse addConstants(Constants constants){

        return null;
    }

    public ApiResponse updateBirthdayWishForCustomer(Integer id, String birthdayWish){
        Optional<Constants> optionalConstants = constantsRepository.findById(id);
        if (optionalConstants.isEmpty())
            return new ApiResponse("Constant obyekti topilmadi", false);

        Constants constants = optionalConstants.get();
        constants.setBirthdayWishForCustomer(birthdayWish);
        constantsRepository.save(constants);
        return new ApiResponse("Tabrik o'zgartirildi", true, constants.getBirthdayWishForCustomer());
    }
}
