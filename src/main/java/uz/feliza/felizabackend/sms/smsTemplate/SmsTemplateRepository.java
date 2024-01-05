package uz.feliza.felizabackend.sms.smsTemplate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsTemplateRepository extends JpaRepository<SmsTemplate, Long> {

    SmsTemplate findBySmsName(SmsTemplateName smsName);
}
