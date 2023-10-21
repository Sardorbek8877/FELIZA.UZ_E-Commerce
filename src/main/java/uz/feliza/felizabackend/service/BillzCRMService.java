package uz.feliza.felizabackend.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.feliza.felizabackend.entity.Order;
import uz.feliza.felizabackend.entity.Product;
import uz.feliza.felizabackend.entity.ProductSizeVariant;
import uz.feliza.felizabackend.payload.ProductSizeVariantDto;

@Service
public class BillzCRMService {
    private final RestTemplate restTemplate;

    public BillzCRMService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendProductToCRM(ProductSizeVariant productSizeVariant, Product product) {
        // Definieren Sie die URL und den Request-Body
        String crmApiUrl = "https://api.billz.uz/v1/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer" + "token");

        String requestBody = "{\"jsonrpc\": \"2.0\",\"method\": \"import.createWithOffice\",\"params\": {\"items\": " +
                "[{\"id\": \"" + productSizeVariant.getId() + "\",\"sku\": \"8739eb18-0d03-11ea-80d9-000c29f3b2cd\"," +
                "\"category\": \"" + product.getCategory() + "\",\"name\": \"" + product.getNameUZB() + "\",\"price\":" +
                " " + product.getPrice() + ",\"details\": [{\"officeID\": 1115,\"quantity\": " + productSizeVariant.getQuantity() +
                "}]}]},\"id\": \"1200\"}";


        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Senden Sie den POST-Request an die CRM-System-API
        ResponseEntity<String> response = restTemplate.postForEntity(crmApiUrl, requestEntity, String.class);

        // Verarbeiten Sie die API-Antwort, z.B. prüfen Sie den Statuscode und die Antwortdaten.
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            System.out.println("Billz API ga mahsulot muvaffaqiyatli qo'shildi");
        } else {
            System.out.println("Billz API ga so'rov yuborishda xatolik!");
        }
    }

    public void sendOrderToCRM(Order order){
        String apiUrl = "https://api.billz.uz/v1/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer" + "token");
        String requestBody = "{\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"method\": \"orders.create\",\n" +
                "    \"params\": {\n" +
                "        \"orderID\": 45,\n" +
                "        \"dateCreated\": \"" + order.getOrderTime() +"\",\n" +
                "        \"datePaid\": \"" + order.getCreatedAt() + "\",\n" +
                "        \"paymentMethod\": \"" + order.getPaymentMethod() + "\",\n" +
                "        \"subTotalPrice\": " + order.getOrderCost() + ",\n" +
                "        \"discountAmount\": 3000,\n" +
                "        \"totalPrice\": 42990,\n" +
                "        \"parked\": true,\n" +
                "        \"products\": [\"billzOfficeID\": 70,\"billzProductID\": 61623,\"productID\": 22341,\n" +
                "                \"name\": \"Рубашка\",\"sku\": \"lan.192\",\"barCode\": \"3381000332000\",\n" +
                "                \"qty\": 1,\"subTotalPrice\": 45990,\"discountAmount\": 45990,\"totalPrice\": 45990\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);

        System.out.println("API Response: " + response);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
