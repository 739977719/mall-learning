package com.dz.ftsp.codelab.dao.cl;

import com.dz.ftsp.codelab.model.cl.TClCommodities;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TClCommoditiesMapper {
    long countByExample(TClCommoditiesExample example);

    int deleteByExample(TClCommoditiesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TClCommodities record);

    int insertSelective(TClCommodities record);

    List<TClCommodities> selectByExample(TClCommoditiesExample example);

    TClCommodities selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TClCommodities record, @Param("example") TClCommoditiesExample example);

    int updateByExample(@Param("record") TClCommodities record, @Param("example") TClCommoditiesExample example);

    int updateByPrimaryKeySelective(TClCommodities record);

    int updateByPrimaryKey(TClCommodities record);
}