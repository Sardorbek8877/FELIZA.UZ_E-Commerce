package uz.feliza.felizabackend.entity.merchant_api;

public class Account {
    private Long order;

    public Long getOrder() {
        return order;
    }

    public Account(Long order) {
        this.order = order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}
