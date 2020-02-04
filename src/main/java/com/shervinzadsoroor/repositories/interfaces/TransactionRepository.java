package com.shervinzadsoroor.repositories.interfaces;

import com.shervinzadsoroor.models.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionRepository {
    List<Transaction> find(Long accountId, Date date);
}
