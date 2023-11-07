package uz.feliza.felizabackend.entity.merchant_api;

import uz.feliza.felizabackend.entity.merchant_api.result.GetStatementResult;

import java.util.List;

public class Transaction {
    private List<GetStatementResult> transactions;

    public Transaction() {
    }

    public Transaction(List<GetStatementResult> transactions) {
        this.transactions = transactions;
    }

    public List<GetStatementResult> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<GetStatementResult> transactions) {
        this.transactions = transactions;
    }
}
