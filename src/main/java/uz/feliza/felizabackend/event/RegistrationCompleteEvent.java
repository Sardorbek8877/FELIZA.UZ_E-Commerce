package uz.feliza.felizabackend.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import uz.feliza.felizabackend.entity.Customer;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private Customer customer;
    private String applicationUrl;
    public RegistrationCompleteEvent(Customer customer, String applicationUrl) {
        super(customer);
        this.customer = customer;
        this.applicationUrl = applicationUrl;
    }
}
