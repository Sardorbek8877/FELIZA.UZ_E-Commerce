package uz.feliza.felizabackend.entity.merchant_api.result;

public class ChangePasswordResult {
    private Boolean success;

    public ChangePasswordResult() {
    }

    public ChangePasswordResult(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
