package uz.feliza.felizabackend.sms.smsTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.payload.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sms")
public class SmsTemplateController {

    private final SmsTemplateService smsTemplateService;
    public SmsTemplateController(SmsTemplateService smsTemplateService){
        this.smsTemplateService = smsTemplateService;
    }

    @GetMapping("/enums/smsNames")
    public ResponseEntity<List<String>> getSmsNames() {
        List<String> smsNames = Arrays.stream(SmsTemplateName.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(smsNames);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllSmsTemplates(){
        List<SmsTemplate> allSmsTemplates = smsTemplateService.getAllSmsTemplates();
        return ResponseEntity.ok(allSmsTemplates);
    }

    @GetMapping("/getBySmsTemplateName/{smsTemplateName}")
    public ResponseEntity<?> getBySmsTemplateName( @PathVariable SmsTemplateName smsTemplateName){
        ApiResponse bySmsTemplateName = smsTemplateService.getBySmsTemplateName(smsTemplateName);
        return ResponseEntity.status(bySmsTemplateName.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(bySmsTemplateName);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createSms(@RequestBody SmsTemplateRequestDto smsRequestDto){
        ApiResponse apiResponse = smsTemplateService.addSms(smsRequestDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping("/edit/{smsTemplateName}")
    public ResponseEntity<?> editSmsTemplate(@PathVariable SmsTemplateName smsTemplateName, @RequestParam String text){
        ApiResponse apiResponse = smsTemplateService.editSmsTemplate(smsTemplateName, text);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
