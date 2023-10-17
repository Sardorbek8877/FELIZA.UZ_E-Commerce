//package uz.feliza.felizabackend.sms.oldCode;
//
//import feign.Feign;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SmsClientConfig {
//
//    public SmsApiClient smsApiClient(){
//        return Feign.builder()
//                .requestInterceptor(bearerAuthInterceptor())
//                .contract(new CustomSpringMvcContract())
//                .target(SmsApiClient.class, "https://notify.eskiz.uz/api/");
//    }
//
//    @Bean
//    public BearerAuthInterceptor bearerAuthInterceptor(){
//        return new BearerAuthInterceptor("token");
//    }
//}
