package uz.feliza.felizabackend.sms;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class SmsConfig {

    private static final String API_URL = "http://notify.eskiz.uz/api/message/sms/send";
    private static final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUyOTYsInJvbGUiOm51bGwsIm" +
            "RhdGEiOnsiaWQiOjUyOTYsIm5hbWUiOiJTYXJkb3JiZWsgU2FmYXJvdiIsImVtYWlsIjoic2FyZG9yYmVrc2FmYXJvdjg4NDRAZ21haWwu" +
            "Y29tIiwicm9sZSI6bnVsbCwiYXBpX3Rva2VuIjpudWxsLCJzdGF0dXMiOiJhY3RpdmUiLCJzbXNfYXBpX2xvZ2luIjoiZXNraXoyIiwic21" +
            "zX2FwaV9wYXNzd29yZCI6ImUkJGsheiIsInV6X3ByaWNlIjo1MCwidWNlbGxfcHJpY2UiOjExNSwidGVzdF91Y2VsbF9wcmljZSI6bnVsbC" +
            "wiYmFsYW5jZSI6NTAwMCwiaXNfdmlwIjowLCJob3N0Ijoic2VydmVyMSIsImNyZWF0ZWRfYXQiOiIyMDIzLTEwLTE3VDE3OjMzOjQ4LjAwM" +
            "DAwMFoiLCJ1cGRhdGVkX2F0IjoiMjAyMy0xMC0xOFQwNDoyNjozOS4wMDAwMDBaIiwid2hpdGVsaXN0IjpudWxsLCJoYXNfcGVyZmVjdHVt" +
            "IjowLCJiZWVsaW5lX3ByaWNlIjpudWxsfSwiaWF0IjoxNjk3NjE1NTQ1LCJleHAiOjE3MDAyMDc1NDV9.46CXIuPF6Ow0dRzP-s-rCFbLTx" +
            "L2VLTXbLozEwIvXEk";

    public static void main(String[] args) {
        SmsConfig smsSender = new SmsConfig();
        String phoneNumber = "998900133442";
        String code = smsSender.generateRandomFourDigitNumber();

        smsSender.sendSMS(phoneNumber, code);

        String message = "SMS API Test qilinmoqda";
        smsSender.sendCustomSMS(phoneNumber, message);
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
}


//import org.springframework.stereotype.Controller;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Random;
//
//@Controller
//public class SmsSenderController {
//
//    private static final String API_URL = "http://notify.eskiz.uz/api/message/sms/" + "send";
//
//    private static final String BEARER_TOKEN = "TOKEN";
//
//    public static void main(String[] args) {
//        String phoneNumber = "numbrt";
//        String message = "Eskiz Test";
//        String from = "4546";
//
//        String callbackUrl = "http://your_callback_url_here";
//        sendSMS(phoneNumber, message, from, callbackUrl);
//    }
//
//    public  void sendSMS(String phoneNumber , String code ) {
//        try {
//            URL url = new URL(API_URL);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            String callbackUrl = "http://localhost:8081/test/sms";
//            String message = "Tasdiqlash kodi: " + code +" Kodni begona shaxslarga bermang." ;
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            String from = "4546";
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//
//            String postData = "mobile_phone=" + phoneNumber
//                    + "&message=" + message
//                    + "&from=" + from
//                    + "&callback_url=" + callbackUrl;
//
//            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//            wr.writeBytes(postData);
//            wr.flush();
//            wr.close();
//
//            int responseCode = connection.getResponseCode();
//            BufferedReader in = new
//                    BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
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
//
//    }
//    public String generateRandomFourDigitNumber() {
//        Random random = new Random();
//        int randomNumber = random.nextInt(10000); // Generate a random number between 0 (inclusive) and 10000 (exclusive)
//        return String.format("%04d", randomNumber); // Format the number with leading zeros if necessary
//    }
//}
