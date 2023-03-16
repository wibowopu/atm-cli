package com.example.wsd.repo;

import com.example.wsd.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account createAccount(Account account);

    Account updateAccount(Long accountId, Account account);

    boolean deleteAccount(Account atm);

    boolean deleteById(Long id);

    List<Account> findAll();

    List<Account> findByIds(List<Long> ids);

    Optional<Account> findById(Long id);
}
