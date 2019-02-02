package com.ycy.center.dao.mapper;

import com.ycy.center.dao.entity.User2;

/**
 * Created by Administrator on 2019/2/2.
 */
public interface User2Mapper {
    int insert(User2 record);
    User2 selectByPrimaryKey(Integer id);
}
