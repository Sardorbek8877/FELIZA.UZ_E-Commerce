package uz.feliza.felizabackend.event;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.User;
import uz.feliza.felizabackend.service.AuthService;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final AuthService authService;
    private final JavaMailSender mailSender;
    private Customer customer;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        //1. get the new registered user
        customer = event.getCustomer();

        //2. create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();

        //3. save a verification token for the user
        authService.saveCustomerVerificationToken(customer, verificationToken);

        //4. build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/api/v1/auth/register/verifyEmail?token=" + verificationToken;

        //5. send the email to the user
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration: {}", url);
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Verification Email";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ customer.getFullName()+ ", </p>"+
                "<p>Thank you for registering with us, " +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("nodirbeknurqulov096@gmail.com", senderName);
        messageHelper.setTo(customer.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
