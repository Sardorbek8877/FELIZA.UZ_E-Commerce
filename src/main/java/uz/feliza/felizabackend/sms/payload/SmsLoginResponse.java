package uz.feliza.felizabackend.sms.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsLoginResponse {

    private String message;
    private Map<String, String> data;
}
