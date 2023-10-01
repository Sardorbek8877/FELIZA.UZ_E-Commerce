package uz.feliza.felizabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FelizaBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(FelizaBackEndApplication.class, args);
    }

}
