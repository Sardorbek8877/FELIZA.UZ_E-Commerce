package uz.feliza.felizabackend.sms;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfigProperties {

    @Value("${sms.message.mail}")
    private String login;
    @Value("${sms.pass}")
    private String password;
    @Value("${sms.from}")
    private String from;
}
