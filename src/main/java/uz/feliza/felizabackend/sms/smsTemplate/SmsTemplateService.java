package uz.feliza.felizabackend.sms.smsTemplate;

import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.payload.ApiResponse;

import java.util.List;

@Service
public class SmsTemplateService {
    private final SmsTemplateRepository smsTemplateRepository;

    public SmsTemplateService(SmsTemplateRepository smsTemplateRepository){
        this.smsTemplateRepository = smsTemplateRepository;
    }

    public List<SmsTemplate> getAllSmsTemplates(){
        return smsTemplateRepository.findAll();
    }

    public ApiResponse getBySmsTemplateName(SmsTemplateName smsTemplateName){
        SmsTemplate bySmsName = smsTemplateRepository.findBySmsName(smsTemplateName);
        return new ApiResponse(bySmsName.getText(), true);
    }

    public ApiResponse addSms(SmsTemplateRequestDto smsRequestDto){
        SmsTemplate bySmsName = smsTemplateRepository.findBySmsName(smsRequestDto.getSmsName());
        if (bySmsName != null){
            return new ApiResponse("Bunday nomli shablon allaqachon mavjud, uni faqat o'zgartirish mumkin!", false, bySmsName);
        }

        SmsTemplate sms = new SmsTemplate();
        sms.setSmsName(smsRequestDto.getSmsName());
        sms.setText(smsRequestDto.getText());

        smsTemplateRepository.save(sms);
        return new ApiResponse("SMS shablon yaratildi", true);
    }

    public ApiResponse editSmsTemplate(SmsTemplateName smsTemplateName, String text){
        SmsTemplate bySmsName = smsTemplateRepository.findBySmsName(smsTemplateName);

        bySmsName.setText(text);

        smsTemplateRepository.save(bySmsName);

        return new ApiResponse("Shablon o'zgartirildi", true, bySmsName);
    }

}
