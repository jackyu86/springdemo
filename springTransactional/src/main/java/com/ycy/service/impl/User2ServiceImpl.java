package com.ycy.service.impl;

import com.ycy.center.dao.entity.User2;
import com.ycy.center.dao.mapper.User2Mapper;
import com.ycy.common.MyExcetion;
import com.ycy.service.User2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Administrator on 2019/2/2.
 */
@Slf4j
@Service
public class User2ServiceImpl implements User2Service {


    @Autowired
    User2Mapper user2Mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(User2 user) {
        user2Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredException(User2 user) {
        user2Mapper.insert(user);
        log.error("addRequiredException");
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNew(User2 user) {
        user2Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNewException(User2 user) {
        user2Mapper.insert(user);
        log.error("addRequiredException");
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNewMyException(User2 user) {
        user2Mapper.insert(user);
        log.error("addRequiredException");
        throw new MyExcetion();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void addSupports(User2 user2) {
        user2Mapper.insert(user2);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void addSupportsException(User2 user2) {
        user2Mapper.insert(user2);
        log.error("addSupportsException--supports");
        throw new RuntimeException("addSupportsException");

    }
}
