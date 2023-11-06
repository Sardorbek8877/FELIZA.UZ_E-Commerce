package uz.feliza.felizabackend.entity.merchant_api.result;

import java.util.Date;
public class CancelTransactionResult {
    private Long transaction;

    private Date cancelTime;
    private int state;

    public CancelTransactionResult() {
    }

    public CancelTransactionResult(Long transaction, Date cancelTime, int state) {
        this.transaction = transaction;
        this.cancelTime = cancelTime;
        this.state = state;
    }

    public Long getTransaction() {
        return transaction;
    }

    public void setTransaction(Long transaction) {
        this.transaction = transaction;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
