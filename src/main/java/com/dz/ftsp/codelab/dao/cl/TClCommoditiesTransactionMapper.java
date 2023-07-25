package com.dz.ftsp.codelab.dao.cl;

import com.dz.ftsp.codelab.model.cl.TClCommodities;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesTransaction;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesTransactionExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TClCommoditiesTransactionMapper {
    long countByExample(TClCommoditiesTransactionExample example);

    int deleteByExample(TClCommoditiesTransactionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TClCommoditiesTransaction record);

    int insertSelective(TClCommoditiesTransaction record);

    List<TClCommoditiesTransaction> selectByExample(TClCommoditiesTransactionExample example);

    TClCommoditiesTransaction selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TClCommoditiesTransaction record, @Param("example") TClCommoditiesTransactionExample example);

    int updateByExample(@Param("record") TClCommoditiesTransaction record, @Param("example") TClCommoditiesTransactionExample example);

    int updateByPrimaryKeySelective(TClCommoditiesTransaction record);

    int updateByPrimaryKey(TClCommoditiesTransaction record);

    TClCommoditiesTransaction getSaleOne();

    List<TClCommoditiesTransaction> selectByDailySale(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    List<TClCommoditiesTransaction> selectByInfoDate(@Param("commoditiesName")String commoditiesName,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}