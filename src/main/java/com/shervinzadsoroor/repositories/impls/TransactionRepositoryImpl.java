package com.shervinzadsoroor.repositories.impls;

import com.shervinzadsoroor.models.Transaction;
import com.shervinzadsoroor.repositories.interfaces.TransactionRepository;

import java.util.Date;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {
    @Override
    public List<Transaction> find(Long accountId, Date date) {
        return null;
    }
}
