package uz.feliza.felizabackend.entity.merchant_api.result;

import uz.feliza.felizabackend.entity.merchant_api.Account;

import java.util.Date;

public class GetStatementResult {
    private Integer id;
    private Date time;
    private double amount;
    private Account account;
    private Date creatTime;
    private Date performTime;
    private Date cancelTime;
    private Long transaction;
    private int state;
    private int reason;
}
