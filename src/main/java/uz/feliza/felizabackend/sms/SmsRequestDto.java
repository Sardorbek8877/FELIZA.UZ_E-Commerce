package uz.feliza.felizabackend.sms;

import lombok.Data;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.sms.smsTemplate.SmsTemplateName;

import java.util.List;

@Data
public class SmsRequestDto {

    private List<Customer> customerList;

    private SmsTemplateName smsTemplateName;
}
