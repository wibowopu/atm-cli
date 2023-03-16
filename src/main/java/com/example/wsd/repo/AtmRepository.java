package com.example.wsd.repo;

import com.example.wsd.model.ATM;
import com.example.wsd.model.Status;
import com.example.wsd.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AtmRepository {

    ATM createATM(String message);

    ATM createATM(String atmMessage, Date createdDate);

    ATM updateMessage(Long taskId, String activity);

    ATM updateStatus(Long taskId, Status status);

    boolean markCompletedById(Long id);

    boolean deleteATM(ATM atm);

    boolean deleteById(Long id);

    List<ATM> findAll();

    List<ATM> findByIds(List<Long> ids);

    List<ATM> findByStatus(Status status);

    Optional<ATM> findById(Long id);
    ATM findByIdAtm(Long id);

    List<ATM> findByStatus(List<Status> statuses);
}
