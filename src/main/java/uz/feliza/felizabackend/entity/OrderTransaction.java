package uz.feliza.felizabackend.entity;

import jakarta.persistence.*;
import uz.feliza.felizabackend.entity.enums.OrderCancelReason;
import uz.feliza.felizabackend.entity.enums.TransactionState;

import java.util.Date;

@Entity(name = "order_transactions")
public class OrderTransaction {
    /**
     * ushbu tablening o'zini unique id si
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * bu to'lov qilingan vaqti to'lovni o'zining Id si
     */
    private String paymentId;

    /**
     * bu to'lov qilingan vaqti (qachon to'lov qilinsa shu columnga tushadi)
     */
    private Date paymentTime;

    /**
     * Yaratilgan vaqti
     */
    private Date createdTime;

    /**
     * Ko'rsatadigan vaqti
     */
    private Date performTime;

    /**
     * shunday holatlar bo'lishi mumkin ya'ni bunda to'lovni bekor qilib puli egasiga qaytarib beriladi
     */
    private Date cancelTime;

    /**
     * Nima sababdan mahsulot qaytarilganligini sababi ko'rsatiladi
     */
    private OrderCancelReason cancelReason;

    /**
     * Transaksiyani holatlari bu
     */
    private TransactionState state;

    /**
     * Bu esa order bilan qanday bog'langanligi
     */
    @OneToOne
    private Order order;

    public OrderTransaction() {
    }

    public OrderTransaction(String paymentId, Date paymentTime, Date createdTime,TransactionState state, Order order) {
        this.paymentId = paymentId;
        this.paymentTime = paymentTime;
        this.createdTime = createdTime;
        this.state = state;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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

    public OrderCancelReason getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(OrderCancelReason cancelReason) {
        this.cancelReason = cancelReason;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
