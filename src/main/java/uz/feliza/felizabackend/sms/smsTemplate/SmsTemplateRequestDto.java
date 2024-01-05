package uz.feliza.felizabackend.sms.smsTemplate;

import lombok.Data;

@Data
public class SmsTemplateRequestDto {
    private SmsTemplateName smsName;
    private String text;
}
