package uz.feliza.felizabackend.sms;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.feliza.felizabackend.payload.ApiResponse;

@RestController
@RequestMapping("api/sms")
public class SendSmsController {

    private final SendSmsService sendSmsService;
    public SendSmsController(SendSmsService sendSmsService){
        this.sendSmsService = sendSmsService;
    }

    @PostMapping("/toCustomerGroup")
    public ResponseEntity<?> sendSmsToCustomerGroup(@RequestBody SmsRequestDto smsRequestDto){
        ApiResponse apiResponse = sendSmsService.sendSmsToCustomerGroup(smsRequestDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
