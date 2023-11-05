package uz.feliza.felizabackend.entity.merchant_api.result;

import java.util.Date;
public class CheckTransactionResult {
    private Date createTime;
    private Date performTime;
    private Date cancelTime;
    private Long transaction;
    private int state;
    private int reason;
}
