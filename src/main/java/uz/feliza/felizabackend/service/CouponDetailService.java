package uz.feliza.felizabackend.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Coupon;
import uz.feliza.felizabackend.entity.CouponDetail;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.repository.CouponDetailRepository;
import uz.feliza.felizabackend.repository.CouponRepository;
import uz.feliza.felizabackend.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CouponDetailService {

    final private CouponDetailRepository couponDetailRepository;
    final private CustomerRepository customerRepository;
    final private CouponRepository couponRepository;
//    final private ScheduledSmsSend scheduledSmsSend;
    public CouponDetailService(CouponDetailRepository couponDetailRepository,
                               CustomerRepository customerRepository,
                               CouponRepository couponRepository
//                               ScheduledSmsSend scheduledSmsSend
    ){
        this.couponDetailRepository = couponDetailRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
//        this.scheduledSmsSend = scheduledSmsSend;
    }

    public List<CouponDetail> getAllCouponsByCustomerId(Long customerId){
        return couponDetailRepository.findAllByCustomerId(customerId);
    }

//    public ApiResponse assignCouponToAllCustomers(List<Customer> customerList, Long couponId, int day, String message) throws ChangeSetPersister.NotFoundException {
//        // Agar list kelishi qiyin bo'lsa, kalit so'zlar bilan listni backendda olamiz
//        for (Customer customer : customerList){
//            addCouponToCustomer(customer, couponId, day);
//            scheduledSmsSend.sendMessageToCustomer(customer, message);
//        }
//        return new ApiResponse("Kuponlar mijozlarga yuborildi!", true);
//    }

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


    public void addCouponToCustomer(Customer customer, Long couponId, int expireDay) throws ChangeSetPersister.NotFoundException {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        LocalDate currentDate = LocalDate.now();
        LocalDate expireDate = currentDate.plusDays(expireDay);

        CouponDetail couponDetail = new CouponDetail(customer, currentDate, expireDate, true, coupon);
        couponDetailRepository.save(couponDetail);
    }


}
