package uz.feliza.felizabackend.sms;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.service.CouponDetailService;
import uz.feliza.felizabackend.sms.smsTemplate.SmsTemplate;
import uz.feliza.felizabackend.sms.smsTemplate.SmsTemplateName;
import uz.feliza.felizabackend.sms.smsTemplate.SmsTemplateRepository;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class SendSmsService {

    private final CouponDetailService couponDetailService;
    private final CustomerRepository customerRepository;
    private final SmsTemplateRepository smsRepository;

    public SendSmsService(CouponDetailService couponDetailService,
                             CustomerRepository customerRepository,
                             SmsTemplateRepository smsRepository){
        this.couponDetailService = couponDetailService;
        this.customerRepository = customerRepository;
        this.smsRepository = smsRepository;
    }

    private final String API_URL = "http://notify.eskiz.uz/api/message/sms/send";
    private final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUzMjEsInJvbGUiOm51bGwsIm" +
            "RhdGEiOnsiaWQiOjUzMjEsIm5hbWUiOiJPT08gUElOSyBTVFlMRSBGQVNISU9OIiwiZW1haWwiOiJyYWtobWF0am9uc29iaXRvdjg4MkBn" +
            "bWFpbC5jb20iLCJyb2xlIjpudWxsLCJhcGlfdG9rZW4iOm51bGwsInN0YXR1cyI6ImluZGVidGVkIiwic21zX2FwaV9sb2dpbiI6ImVza2" +
            "l6MiIsInNtc19hcGlfcGFzc3dvcmQiOiJlJCRrIXoiLCJ1el9wcmljZSI6NTAsInVjZWxsX3ByaWNlIjoxMTUsInRlc3RfdWNlbGxfcHJp" +
            "Y2UiOm51bGwsImJhbGFuY2UiOi00MDY2NjAsImlzX3ZpcCI6MCwiaG9zdCI6InNlcnZlcjEiLCJjcmVhdGVkX2F0IjoiMjAyMy0xMC0yMF" +
            "QxMzo0MDoxMC4wMDAwMDBaIiwidXBkYXRlZF9hdCI6IjIwMjMtMTAtMjNUMTU6NDg6MTAuMDAwMDAwWiIsIndoaXRlbGlzdCI6bnVsbCwi" +
            "aGFzX3BlcmZlY3R1bSI6MCwiYmVlbGluZV9wcmljZSI6bnVsbH0sImlhdCI6MTY5ODI1NTQ1OSwiZXhwIjoxNzAwODQ3NDU5fQ.2K9JaXu" +
            "Z1v0c369odlfJFhmYYMt0D4RetMFxrecSyaM";

    private HttpURLConnection getHttpURLConnection(String phoneNumber, String customMessage) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        String postData = "mobile_phone=" + phoneNumber
                + "&message=" + customMessage
                + "&from=4546"
                + "&callback_url=" + "http://0000.uz/test.php";

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(postData);
            wr.flush();
        }
        return connection;
    }

    public void sendSmsToCustomer(String phoneNumber, String customMessage) {
        try {
            HttpURLConnection connection = getHttpURLConnection(phoneNumber, customMessage);

            int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (responseCode == 200) {
                System.out.println("SMS sent successfully.");
            } else {
                System.out.println("Failed to send SMS. Status code: " + responseCode);
                System.out.println("Response content: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApiResponse sendSmsToCustomerGroup(SmsRequestDto smsRequestDto){
        SmsTemplate bySmsName = smsRepository.findBySmsName(smsRequestDto.getSmsTemplateName());

        for (Customer customerItem : smsRequestDto.getCustomerList()){
            sendSmsToCustomer(customerItem.getPhoneNumber(), bySmsName.getText());
        }
        return new ApiResponse("SMS mijozlarga yuborildi", true);
    }

//    public void sendSMSWithVerifyCode(String phoneNumber, String code) {
//        try {
//            URL url = new URL(API_URL);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            String encodedPhoneNumber = URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);
//            String encodedCode = URLEncoder.encode(code, StandardCharsets.UTF_8);
//
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setDoOutput(true);
//
//            String postData = "mobile_phone=" + phoneNumber
//                    + "&message=" + "Tasdiqlash kodi: " + code + " Kodni begona shaxslarga bermang."
//                    + "&from=4546"
//                    + "&callback_url=" + "http://0000.uz/test.php";
//
//            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
//                wr.writeBytes(postData);
//                wr.flush();
//            }
//
//            int responseCode = connection.getResponseCode();
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String inputLine;
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            if (responseCode == 200) {
//                System.out.println("SMS sent successfully.");
//            } else {
//                System.out.println("Failed to send SMS. Status code: " + responseCode);
//                System.out.println("Response content: " + response);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public String generateRandomFourDigitNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return String.format("%04d", randomNumber);
    }

    @Scheduled(cron = "0 0 7 * * *", zone = "Asia/Tashkent") // 08:00 da jo'natishi kerak
    public void sendBirthdayGreetings() throws ChangeSetPersister.NotFoundException {
        List<Customer> customersWithBirthday = getCustomersWithBirthdayToday();

        SmsTemplate birthdayWish = smsRepository.findBySmsName(SmsTemplateName.BIRTHDAY_WISH);
        String message = birthdayWish.getText();

        for (Customer customer : customersWithBirthday){
            couponDetailService.addCouponToCustomer(customer, 1L, 10);
            sendSmsToCustomer(customer.getPhoneNumber(), message);
        }
    }

    public List<Customer> getCustomersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        return customerRepository.findByBirthDateMonthAndBirthDateDayOfMonth(today.getMonthValue(), today.getDayOfMonth());
    }
}
