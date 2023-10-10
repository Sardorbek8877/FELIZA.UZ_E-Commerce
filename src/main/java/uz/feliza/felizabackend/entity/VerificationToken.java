package uz.feliza.felizabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Date expirationTime;
    private static final int EXPIRATION_TIME = 15;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public VerificationToken(String token, Customer customer){
        super();
        this.token = token;
        this.customer = customer;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public VerificationToken(String token){
        super();
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public VerificationToken() {

    }

    public Date getTokenExpirationTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
