package com.dz.ftsp.codelab.manager;

import com.dz.ftsp.codelab.dao.cl.TClCommoditiesTransactionMapper;
import com.dz.ftsp.codelab.model.cl.TClCommodities;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesExample;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesTransaction;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesTransactionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component

public class ClCommoditiesTransactionManager extends ClBaseManager{

    @Autowired
    TClCommoditiesTransactionMapper ClCommoditiesTransactionMapper;
    public void writeTransaction(Long id, String commoditiesName, BigDecimal price, Long quantity) {
        TClCommoditiesTransaction transaction = new TClCommoditiesTransaction();
        Date now = new Date();
        transaction.setCommoditiesId(id);
        transaction.setCommoditiesName(commoditiesName);
        transaction.setTransactionAmount(price);
        transaction.setQuantity(quantity);
        transaction.setTransactionDate(now);
        ClCommoditiesTransactionMapper.insert(transaction);

    }

    public List<TClCommoditiesTransaction> getComoDittePage(String commoditiesName, Date startDate, Date endDate) {
        return ClCommoditiesTransactionMapper.selectByInfoDate(commoditiesName,startDate,endDate);

    }
}
