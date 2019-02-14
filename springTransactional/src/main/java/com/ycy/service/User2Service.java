package com.ycy.service;

import com.ycy.center.dao.entity.User2;

/**
 * Created by Administrator on 2019/2/2.
 */
public interface User2Service {
     void addRequired(User2 user);

     void addRequiredException(User2 user);

     void addRequiresNew(User2 user);

     void addRequiresNewException(User2 user);

     void addRequiresNewMyException(User2 user);

     void addSupports(User2 user2);

     void addSupportsException(User2 user2);
}
