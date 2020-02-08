package com.shervinzadsoroor.repositories.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shervinzadsoroor.models.Transaction;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> find(Long accountId, String date);

    List<Transaction> findByAccountId(Long accountId);

    void printTransactions(List<Transaction> transactions);

    List<Transaction> findByDateAndAccountId(Long accountId,String strDate) throws JsonProcessingException;
}
