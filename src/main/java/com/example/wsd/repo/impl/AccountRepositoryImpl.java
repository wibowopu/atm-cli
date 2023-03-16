package com.example.wsd.repo.impl;

import com.example.wsd.model.Account;
import com.example.wsd.repo.AccountRepository;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {

    public static final String Account_MAPDB = "account.mapdb";
    public  static final String Account = "account";


    DB db = null;
    ConcurrentMap<Long, Account> map = null;
    Comparator<Account> bankComparator= (o1, o2) -> o1.getCreatedOn().compareTo(o2.getCreatedOn());

    private void start(){
        this.db = DBMaker.fileDB(Account_MAPDB).make();
        this.map = db.hashMap(Account, Serializer.LONG,Serializer.JAVA).createOrOpen();
    }

    private void shutdown(){
        this.db.close();
    }


    @Override
    public Account createAccount(Account account) {
        this.start();
        Account accountdb = new Account(account.getName());
        accountdb.setId(getNewTaskId());
        map.put(accountdb.getId(), accountdb);
        this.shutdown();

        return accountdb;
    }


    private Long getNewTaskId() {
        Atomic.Long id = db.atomicLong("id").createOrOpen();
        return id.addAndGet(1);
    }

    @Override
    public Account updateAccount(Long accountId, Account bank) {
        Optional<Account> byId = findById(accountId);

        if (byId.isPresent()) {
            Account task = byId.get();
            task.setName(bank.getName());
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }


    @Override
    public boolean deleteAccount(Account bank) {
        if (Objects.nonNull(bank.getId())) {
            this.start();
            map.remove(bank.getId());
            this.shutdown();
        }
        return true;
    }

    @Override
    public boolean deleteById(Long bank) {
        if (Objects.nonNull(bank)) {
            this.start();
            map.remove(bank);
            this.shutdown();
        }
        return true;
    }

    @Override
    public List<Account> findAll() {
        this.start();
        List<Account> collect = map.values().stream()
                .sorted(bankComparator)
                .collect(Collectors.toList());
        this.shutdown();
        return collect;
    }

    @Override
    public List<Account> findByIds(List<Long> ids) {
        List<Account> bankList = new ArrayList<>();

        this.start();
        bankList = ids.stream().map(map::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.shutdown();

        return bankList;
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        this.start();
        Account bank = map.get(accountId);
        this.shutdown();
        return Optional.of(bank);
    }
}
