package com.ryanbondoc.transaction.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions") // Table name in the database
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Primary Key (auto-generated)

    private String transactionId; // Unique identifier for the transaction
    private BigDecimal amountProcessed; // Amount processed in the transaction
    private String paymentMethod; // Payment method used (e.g., credit card, bank transfer)
    private String status; // Status (e.g., success, failed, pending)
    private LocalDateTime timestamp; // Timestamp of processing
    private BigDecimal feesApplied; // Fees applied (if any)
    private String transactionReference; // Reference to link to PaymentRequest ID
    private String resultOrErrorCode; // Result or error code (if any)

    // Method to generate a unique Transaction ID
    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
