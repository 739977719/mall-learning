package com.dz.ftsp.codelab.dao.cl;

import com.dz.ftsp.codelab.model.cl.TClDict;
import com.dz.ftsp.codelab.model.cl.TClDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TClDictMapper {
    long countByExample(TClDictExample example);

    int deleteByExample(TClDictExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TClDict record);

    int insertSelective(TClDict record);

    List<TClDict> selectByExample(TClDictExample example);

    TClDict selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TClDict record, @Param("example") TClDictExample example);

    int updateByExample(@Param("record") TClDict record, @Param("example") TClDictExample example);

    int updateByPrimaryKeySelective(TClDict record);

    int updateByPrimaryKey(TClDict record);
}