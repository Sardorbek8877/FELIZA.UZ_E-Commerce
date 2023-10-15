package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.User;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

//    List<Customer> findAllByBirthDate(Month month, int day);
//    List<Customer> findByBirthDateMonthAndBirthDateDayOfMonth(int month, int day);
    @Query("SELECT c FROM Customer c WHERE MONTH(c.birthDate) = :month AND DAY(c.birthDate) = :day")
    List<Customer> findByBirthDateMonthAndBirthDateDayOfMonth(@Param("month") int month, @Param("day") int day);
}
