package uz.feliza.felizabackend.exception;

public class PasswordEmptyException extends RuntimeException {
    public PasswordEmptyException(String s) {
        super(s);
    }
}
