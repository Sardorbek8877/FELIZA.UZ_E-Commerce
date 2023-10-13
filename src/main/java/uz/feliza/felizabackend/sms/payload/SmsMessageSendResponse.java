package uz.feliza.felizabackend.sms.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsMessageSendResponse {

    private String id;
    private String message;
    private String status;
}
