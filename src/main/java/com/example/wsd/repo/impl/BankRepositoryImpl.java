package com.example.wsd.repo.impl;

import com.example.wsd.model.Bank;
import com.example.wsd.model.User;
import com.example.wsd.repo.BankRepository;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class BankRepositoryImpl implements BankRepository {

    public static final String Bank_MAPDB = "bank.mapdb";
    public  static final String Bank = "bank";


    DB db = null;
    ConcurrentMap<Long, Bank> map = null;
    Comparator<Bank> bankComparator= (o1, o2) -> o1.getCreatedOn().compareTo(o2.getCreatedOn());

    private void start(){
        this.db = DBMaker.fileDB(Bank_MAPDB).make();
        this.map = db.hashMap(Bank, Serializer.LONG,Serializer.JAVA).createOrOpen();
    }

    private void shutdown(){
        this.db.close();
    }


    @Override
    public Bank createBank(Bank bank) {
        this.start();
        Bank bankdb = new Bank(bank.getName());
        bank.setId(getNewTaskId());
        map.put(bank.getId(), bankdb);
        this.shutdown();

        return bank;
    }


    private Long getNewTaskId() {
        Atomic.Long id = db.atomicLong("id").createOrOpen();
        return id.addAndGet(1);
    }

    @Override
    public Bank updateBank(Long bankId, Bank bank) {
        Optional<Bank> byId = findById(bankId);

        if (byId.isPresent()) {
            Bank task = byId.get();
            task.setName(bank.getName());
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }


    @Override
    public boolean deleteBank(Bank bank) {
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
    public List<Bank> findAll() {
        this.start();
        List<Bank> collect = map.values().stream()
                .sorted(bankComparator)
                .collect(Collectors.toList());
        this.shutdown();
        return collect;
    }

    @Override
    public List<Bank> findByIds(List<Long> ids) {
        List<Bank> bankList = new ArrayList<>();

        this.start();
        bankList = ids.stream().map(map::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.shutdown();

        return bankList;
    }

    @Override
    public Optional<Bank> findById(Long bankId) {
        this.start();
        Bank bank = map.get(bankId);
        this.shutdown();
        return Optional.of(bank);
    }
}
