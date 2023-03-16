package com.example.wsd.repo.impl;

import com.example.wsd.model.ATM;
import com.example.wsd.model.Status;
import com.example.wsd.model.User;
import com.example.wsd.repo.AtmRepository;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class AtmRepositoryImpl implements AtmRepository {

    public static final String ATM_MAPDB = "atm.mapdb";
    public  static final String ATM = "atm";

    DB db = null;
    ConcurrentMap<Long, ATM> map = null;
    Comparator<ATM> atmComparator= (o1, o2) -> o1.getCreatedOn().compareTo(o2.getCreatedOn());

    private void start(){
        this.db = DBMaker.fileDB(ATM_MAPDB).make();
        this.map = db.hashMap(ATM, Serializer.LONG,Serializer.JAVA).createOrOpen();
    }

    private void shutdown(){
        this.db.close();
    }


    @Override
    public ATM createATM(String name) {
        this.start();

        ATM atm = new ATM(name);
        atm.setId(getNewTaskId());
        map.put(atm.getId(), atm);
        this.shutdown();

        return atm;
    }

    @Override
    public ATM createATM(String message, Date createdDate) {
        this.start();

        ATM atm = new ATM(message, createdDate);
        atm.setId(getNewTaskId());
        map.put(atm.getId(), atm);
        this.shutdown();

        return atm;
    }

    private Long getNewTaskId() {
//        return  1L;
        Atomic.Long id = db.atomicLong("id").createOrOpen();
        return id.addAndGet(1);
    }

    @Override
    public ATM updateMessage(Long taskId, String activity) {
        Optional<ATM> byId = findById(taskId);

        if (byId.isPresent()) {
            ATM task = byId.get();
            task.setLoginName(activity);
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }

    @Override
    public ATM updateStatus(Long taskId, Status status) {
        Optional<ATM> byId = findById(taskId);

        if (byId.isPresent()) {
            ATM task = byId.get();
            task.setStatus(status);
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }

    @Override
    public boolean markCompletedById(Long taskId) {
        Optional<ATM> task = findById(taskId);
        if (task.isPresent()) {
            ATM updatedATM = updateStatus(taskId, Status.COMPLETED);
            return Objects.nonNull(updatedATM);
        }

        return false;
    }

    @Override
    public boolean deleteATM(ATM atm) {
        if (Objects.nonNull(atm.getId())) {
            this.start();
            map.remove(atm.getId());
            this.shutdown();
        }
        return true;
    }

    @Override
    public boolean deleteById(Long taskId) {
        if (Objects.nonNull(taskId)) {
            this.start();
            map.remove(taskId);
            this.shutdown();
        }
        return true;
    }

    @Override
    public List<ATM> findAll() {
        this.start();
        List<ATM> collect = map.values().stream()
                .sorted(atmComparator)
                .collect(Collectors.toList());
        this.shutdown();
        return collect;
    }

    @Override
    public List<ATM> findByIds(List<Long> ids) {
        List<ATM> atmList = new ArrayList<>();

        this.start();
        atmList = ids.stream().map(map::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.shutdown();

        return atmList;
    }

//    @Override
//    public List<User> findByName(String toTranfer) {
//        List<User> listUser = new ArrayList<>();
//
//        findAll().forEach(atm -> {
//            atm.getDataBank().getUsers().forEach(
//                    user -> {
//                        if(user.getName().toLowerCase()
//                                .equals(toTranfer.toLowerCase())){
//                            listUser.add(user);
//                        }
//                    }
//            );
//        });
//        return listUser;
//    }

    @Override
    public List<ATM> findByStatus(Status status) {
        List<ATM> atmList = new ArrayList<>();

        findAll().forEach(atm -> {
            if (atm.getStatus() == status) {
                atmList.add(atm);
            }
        });

        return atmList;
    }

    @Override
    public ATM findByIdAtm(Long taskId) {
        this.start();
        ATM atm = map.get(taskId);
        this.shutdown();
        return atm;
    }

    @Override
    public Optional<ATM> findById(Long taskId) {
        this.start();
        ATM atm = map.get(taskId);
        this.shutdown();
        return Optional.of(atm);
    }
    @Override
    public List<ATM> findByStatus(List<Status> statuses) {
        List<ATM> atmList = new ArrayList<>();

        List<Status> statusList = new ArrayList<>();

        if (Objects.nonNull(statuses)) {
            statusList = statuses;
        }

        List<Status> finalStatusList = statusList;
        findAll().forEach(atm -> {
            if (finalStatusList.contains(atm.getStatus())) {
                atmList.add(atm);
            }
        });

        return atmList;
    }
}
