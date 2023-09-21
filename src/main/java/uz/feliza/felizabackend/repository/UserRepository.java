package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.feliza.felizabackend.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, PagingAndSortingRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
