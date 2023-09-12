package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Color;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.ColorRepository;

@Service
public class ColorService {

    @Autowired
    ColorRepository colorRepository;

    public ApiResponse addColor(Color color){
        boolean existsByNameUZB = colorRepository.existsByNameUZB(color.getNameUZB());
        if (existsByNameUZB)
            return new ApiResponse("Bunday rang mavjud!", false);

        Color color1 = new Color(
                color.getNameUZB(),
                color.getNameRUS(),
                color.getColorCode()
        );

        colorRepository.save(color);
        return new ApiResponse("Rang yaratildi", true);
    }
}
