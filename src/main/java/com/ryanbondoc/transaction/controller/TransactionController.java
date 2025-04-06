package com.ryanbondoc.transaction.controller;

import com.ryanbondoc.transaction.model.PaymentRequest;
import com.ryanbondoc.transaction.model.Transaction;
import com.ryanbondoc.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/pay")
    public ResponseEntity<Transaction> processPayment(@Valid @RequestBody PaymentRequest paymentRequest, @RequestParam String paymentMethod) {
        Transaction processedTransaction = transactionService.processTransaction(paymentRequest, paymentMethod);
        return new ResponseEntity<>(processedTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
