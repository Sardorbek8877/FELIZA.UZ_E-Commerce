package uz.feliza.felizabackend.service;

import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import uz.feliza.felizabackend.entity.enums.OrderCancelReason;
import uz.feliza.felizabackend.entity.merchant_api.Account;
import uz.feliza.felizabackend.entity.merchant_api.Transaction;
import uz.feliza.felizabackend.entity.merchant_api.result.*;
import uz.feliza.felizabackend.exception.OrderNotExistsException;
import uz.feliza.felizabackend.exception.TransactionNotFoundException;
import uz.feliza.felizabackend.exception.UnableCompleteException;
import uz.feliza.felizabackend.exception.WrongAmountException;

import java.util.Date;

@JsonRpcService("/api")
public interface IMerchantService {
    @JsonRpcErrors({
            @JsonRpcError(exception = UnableCompleteException.class,
            code = -31008, message = "Unable to complete operation", data = "transaction")
    })
    CreateTransactionResult createTransaction(@JsonRpcParam(value = "id") String id,
                                              @JsonRpcParam(value = "time") Date time,
                                              @JsonRpcParam(value = "amount") Double amount,
                                              @JsonRpcParam(value = "account") Account account) throws WrongAmountException;

    @JsonRpcErrors({
            @JsonRpcError(exception = WrongAmountException.class,
            code = -31001,message = "Wrong amount", data = "amount"),

            @JsonRpcError(exception = OrderNotExistsException.class,
            code = -31050, message = "Order not found", data = "order")
    })
    CheckPerformTransactionResult checkPerformTransaction(@JsonRpcParam(value = "amount") Double amount,

                                                          @JsonRpcParam(value = "account") Account account) throws WrongAmountException;

    @JsonRpcErrors({
            @JsonRpcError(exception = TransactionNotFoundException.class,
            code = -31003,message = "Order transaction not found", data = "transaction")
    })
    CheckTransactionResult checkTransaction(@JsonRpcParam(value = "id") String id);

    @JsonRpcErrors({
            @JsonRpcError(exception = UnableCompleteException.class,
                    code = -31008, message = "Unable to complete operation", data = "transaction"),

            @JsonRpcError(exception = TransactionNotFoundException.class,
                    code = -31003,message = "Order transaction not found", data = "transaction")
    })
    PerformTransactionResult performTransaction(@JsonRpcParam(value = "id") String id);

    @JsonRpcErrors({
            @JsonRpcError(exception = UnableCompleteException.class,
                    code = -31007, message = "Unable to complete operation", data = "transaction"),

            @JsonRpcError(exception = TransactionNotFoundException.class,
                    code = -31003,message = "Order transaction not found", data = "transaction")
    })
    CancelTransactionResult cancelTransaction(@JsonRpcParam(value = "id") String id, @JsonRpcParam(value = "reason") OrderCancelReason reason);

    Transaction getStatement(@JsonRpcParam(value = "from") Date from, @JsonRpcParam(value = "to") Date to);
    ChangePasswordResult changePassword(@JsonRpcParam(value = "password") String password);

}
