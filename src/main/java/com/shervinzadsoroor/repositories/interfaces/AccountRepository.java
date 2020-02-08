package com.shervinzadsoroor.repositories.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shervinzadsoroor.models.Account;

import java.util.List;

public interface AccountRepository {
    Long create(String info) throws JsonProcessingException;

    Account retrieve(Long accountId);

    void update(Long accountId);

    void delete(Long accountId);

    List<Account> findAll(String id) throws JsonProcessingException;

    void deactivate(Long accountId);

    public String getCreateAccountInfo() throws JsonProcessingException;

    String generateCreditCardNumber();

    int generateCVV2();


}
