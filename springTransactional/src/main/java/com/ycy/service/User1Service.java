package com.ycy.service;

import com.ycy.center.dao.entity.User1;
import com.ycy.center.dao.entity.User2;

/**
 * Created by Administrator on 2019/2/2.
 */
public interface User1Service {
     void addRequired(User1 user);

     void addRequiredException(User1 user);

     void addRequiresNew(User1 user);

     void addRequiresUser1AndRequiresNewUser2(User1 user1, User2 user2);
}
