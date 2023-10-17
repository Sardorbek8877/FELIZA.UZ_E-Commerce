package uz.feliza.felizabackend.sms;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.service.CouponDetailService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduledSmsSend {

    private final CouponDetailService couponDetailService;
    private final CustomerRepository customerRepository;

    public ScheduledSmsSend(CouponDetailService couponDetailService,
                            CustomerRepository customerRepository){
        this.couponDetailService = couponDetailService;
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        return customerRepository.findByBirthDateMonthAndBirthDateDayOfMonth(today.getMonthValue(), today.getDayOfMonth());
    }

    public void sendMessageToCustomer(Customer customer, String message) {
        // Implementiere die Logik zum Versenden der Geburtstagsnachricht
        // Hier könnten Sie eine E-Mail- oder Benachrichtigungsservice-API verwenden
        // Beispiel: emailService.sendBirthdayEmail(customer.getEmail(), "Alles Gute zum Geburtstag!");
    }

    @Scheduled(cron = "0 0 7 * * *", zone = "Asia/Tashkent") // 08:00 da jo'natishi kerak
    public void sendBirthdayGreetings() throws ChangeSetPersister.NotFoundException {
        List<Customer> customersWithBirthday = getCustomersWithBirthdayToday();
        String message = "FELIZA sizni tug'ilgan kuningiz bilan muborakbod etadi va sovg'a sifatida online " +
                "do'konimizdan harid uchun kupon taqdim etadi. Kupon allaqachon akkountingizga biriktirilgan.";
        for (Customer customer : customersWithBirthday){
            couponDetailService.addCouponToCustomer(customer, 1L, 10);
            sendMessageToCustomer(customer, message);
        }
    }

    @Scheduled(cron = "0 0 7 * * *", zone = "Asia/Tashkent")
    public void inActiveCoupons(){

    }
}
