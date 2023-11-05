package uz.feliza.felizabackend.entity.enums;

public enum OrderCancelReason {
    RECEIVER_NOT_FOUND(1),
    DEBIT_OPERATOR_ERROR(2),
    TRANSACTION_ERROR(3),
    TRANSACTION_TIMEOUT(4),
    MONEY_BACK(5),
    UNKNOWN_ERROR(10);
    private final int cancelReason;
    OrderCancelReason(int cancelReason){
        this.cancelReason = cancelReason;
    }

    int getCancelReason(){
        return cancelReason;
    }
}
