package uz.feliza.felizabackend.entity.merchant_api.result;

import java.util.Date;

public class CreateTransactionResult {
    private Date createTime;
    private Long transaction;
    private int state;

    public CreateTransactionResult(Date createTime, Long transaction, int state) {
        this.createTime = createTime;
        this.transaction = transaction;
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}
