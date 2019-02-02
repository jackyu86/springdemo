package com.ycy.service.impl;

import com.ycy.center.dao.entity.User1;
import com.ycy.center.dao.mapper.User1Mapper;
import com.ycy.service.User1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019/2/2.
 */
@Service
public class User1ServiceImpl implements User1Service {

    @Autowired
    User1Mapper user1Mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(User1 user) {
        user1Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredException(User1 user) {
        user1Mapper.insert(user);
        throw new RuntimeException();
    }
}
