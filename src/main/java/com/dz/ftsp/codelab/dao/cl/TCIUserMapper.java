package com.dz.ftsp.codelab.dao.cl;

import com.dz.ftsp.codelab.model.cl.TCIUser;
import org.apache.ibatis.annotations.Param;

public interface TCIUserMapper {
    TCIUser findByUsername(@Param("username") String username);
}
