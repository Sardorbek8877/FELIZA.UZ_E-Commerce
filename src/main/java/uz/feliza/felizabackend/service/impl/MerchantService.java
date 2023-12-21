//package uz.feliza.felizabackend.service.impl;
//
//import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import uz.feliza.felizabackend.entity.Order;
//import uz.feliza.felizabackend.entity.OrderTransaction;
//import uz.feliza.felizabackend.entity.enums.OrderCancelReason;
//import uz.feliza.felizabackend.entity.enums.TransactionState;
//import uz.feliza.felizabackend.entity.merchant_api.Account;
//import uz.feliza.felizabackend.entity.merchant_api.Transaction;
//import uz.feliza.felizabackend.entity.merchant_api.result.*;
//import uz.feliza.felizabackend.exception.OrderNotExistsException;
//import uz.feliza.felizabackend.exception.TransactionNotFoundException;
//import uz.feliza.felizabackend.exception.UnableCompleteException;
//import uz.feliza.felizabackend.exception.WrongAmountException;
//import uz.feliza.felizabackend.repository.OrderRepository;
//import uz.feliza.felizabackend.repository.TransactionRepository;
//import uz.feliza.felizabackend.service.IMerchantService;
//
//import java.util.*;
//
//@Service
//@AutoJsonRpcServiceImpl
//@RequiredArgsConstructor
//public class MerchantService implements IMerchantService {
//    private Long expiredTime = 43200000L;
//    private final OrderRepository orderRepository;
//    private final TransactionRepository transactionRepository;
//    Order order;
//
//    /**
//     * Merchant API Method. Docs: https://paycom.uz/api/
//     * @param id
//     * @param time
//     * @param amount
//     * @param account
//     * @return
//     * @throws WrongAmountException
//     */
//    @Override
//    public CreateTransactionResult createTransaction(String id, Date time, Double amount, Account account) throws WrongAmountException {
//        Optional<OrderTransaction> transaction = transactionRepository.findByPaymentId(id);
//        if (transaction.isEmpty()){
//            if (checkPerformTransaction(amount, account).getAllow()){
//              OrderTransaction newTransaction = new OrderTransaction(id,
//                                                                     time,
//                                                                     new Date(),
//                                                                     TransactionState.IN_PROGRESS,order);
//              transactionRepository.save(newTransaction);
//              return new CreateTransactionResult(newTransaction.getCreatedTime(),
//                                                 newTransaction.getId(),
//                                                 newTransaction.getState().getCode());
//            }
//        }else {
//            OrderTransaction existingTransaction = transaction.get();
//            if (existingTransaction.getState() == TransactionState.IN_PROGRESS){
//                if (System.currentTimeMillis() - existingTransaction.getPaymentTime().getTime() > expiredTime)
//                    throw new UnableCompleteException();
//                return new CreateTransactionResult(existingTransaction.getCreatedTime(),
//                                                   existingTransaction.getId(),
//                                                   existingTransaction.getState().getCode());
//            }else
//                throw new UnableCompleteException();
//        }
//
//        //ya'ni bu yerda transacsiyani yarata olmasa throw qilib yuboradi
//        throw new UnableCompleteException();
//    }
//
//    @Override
//    public CheckPerformTransactionResult checkPerformTransaction(Double amount, Account account) throws WrongAmountException {
//        Optional<Order> order = orderRepository.findById(account.getOrder());
//        if (order.isEmpty()) throw new OrderNotExistsException();
//        if (!Objects.equals(amount, order.get().getOrderCost())) throw new WrongAmountException();
//
//        return new CheckPerformTransactionResult(true);
//    }
//
//    /**
//     * Merchant API (Check Transaction Method)
//     * @param id
//     * @return
//     */
//    @Override
//    public CheckTransactionResult checkTransaction(String id) {
//        Optional<OrderTransaction> transaction = transactionRepository.findByPaymentId(id);
//        if (transaction.isPresent()){
//            OrderTransaction existingTransaction = transaction.get();
//            return new CheckTransactionResult(existingTransaction.getCreatedTime(),
//                    existingTransaction.getPerformTime(),
//                    existingTransaction.getCancelTime(),
//                    existingTransaction.getId(),
//                    existingTransaction.getState().getCode(),
//                    existingTransaction.getCancelReason().getCode());
//        }else
//            throw new TransactionNotFoundException();
//    }
//
//    /**
//     * Merchant API (Perform Transaction Method)
//     * @param id
//     * @return
//     */
//    @Override
//    public PerformTransactionResult performTransaction(String id) {
//        Optional<OrderTransaction> transaction = transactionRepository.findByPaymentId(id);
//        if (transaction.isPresent()){
//            OrderTransaction existingTransaction = transaction.get();
//            TransactionState state = existingTransaction.getState();
//            if (state == TransactionState.IN_PROGRESS){
//                if (System.currentTimeMillis() - existingTransaction.getPaymentTime().getTime() > expiredTime){
//
//                    state = TransactionState.CANCELLED;
//                    transactionRepository.save(existingTransaction);
//                    throw new UnableCompleteException();
//                }else {
//                    state = TransactionState.DONE;
//                    Date performTime = existingTransaction.getPerformTime();
//                    performTime = new Date();
//                    transactionRepository.save(existingTransaction);
//                    return new PerformTransactionResult(existingTransaction.getId(),
//                            existingTransaction.getPerformTime(),
//                            existingTransaction.getState().getCode());
//                }
//            } else if (state == TransactionState.DONE) {
//                return new PerformTransactionResult(existingTransaction.getId(),
//                        existingTransaction.getPerformTime(),
//                        existingTransaction.getState().getCode());
//            }else
//                throw new UnableCompleteException();
//        }else
//            throw new TransactionNotFoundException();
//    }
//
//    @Override
//    public CancelTransactionResult cancelTransaction(String id, OrderCancelReason reason) {
//        Optional<OrderTransaction> transaction = transactionRepository.findByPaymentId(id);
//        if (transaction.isPresent()){
//            OrderTransaction existTransaction = transaction.get();
//            TransactionState state = existTransaction.getState();
//            if (state==TransactionState.IN_PROGRESS){
//                state=TransactionState.CANCELLED;
//            } else if (state == TransactionState.DONE) {
//                if (existTransaction.getOrder().getDelivered()){
//                    /**
//                     * here we can check. Why we can cancel a transaction?
//                     * Or can we cancel this transaction?
//                     */
//                    throw new UnableCompleteException();
//                }else
//                    //here if order didn't get to the customer we can cancel it immediately
//                    state=TransactionState.POST_CANCELLED;
//            }else
//                state = TransactionState.CANCELLED;
//
//            Date cancelTime = existTransaction.getCancelTime();
//            cancelTime= new Date();
//
//            OrderCancelReason cancelReason = existTransaction.getCancelReason();
//            cancelReason = reason;
//
//            transactionRepository.save(existTransaction);
//            return new CancelTransactionResult(existTransaction.getId(),
//                    existTransaction.getCancelTime(),
//                    existTransaction.getState().getCode());
//        }else
//            throw new TransactionNotFoundException();
//    }
//
//    @Override
//    public Transaction getStatement(Date from, Date to) {
//        var results = new ArrayList<GetStatementResult>();
//        var transactions = transactionRepository.findByPaymentTimeAndState(from, to, TransactionState.DONE);
//        transactions.forEach(it -> results.add(new GetStatementResult(it.getPaymentId(),
//                it.getPaymentTime(),
//                it.getOrder().getOrderCost(),
//                new Account(it.getOrder().getId()),
//                it.getCreatedTime(),
//                it.getPerformTime(),
//                it.getCancelTime(),
//                it.getId(),
//                it.getState().getCode(),
//                it.getCancelReason().getCode())));
//
//        return new Transaction(results);
//    }
//
//    @Override
//    public ChangePasswordResult changePassword(String password) {
//        //todo: need to change auth password for paycom
//        return new ChangePasswordResult(true);
//    }
//}
