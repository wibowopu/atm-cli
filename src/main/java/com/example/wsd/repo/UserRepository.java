package com.example.wsd.repo;

import com.example.wsd.model.Bank;
import com.example.wsd.model.Holder;
import com.example.wsd.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User createUser(User user, Bank bank);

    User updateAccount(Long userId, User user);
    User updateUser(Long userId, User user);
    User updateOwned(Long userId, Holder holder);
    public User updateHolder(Long userId, User user);
    User updateHolder(Long userId, Holder holder);
    User updateAmount(Long userId, User user);
    boolean isUser(String name);



    boolean deleteUser(User user);

    boolean deleteById(Long id);

    List<User> findAll();

    List<User> findByIds(List<Long> ids);

    Optional<User> findById(Long id);

    User findByName(String Name) throws Exception;
}
