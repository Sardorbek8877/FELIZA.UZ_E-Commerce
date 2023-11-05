package uz.feliza.felizabackend.service;

import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import uz.feliza.felizabackend.entity.merchant_api.Account;
import uz.feliza.felizabackend.entity.merchant_api.result.CheckPerformTransactionResult;
import uz.feliza.felizabackend.entity.merchant_api.result.CreateTransactionResult;
import uz.feliza.felizabackend.exception.OrderNotExistsException;
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
}
