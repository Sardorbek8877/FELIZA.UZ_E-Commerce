package uz.feliza.felizabackend.service.impl;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Order;
import uz.feliza.felizabackend.entity.OrderTransaction;
import uz.feliza.felizabackend.entity.enums.TransactionState;
import uz.feliza.felizabackend.entity.merchant_api.Account;
import uz.feliza.felizabackend.entity.merchant_api.result.CheckPerformTransactionResult;
import uz.feliza.felizabackend.entity.merchant_api.result.CreateTransactionResult;
import uz.feliza.felizabackend.exception.OrderNotExistsException;
import uz.feliza.felizabackend.exception.UnableCompleteException;
import uz.feliza.felizabackend.exception.WrongAmountException;
import uz.feliza.felizabackend.repository.OrderRepository;
import uz.feliza.felizabackend.repository.TransactionRepository;
import uz.feliza.felizabackend.service.IMerchantService;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@AutoJsonRpcServiceImpl
@RequiredArgsConstructor
public class MerchantService implements IMerchantService {
    private Long expiredTime = 43200000L;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    Order order;

    /**
     * Merchant API Method. Docs: https://paycom.uz/api/
     * @param id
     * @param time
     * @param amount
     * @param account
     * @return
     * @throws WrongAmountException
     */
    @Override
    public CreateTransactionResult createTransaction(String id, Date time, Double amount, Account account) throws WrongAmountException {
        Optional<OrderTransaction> transaction = transactionRepository.findByPaymentId(id);
        if (transaction.isEmpty()){
            if (checkPerformTransaction(amount, account).getAllow()){
              OrderTransaction newTransaction = new OrderTransaction(id,
                                                                     time,
                                                                     new Date(),
                                                                     TransactionState.IN_PROGRESS,order);
              transactionRepository.save(newTransaction);
              return new CreateTransactionResult(newTransaction.getCreatedTime(),
                                                 newTransaction.getId(),
                                                 newTransaction.getState().getCode());
            }
        }else {
            OrderTransaction existingTransaction = transaction.get();
            if (existingTransaction.getState() == TransactionState.IN_PROGRESS){
                if (System.currentTimeMillis() - existingTransaction.getPaymentTime().getTime() > expiredTime)
                    throw new UnableCompleteException();
                return new CreateTransactionResult(existingTransaction.getCreatedTime(),
                                                   existingTransaction.getId(),
                                                   existingTransaction.getState().getCode());
            }else
                throw new UnableCompleteException();
        }

        //ya'ni bu yerda transacsiyani yarata olmasa throw qilib yuboradi
        throw new UnableCompleteException();
    }

    @Override
    public CheckPerformTransactionResult checkPerformTransaction(Double amount, Account account) throws WrongAmountException {
        Optional<Order> order = orderRepository.findById(account.getOrder());
        if (order.isEmpty()) throw new OrderNotExistsException();
        if (!Objects.equals(amount, order.get().getOrderCost())) throw new WrongAmountException();

        return new CheckPerformTransactionResult(true);
    }
}
