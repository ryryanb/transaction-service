package com.ryanbondoc.transaction.service;

import com.ryanbondoc.transaction.model.PaymentRequest;
import com.ryanbondoc.transaction.model.PaymentResponse;
import com.ryanbondoc.transaction.model.Transaction;
import com.ryanbondoc.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final PaymentGatewayService paymentGatewayService;

    public TransactionService(TransactionRepository transactionRepository, PaymentGatewayService paymentGatewayService) {
        this.transactionRepository = transactionRepository;
        this.paymentGatewayService = paymentGatewayService;
    }

    public Transaction processTransaction(PaymentRequest paymentReq, String paymentMethod) {
        Transaction transaction = new Transaction();
        transaction.setStatus("PENDING");
        transaction.setPaymentMethod(paymentMethod);
        transaction = transactionRepository.save(transaction);

        // Process payment through the selected gateway
        PaymentResponse resp = paymentGatewayService.processPayment(paymentReq);

        if (resp.getStatus().equals("success")) {
            transaction.setStatus("COMPLETED");
        } else {
            transaction.setStatus("FAILED");
        }

        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
}
