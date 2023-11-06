package uz.feliza.felizabackend.entity.merchant_api.result;

import java.util.Date;
public class CheckTransactionResult {
    private Date createTime;
    private Date performTime;
    private Date cancelTime;
    private Long transaction;
    private int state;
    private int reason;

    public CheckTransactionResult() {
    }

    public CheckTransactionResult(Date createTime, Date performTime, Date cancelTime, Long transaction, int state, int reason) {
        this.createTime = createTime;
        this.performTime = performTime;
        this.cancelTime = cancelTime;
        this.transaction = transaction;
        this.state = state;
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPerformTime() {
        return performTime;
    }

    public void setPerformTime(Date performTime) {
        this.performTime = performTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Long getTransaction() {
        return transaction;
    }

    public void setTransaction(Long transaction) {
        this.transaction = transaction;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }
}
