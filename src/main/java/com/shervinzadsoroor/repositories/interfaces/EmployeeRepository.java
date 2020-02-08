package com.shervinzadsoroor.repositories.interfaces;

import com.shervinzadsoroor.models.Account;

import java.util.List;

public interface EmployeeRepository {
    void activateTheAccount(Long inactiveAccountId,List<Account> deactiveAccounts);

    void deleteAccount(Long accountId);
}
