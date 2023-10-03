package uz.feliza.felizabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Color;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.ColorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService {

    private final ColorRepository colorRepository;
    public ColorService(ColorRepository colorRepository){
        this.colorRepository = colorRepository;
    }

    public List<Color> getAllColor(){
        return colorRepository.findAll();
    }

    public ApiResponse getColorById(Long id){
        Optional<Color> optionalColor = colorRepository.findById(id);
        return optionalColor.map(color -> new ApiResponse(id + "IDli rang: ", true, color))
                .orElseGet(() -> new ApiResponse("Rang topilmadi", false));
    }

    public ApiResponse addColor(Color color){
        boolean existsByNameUZB = colorRepository.existsByNameUZB(color.getNameUZB());
        if (existsByNameUZB)
            return new ApiResponse("Bunday rang mavjud!", false);

        colorRepository.save(color);
        return new ApiResponse("Rang yaratildi", true);
    }

    public ApiResponse editColor(Long id, Color color){
        Optional<Color> optionalColor = colorRepository.findById(id);
        if (optionalColor.isEmpty())
            return new ApiResponse("Rang topilmadi", false);
        Color editingColor = optionalColor.get();

        editingColor.setNameUZB(color.getNameUZB());
        editingColor.setNameRUS(color.getNameRUS());
        editingColor.setColorCode(color.getColorCode());

        colorRepository.save(editingColor);
        return new ApiResponse("Rang o'zgartirildi", true);
    }

    public ApiResponse deleteColor(Long id){
        Optional<Color> optionalColor = colorRepository.findById(id);
        if (optionalColor.isEmpty())
            return new ApiResponse("Rang topilmadi", false);
        colorRepository.deleteById(id);
        return new ApiResponse("Rang o'chirildi", true);
    }
}
