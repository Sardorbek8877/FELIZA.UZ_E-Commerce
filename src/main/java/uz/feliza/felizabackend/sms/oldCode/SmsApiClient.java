//package uz.feliza.felizabackend.sms.oldCode;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import uz.feliza.felizabackend.sms.oldCode.payload.SmsLoginResponse;
//import uz.feliza.felizabackend.sms.oldCode.payload.SmsMessageSendResponse;
//
//@RequestMapping
//@FeignClient(name = "sms-api", url = "https://notify.eskiz.uz/api")
//public interface SmsApiClient {
//
//    @PostMapping("/auth/login")
//    ResponseEntity<SmsLoginResponse> login(@RequestParam("email") String username, @RequestParam("password") String password);
//
//    @PostMapping("/message/sms/send")
//    ResponseEntity<SmsMessageSendResponse> sendSms(
//            @RequestParam("mobile_phone") String to,
//            @RequestParam("message") String text,
//            @RequestParam("from") String from,
//            @RequestParam("callback_url") String callbackUrl
//    );
//}
