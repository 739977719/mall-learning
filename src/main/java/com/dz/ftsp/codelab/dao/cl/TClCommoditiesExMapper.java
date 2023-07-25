package com.dz.ftsp.codelab.dao.cl;

import org.apache.ibatis.annotations.Param;

public interface TClCommoditiesExMapper extends TClCommoditiesMapper {

    Long getLatestId();

    Integer countByCommoditiesName(String commoditiesName);

    Long purchaseCommodities(Long id, Long quantity);

    boolean updateInventory(Long id, Long newInventory);
}
