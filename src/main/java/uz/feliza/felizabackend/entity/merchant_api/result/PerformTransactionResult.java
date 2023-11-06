package uz.feliza.felizabackend.entity.merchant_api.result;

import java.util.Date;

public class PerformTransactionResult {
    private Long transaction;
    private Date performTime;
    private int state;

    public PerformTransactionResult() {
    }

    public PerformTransactionResult(Long transaction, Date performTime, int state) {
        this.transaction = transaction;
        this.performTime = performTime;
        this.state = state;
    }

    public Long getTransaction() {
        return transaction;
    }

    public void setTransaction(Long transaction) {
        this.transaction = transaction;
    }

    public Date getPerformTime() {
        return performTime;
    }

    public void setPerformTime(Date performTime) {
        this.performTime = performTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
