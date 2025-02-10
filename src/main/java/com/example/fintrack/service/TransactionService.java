package com.example.fintrack.service;

import com.example.fintrack.model.Transaction;
import com.example.fintrack.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        if (transaction.getCurrency() == null || transaction.getCurrency().isEmpty()) {
            transaction.setCurrency("BGN"); // Устанавливаем валюту по умолчанию
        }
        Transaction saved = transactionRepository.save(transaction);
        return ResponseEntity.ok(saved);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> updateTransaction(Long id, Transaction updatedTransaction) {
        return transactionRepository.findById(id).map(existingTransaction -> {
            existingTransaction.setDescription(updatedTransaction.getDescription());
            existingTransaction.setAmount(updatedTransaction.getAmount());
            existingTransaction.setDate(updatedTransaction.getDate());
            return transactionRepository.save(existingTransaction);
        });
    }

    public void deleteTransaction(Long id) {transactionRepository.deleteById(id);}

}
