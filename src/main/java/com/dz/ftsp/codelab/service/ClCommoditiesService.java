package com.dz.ftsp.codelab.service;

import com.dz.ftsp.codelab.manager.ClCommoditiesManager;
import com.dz.ftsp.codelab.model.cl.TClCommodities;
import com.dz.ftsp.utils.DateUtils;
import com.dz.ftsp.utils.MapUtils;
import com.dz.ftsp.utils.ValidateUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author 73997
 */
@Service
public class ClCommoditiesService extends ClBaseService {
    @Autowired
    ClCommoditiesManager commoditiesManager;


    /**
     * 商品
     *
     * @param conditions
     * @param pageNum
     * @param pageSize
     * @param sort
     * @return
     */
    public List<TClCommodities> pageList(Map<String, ?> conditions, Integer pageNum, Integer pageSize, String sort) {
        if (ValidateUtils.notNull(pageNum) && ValidateUtils.notNull(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        PageHelper.orderBy(sort);

        Long id = MapUtils.getLongFromMap(conditions, "id", null);
        String commoditiesName = MapUtils.getStringFromMap(conditions, "commoditiesName", null);
        String category = MapUtils.getStringFromMap(conditions, "category", null);
        BigDecimal price = MapUtils.getBigDecimalFromMap(conditions, "price", null);
        Long inventory = MapUtils.getLongFromMap(conditions, "inventory", null);
        String createdByName = MapUtils.getStringFromMap(conditions, "createdByName", null);
        Long createdById = MapUtils.getLongFromMap(conditions, "createdById", null);
        String beginDate = MapUtils.getStringFromMap(conditions, "beginDate", null);
        String endDate = MapUtils.getStringFromMap(conditions, "endDate", null);

        Date createTimeFrom = (beginDate == null) ? null : DateUtils.parse(beginDate, "yyyyMMdd");
        Date createTimeTo = (endDate == null) ? null : DateUtils.parse(endDate, "yyyyMMdd");

        List<TClCommodities> list = commoditiesManager.list(id, commoditiesName, category, price, inventory, createTimeFrom, createTimeTo, createdByName, createdById);

        PageHelper.clearPage();

        return list;
    }

    public Long addCommodities(Map<String, ?> map) {
        // 在这里执行添加商品的逻辑
        try {
            // 执行商品添加操作
            String commoditiesName = MapUtils.getStringFromMap(map, "commoditiesName", null);
            String category = MapUtils.getStringFromMap(map, "category", null);
            BigDecimal price = MapUtils.getBigDecimalFromMap(map, "price", null);
            Long inventory = MapUtils.getLongFromMap(map, "inventory", null);
           return commoditiesManager.addCommodities(commoditiesName, category, price, inventory);
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常情况
            return Long.valueOf(0);
        }
    }

    //删除

    public void deleteCommodities(Map<String, ?> map) {
        Long id = MapUtils.getLongFromMap(map, "id", null);

        commoditiesManager.deleteCommodities(id);


    }

    //修改

    public void editCommodities(Map<String, ?> map) {
        String commoditiesName = MapUtils.getStringFromMap(map, "commoditiesName", null);
        String category = MapUtils.getStringFromMap(map, "category", null);
        BigDecimal price = MapUtils.getBigDecimalFromMap(map, "price", null);
        Long inventory = MapUtils.getLongFromMap(map, "inventory", null);
        Long id = MapUtils.getLongFromMap(map, "id", null);

        commoditiesManager.editCommodities(id, commoditiesName, category, price, inventory);
    }

    public Long getLatestId() {

        Long latestId = commoditiesManager.getLatestId();

        return latestId;

    }
    public boolean isCommoditiesNameExists(Map<String, ?> map) {
        // 调用 Manger 层的方法查询商品名称是否已存在于数据库中
        // 返回结果为布尔值，表示商品名称是否已存在
        String commoditiesName = MapUtils.getStringFromMap(map, "commoditiesName", null);
        return commoditiesManager.isCommoditiesNameExists(commoditiesName);
    };


    public List<TClCommodities> searchCommodities(Map<String, ?> map){
        String commoditiesName = MapUtils.getStringFromMap(map, "commoditiesName", null);
        String category = MapUtils.getStringFromMap(map, "category", null);
        BigDecimal minPrice = MapUtils.getBigDecimalFromMap(map,"minPrice",null);
        BigDecimal maxPrice = MapUtils.getBigDecimalFromMap(map,"maxPrice",null);

        List<TClCommodities> list = commoditiesManager.searchCommodities(commoditiesName,category,minPrice,maxPrice);

        return list;

    }
};



