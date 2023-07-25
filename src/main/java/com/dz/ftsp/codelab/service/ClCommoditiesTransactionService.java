package com.dz.ftsp.codelab.service;

import com.dz.ftsp.codelab.dao.cl.TClCommoditiesTransactionMapper;
import com.dz.ftsp.codelab.manager.ClCommoditiesManager;
import com.dz.ftsp.codelab.manager.ClCommoditiesTransactionManager;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesTransaction;
import com.dz.ftsp.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service

public class ClCommoditiesTransactionService extends ClBaseService{

    @Autowired
    ClCommoditiesTransactionManager commoditiesTransactionManager;

    @Autowired
    ClCommoditiesManager commoditiesManager;

    @Autowired
    TClCommoditiesTransactionMapper tClCommoditiesTransactionMapper;


    public void purchaseCommodities(Map<String,?> map) {
        Long id = MapUtils.getLongFromMap(map,"id",null);
        String commoditiesName = MapUtils.getStringFromMap(map,"commoditiesName",null);
        BigDecimal price = MapUtils.getBigDecimalFromMap(map,"price",null);
        Long quantity = MapUtils.getLongFromMap(map,"quantity",null);
        // 调用manager层执行库存查询，若有库存，返回true，否则返回false
        boolean isSuccess = commoditiesManager.purchaseCommodities(id,quantity);
        if (isSuccess){
            commoditiesTransactionManager.writeTransaction(id,commoditiesName,price,quantity);
        }
    }

     /**
     * 获取日期额度
     */
    public List<TClCommoditiesTransaction> getDailySale(Map<String, ?> map) {
        Date startDate = MapUtils.getDateFromMapNotNull(map,"startDate","yyyy-MM-dd");
        Date endDate =MapUtils.getDateFromMapNotNull(map,"endDate","yyyy-MM-dd");
        List<TClCommoditiesTransaction> list = tClCommoditiesTransactionMapper.selectByDailySale(startDate,endDate);
        return list;
    }


    public TClCommoditiesTransaction SaleOneProduct() {
        return tClCommoditiesTransactionMapper.getSaleOne();
    }

    public List<TClCommoditiesTransaction> getComoDittePage(Map<String,?> map) {
        Date startDate = MapUtils.getDateFromMapNotNull(map,"startDate","yyyy-MM-dd");
        Date endDate =MapUtils.getDateFromMapNotNull(map,"endDate","yyyy-MM-dd");
        String commoditiesName = MapUtils.getStringFromMap(map,"commoditiesName",null);
        TClCommoditiesTransaction tClCommoditiesTransaction = (TClCommoditiesTransaction) commoditiesTransactionManager.getComoDittePage(commoditiesName,startDate,endDate);
        return null;
    }
}
