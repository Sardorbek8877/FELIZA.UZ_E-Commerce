//package uz.feliza.felizabackend.sms.oldCode;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//
//public class BearerAuthInterceptor implements RequestInterceptor {
//
//    private String token = "token";
//
//    public BearerAuthInterceptor(String token){
//        this.token = token;
//    }
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        requestTemplate.header(
//                "Authorization", String.format("Bearer %s", token)
//        );
//    }
//}
