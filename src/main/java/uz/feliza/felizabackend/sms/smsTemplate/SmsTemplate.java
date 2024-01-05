package uz.feliza.felizabackend.sms.smsTemplate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsTemplate extends AbstractLongEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private SmsTemplateName smsName;

    private String text;
}
