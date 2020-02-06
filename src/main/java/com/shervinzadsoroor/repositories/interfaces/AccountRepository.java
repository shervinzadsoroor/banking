package com.shervinzadsoroor.repositories.interfaces;

import com.shervinzadsoroor.models.Account;

import java.util.List;

public interface AccountRepository {
    void create();

    Account retrieve(Long accountId);

    void update(Long accountId);

    void delete(Long accountId);

    List<Account> findAll(Long costumerId);

    void deactivate(Long accountId);
}
