//package uz.feliza.felizabackend.sms;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//import java.util.Random;
//
//@Data
//public class SmsConfig {
//
//    private final String API_URL = "http://notify.eskiz.uz/api/message/sms/send";
//    private final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUzMjEsInJvbGUiOm51bGwsIm" +
//            "RhdGEiOnsiaWQiOjUzMjEsIm5hbWUiOiJPT08gUElOSyBTVFlMRSBGQVNISU9OIiwiZW1haWwiOiJyYWtobWF0am9uc29iaXRvdjg4MkBn" +
//            "bWFpbC5jb20iLCJyb2xlIjpudWxsLCJhcGlfdG9rZW4iOm51bGwsInN0YXR1cyI6ImluZGVidGVkIiwic21zX2FwaV9sb2dpbiI6ImVza2" +
//            "l6MiIsInNtc19hcGlfcGFzc3dvcmQiOiJlJCRrIXoiLCJ1el9wcmljZSI6NTAsInVjZWxsX3ByaWNlIjoxMTUsInRlc3RfdWNlbGxfcHJp" +
//            "Y2UiOm51bGwsImJhbGFuY2UiOi00MDY2NjAsImlzX3ZpcCI6MCwiaG9zdCI6InNlcnZlcjEiLCJjcmVhdGVkX2F0IjoiMjAyMy0xMC0yMF" +
//            "QxMzo0MDoxMC4wMDAwMDBaIiwidXBkYXRlZF9hdCI6IjIwMjMtMTAtMjNUMTU6NDg6MTAuMDAwMDAwWiIsIndoaXRlbGlzdCI6bnVsbCwi" +
//            "aGFzX3BlcmZlY3R1bSI6MCwiYmVlbGluZV9wcmljZSI6bnVsbH0sImlhdCI6MTY5ODI1NTQ1OSwiZXhwIjoxNzAwODQ3NDU5fQ.2K9JaXu" +
//            "Z1v0c369odlfJFhmYYMt0D4RetMFxrecSyaM";
//
//}