package com.example.wsd.repo.impl;

import com.example.wsd.model.Account;
import com.example.wsd.model.Bank;
import com.example.wsd.model.Holder;
import com.example.wsd.model.User;
import com.example.wsd.repo.AccountRepository;
import com.example.wsd.repo.UserRepository;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {

    public static final String User_MAPDB = "user.mapdb";
    public  static final String User = "user";


    DB db = null;
    ConcurrentMap<Long, User> map = null;
    Comparator<User> userComparator= (o1, o2) -> o1.getCreatedOn().compareTo(o2.getCreatedOn());

    private void start(){
        this.db = DBMaker.fileDB(User_MAPDB).make();
        this.map = db.hashMap(User, Serializer.LONG,Serializer.JAVA).createOrOpen();
    }

    private void shutdown(){
        this.db.close();
    }


    @Override
    public User createUser(User user, Bank bank) {
        this.start();
        User userdb = new User(user.getName(),bank);
        userdb.setId(getNewTaskId());
        userdb.setName(user.getName());
        userdb.setUuid(bank.getUserUUID());
        Account newAccount = new Account(user.getName());
        Holder holder = new Holder();
        Holder owned = new Holder();
        userdb.setAccount(newAccount);
        userdb.setOwned(owned);
        userdb.setHolder(holder);
        map.put(userdb.getId(), userdb);
        this.shutdown();

        return userdb;
    }


    private Long getNewTaskId() {
        Atomic.Long id = db.atomicLong("id").createOrOpen();
        return id.addAndGet(1);
    }

    @Override
    public User updateUser(Long userId, User user) {
        Optional<User> byId = findById(userId);

        if (byId.isPresent()) {
            User task = byId.get();
            task.setName(user.getName());
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }

    @Override
    public User updateAccount(Long userId, User user) {
        Optional<User> byId = findById(userId);

        if (byId.isPresent()) {
            User task = byId.get();
            task.setAccount(
                    user.getAccount()
            );
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }
    @Override
    public User updateAmount(Long userId, User user)
    {
        Optional<User> byId = findById(userId);

        if (byId.isPresent()) {
            User task = byId.get();
            task.getAccount().setAmount(
                    user.getAccount().getAmount()
            );
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }
    @Override
    public User updateHolder(Long userId, User user)
    {
        Optional<User> byId = findById(userId);

        if (byId.isPresent()) {
            User task = byId.get();
            task.setHolder(
                    user.getHolder()
            );
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }

    @Override
    public User updateOwned(Long userId, Holder holder)
    {
        Optional<User> byId = findById(userId);

        if (byId.isPresent()) {
            User task = byId.get();
            task.setOwned(
                    holder
            );
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }
    @Override
    public User updateHolder(Long userId, Holder holder)
    {
        Optional<User> byId = findById(userId);

        if (byId.isPresent()) {
            User task = byId.get();
            task.setHolder(
                    holder
            );
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }

    @Override
    public boolean deleteUser(User bank) {
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
    public List<User> findAll() {
        this.start();
        List<User> collect = map.values().stream()
                .sorted(userComparator)
                .collect(Collectors.toList());
        this.shutdown();
        return collect;
    }

    @Override
    public List<User> findByIds(List<Long> ids) {
        List<User> bankList = new ArrayList<>();

        this.start();
        bankList = ids.stream().map(map::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.shutdown();

        return bankList;
    }

    @Override
    public Optional<User> findById(Long userId) {
        this.start();
        User bank = map.get(userId);
        this.shutdown();
        return Optional.of(bank);
    }

    @Override
    public boolean isUser(String name) {
        boolean status = false;
        List<User> listUser = new ArrayList<>();
        this.findAll().forEach(
                user -> {
                    if (user.getName().toLowerCase()
                            .equals(name.toLowerCase())) {
                        listUser.add(user);
                    }
                });

        if(listUser.size()>0)
        {
            status= true;
        }else
        {
            status=false;
        }
        return status;
    }

    @Override
    public User findByName(String name) throws Exception {
        List<User> listUser = new ArrayList<>();

        User userFound = null;
        AtomicReference<User> userFounded = new AtomicReference<>(new User());
        this.findAll().forEach(
                user -> {
                    if (user.getName().toLowerCase()
                            .equals(name.toLowerCase())) {
                        listUser.add(user);
                    }
                });

        try {
            userFound = listUser.get(0);
            return userFound;
        }catch (Exception ex){
            throw new Exception("User Not Found",null);
        }
    }

}
