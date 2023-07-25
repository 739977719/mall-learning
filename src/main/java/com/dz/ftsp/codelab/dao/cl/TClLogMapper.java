package com.dz.ftsp.codelab.dao.cl;

import com.dz.ftsp.codelab.model.cl.TClLog;
import com.dz.ftsp.codelab.model.cl.TClLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TClLogMapper {
    long countByExample(TClLogExample example);

    int deleteByExample(TClLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TClLog record);

    int insertSelective(TClLog record);

    List<TClLog> selectByExample(TClLogExample example);

    TClLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TClLog record, @Param("example") TClLogExample example);

    int updateByExample(@Param("record") TClLog record, @Param("example") TClLogExample example);

    int updateByPrimaryKeySelective(TClLog record);

    int updateByPrimaryKey(TClLog record);
}