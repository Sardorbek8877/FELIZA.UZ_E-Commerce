package uz.feliza.felizabackend.service;

import jakarta.persistence.PreUpdate;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Coupon;
import uz.feliza.felizabackend.entity.CouponDetail;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.CouponDetailDto;
import uz.feliza.felizabackend.repository.CouponDetailRepository;
import uz.feliza.felizabackend.repository.CouponRepository;
import uz.feliza.felizabackend.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CouponDetailService {

    final private CouponDetailRepository couponDetailRepository;
    final private CustomerRepository customerRepository;
    final private CouponRepository couponRepository;
    public CouponDetailService(CouponDetailRepository couponDetailRepository,
                               CustomerRepository customerRepository,
                               CouponRepository couponRepository){
        this.couponDetailRepository = couponDetailRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    public List<CouponDetail> getAllCouponsByCustomerId(Long customerId){
        return couponDetailRepository.findAllByCustomerId(customerId);
    }

    public ApiResponse assignCouponToAllCustomers(List<Customer> customerList, Long couponId, int day, String message) throws ChangeSetPersister.NotFoundException {
        // Agar list kelishi qiyin bo'lsa, kalit so'zlar bilan listni backendda olamiz
        for (Customer customer : customerList){
            addCouponToCustomer(customer, couponId, day);
            sendMessageToCustomer(customer, message);
        }
        return new ApiResponse("Kuponlar mijozlarga yuborildi!", true);
    }

    //status o'zgarganda
    public void addCouponIfStatusChanged(Long customerId, Long couponId){

    }

//    public ApiResponse addCouponDetail(CouponDetailDto couponDetailDto){
//
//        Optional<Customer> optionalCustomer = customerRepository.findById(couponDetailDto.getCustomerId());
//        if (optionalCustomer.isEmpty())
//            return new ApiResponse("Mijoz topilmadi", false);
//        Customer customer = optionalCustomer.get();
//
//        Optional<Coupon> optionalCoupon = couponRepository.findById(couponDetailDto.getCouponId());
//        if (optionalCoupon.isEmpty())
//            return new ApiResponse("Kupon topilmadi", false);
//        Coupon coupon = optionalCoupon.get();
//
//        CouponDetail couponDetail = new CouponDetail(
//                customer,
//                couponDetailDto.getStartDate(),
//                couponDetailDto.getExpireDate(),
//                couponDetailDto.isActive(),
//                coupon);
//        couponDetailRepository.save(couponDetail);
//        return new ApiResponse("Kupon " + customer.getFullName() + " ga qo'shildi", true);
//    }

    //METHODS FOR AUTOMATIC COUPONS WITH SMS SENDING
    public List<Customer> getCustomersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        return customerRepository.findByBirthDateMonthAndBirthDateDayOfMonth(today.getMonthValue(), today.getDayOfMonth());
    }

    public void addCouponToCustomer(Customer customer, Long couponId, int expireDay) throws ChangeSetPersister.NotFoundException {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        LocalDate currentDate = LocalDate.now();
        LocalDate expireDate = currentDate.plusDays(expireDay);

        CouponDetail couponDetail = new CouponDetail(customer, currentDate, expireDate, true, coupon);
        couponDetailRepository.save(couponDetail);
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
            addCouponToCustomer(customer, 1L, 10);
            sendMessageToCustomer(customer, message);
        }
    }

    @Scheduled(cron = "0 0 7 * * *", zone = "Asia/Tashkent")
    public void inActiveCoupons(){

    }
}
