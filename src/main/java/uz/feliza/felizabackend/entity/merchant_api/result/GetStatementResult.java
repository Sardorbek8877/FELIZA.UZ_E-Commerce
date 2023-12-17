package uz.feliza.felizabackend.entity.merchant_api.result;

import uz.feliza.felizabackend.entity.merchant_api.Account;

import java.util.Date;

public class GetStatementResult {
    private String id;
    private Date time;
    private double amount;
    private Account account;
    private Date creatTime;
    private Date performTime;
    private Date cancelTime;
    private Long transaction;
    private int state;
    private int reason;
    public GetStatementResult() {
    }

    public GetStatementResult(String id, Date time, double amount, Account account, Date creatTime, Date performTime, Date cancelTime, Long transaction, int state, int reason) {
        this.id = id;
        this.time = time;
        this.amount = amount;
        this.account = account;
        this.creatTime = creatTime;
        this.performTime = performTime;
        this.cancelTime = cancelTime;
        this.transaction = transaction;
        this.state = state;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
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
