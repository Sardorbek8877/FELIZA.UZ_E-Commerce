package uz.feliza.felizabackend.entity.merchant_api.result;
public class CheckPerformTransactionResult {
    private Boolean allow = false;

    public CheckPerformTransactionResult(Boolean allow){
        this.allow = allow;
    }

    public Boolean getAllow() {
        return allow;
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }
}
