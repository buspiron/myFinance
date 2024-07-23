package pl.coderslab.myFinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.myFinance.model.Transaction;
import pl.coderslab.myFinance.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findByUserId(Integer userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> findByCategoryId(Integer categoryId) {
        return transactionRepository.findByCategoryId(categoryId);
    }

    public Optional<Transaction> findById(Integer id) {
        return transactionRepository.findById(id);
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteById(Integer id) {
        transactionRepository.deleteById(id);
    }
}