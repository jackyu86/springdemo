package com.ycy.service.impl;

import com.ycy.center.dao.entity.User1;
import com.ycy.center.dao.entity.User2;
import com.ycy.center.dao.mapper.User1Mapper;
import com.ycy.common.MyExcetion;
import com.ycy.service.User1Service;
import com.ycy.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019/2/2.
 */
@Service
public class User1ServiceImpl implements User1Service {

    @Autowired
    User1Mapper user1Mapper;

    @Autowired
    User2Service user2Service;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNew(User1 user) {
        user1Mapper.insert(user);
    }

    @Override
    //
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT,
            rollbackFor = RuntimeException.class,noRollbackFor = MyExcetion.class)
    public void addRequiresUser1AndRequiresNewUser2(User1 user1, User2 user2) {

        user1Mapper.insert(user1);
        // new transactional throws runtime exception for rollback
        try {
            user2Service.addRequiresNewException(user2);
        }catch (Exception e){
            //抛出不回滚异常 外层事务不回滚
            throw new MyExcetion();
        }
        // new transational throws myexception for no rollback
        //user2Service.addRequiresNewMyException(user2);

        //user2Service.addRequiresNew(user2);
        //throw new RuntimeException("addRequiresUser1AndRequiresNewUser2");

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiresUser1AndRequiresUser2(User1 user, User2 user2) {
        user1Mapper.insert(user);

        user2Service.addRequired(user2);
        //user2Service.addRequiredException(user2);
         throw new RuntimeException("throws  user2 insert after  ");
    }
}
