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

     void addRequiresUser1AndRequiresUser2(User1 user,User2 user2);

     void addSupports(User1 user1);

     void addSupportsException(User1 user1);

     void addRequiresUser1AndSupportsUser2(User1 user1, User2 user2);
}
