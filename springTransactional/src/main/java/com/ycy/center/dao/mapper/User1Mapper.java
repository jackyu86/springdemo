package com.ycy.center.dao.mapper;

import com.ycy.center.dao.entity.User1;

/**
 * Created by Administrator on 2019/2/2.
 */
public interface User1Mapper {
    int insert(User1 record);
    User1 selectByPrimaryKey(Integer id);
}
