package uz.feliza.felizabackend.sms;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Scheduled;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.service.CouponDetailService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class SmsService {

    private final String API_URL = "http://notify.eskiz.uz/api/message/sms/send";
    private final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUzMjEsInJvbGUiOm51bGwsIm" +
            "RhdGEiOnsiaWQiOjUzMjEsIm5hbWUiOiJPT08gUElOSyBTVFlMRSBGQVNISU9OIiwiZW1haWwiOiJyYWtobWF0am9uc29iaXRvdjg4MkBn" +
            "bWFpbC5jb20iLCJyb2xlIjpudWxsLCJhcGlfdG9rZW4iOm51bGwsInN0YXR1cyI6ImluZGVidGVkIiwic21zX2FwaV9sb2dpbiI6ImVza2" +
            "l6MiIsInNtc19hcGlfcGFzc3dvcmQiOiJlJCRrIXoiLCJ1el9wcmljZSI6NTAsInVjZWxsX3ByaWNlIjoxMTUsInRlc3RfdWNlbGxfcHJp" +
            "Y2UiOm51bGwsImJhbGFuY2UiOi00MDY2NjAsImlzX3ZpcCI6MCwiaG9zdCI6InNlcnZlcjEiLCJjcmVhdGVkX2F0IjoiMjAyMy0xMC0yMF" +
            "QxMzo0MDoxMC4wMDAwMDBaIiwidXBkYXRlZF9hdCI6IjIwMjMtMTAtMjNUMTU6NDg6MTAuMDAwMDAwWiIsIndoaXRlbGlzdCI6bnVsbCwi" +
            "aGFzX3BlcmZlY3R1bSI6MCwiYmVlbGluZV9wcmljZSI6bnVsbH0sImlhdCI6MTY5ODI1NTQ1OSwiZXhwIjoxNzAwODQ3NDU5fQ.2K9JaXu" +
            "Z1v0c369odlfJFhmYYMt0D4RetMFxrecSyaM";

    private final CouponDetailService couponDetailService;
    private final CustomerRepository customerRepository;

    public SmsService(CouponDetailService couponDetailService,
                      CustomerRepository customerRepository){
        this.couponDetailService = couponDetailService;
        this.customerRepository = customerRepository;
    }

    public void sendSMS(String phoneNumber, String code) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String encodedPhoneNumber = URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);
            String encodedCode = URLEncoder.encode(code, StandardCharsets.UTF_8);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String postData = "mobile_phone=" + phoneNumber
                    + "&message=" + "Tasdiqlash kodi: " + code + " Kodni begona shaxslarga bermang."
                    + "&from=4546"
                    + "&callback_url=" + "http://0000.uz/test.php";

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(postData);
                wr.flush();
            }

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

    public void sendCustomSMS(String phoneNumber, String customMessage) {
        try {
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

    public String generateRandomFourDigitNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return String.format("%04d", randomNumber);
    }

    public List<Customer> getCustomersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        return customerRepository.findByBirthDateMonthAndBirthDateDayOfMonth(today.getMonthValue(), today.getDayOfMonth());
    }

    public void sendMessageToCustomer(Customer customer, String message) {
        // Implementiere die Logik zum Versenden der Geburtstagsnachricht
        // Hier könnten Sie eine E-Mail- oder Benachrichtigungsservice-API verwenden
        // Beispiel: emailService.sendBirthdayEmail(customer.getEmail(), "Alles Gute zum Geburtstag!");
    }

    @Scheduled(cron = "0 0 7 * * *", zone = "Asia/Tashkent") // 08:00 da jo'natishi kerak
    public void sendBirthdayGreetings() throws ChangeSetPersister.NotFoundException {
        List<Customer> customersWithBirthday = getCustomersWithBirthdayToday();
        String message = "FELIZA sizni tug'ilgan kuningiz bilan muborakbod etadi va sovg'a sifatida online " +
                "do'konimizdan harid uchun kupon taqdim etadi. Kupon allaqachon akkountingizga biriktirilgan.";
        for (Customer customer : customersWithBirthday){
            couponDetailService.addCouponToCustomer(customer, 1L, 10);
            sendMessageToCustomer(customer, message);
        }
    }

    @Scheduled(cron = "0 0 7 * * *", zone = "Asia/Tashkent")
    public void inActiveCoupons(){

    }
}
