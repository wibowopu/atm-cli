package com.example.wsd.repo;

import com.example.wsd.model.Bank;
import com.example.wsd.model.Status;
import com.example.wsd.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BankRepository {

    Bank createBank(Bank bank);

    Bank updateBank(Long bankId, Bank bank);

    boolean deleteBank(Bank bank);

    boolean deleteById(Long id);

    List<Bank> findAll();

    List<Bank> findByIds(List<Long> ids);

    Optional<Bank> findById(Long id);
}
