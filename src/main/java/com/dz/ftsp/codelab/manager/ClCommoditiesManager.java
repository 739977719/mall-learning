package com.dz.ftsp.codelab.manager;
import com.dz.ftsp.codelab.dao.cl.TClCommoditiesExMapper;
import com.dz.ftsp.codelab.model.cl.TClCommodities;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesExample;
import com.dz.ftsp.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品台账manager
 */
@Component
public class ClCommoditiesManager extends ClBaseManager {

    @Autowired
    private TClCommoditiesExMapper stCommoditiesMapper;

    /**
     * 查询商品信息
     *
     * @param id                 商品id
     * @param commoditiesName    商品名称
     * @param category           商品类别
     * @param price              价格
     * @param inventory          库存
     * @param createdTimeFrom    创建时间从
     * @param createdTimeTo      创建时间到
     * @param createdByName          创建人
     * @param createdById        创建人id
     *
     *
     */
    public List<TClCommodities> list(Long id, String commoditiesName, String category, BigDecimal price, Long inventory,
                                     Date createdTimeFrom, Date createdTimeTo, String createdByName, Long createdById) {
        TClCommoditiesExample example = new TClCommoditiesExample();
        TClCommoditiesExample.Criteria criteria = example.createCriteria();
        if (ValidateUtils.notNull(id)) {
            criteria.andIdEqualTo(id);
        }
        if (ValidateUtils.isNotEmptyString(commoditiesName)) {
            criteria.andCommoditiesNameLike("%" + commoditiesName + "%");
        }
        if (ValidateUtils.isNotEmptyString(category)) {
            criteria.andCategoryLike("%" + category + "%");
        }
        if (ValidateUtils.isNotEmptyString(createdByName)) {
            criteria.andCreatedByNameLike("%" + createdByName + "%");
        }
        if (ValidateUtils.notNull(price)) {
            criteria.andPriceEqualTo(price);
        }
        if (ValidateUtils.notNull(createdById)) {
            criteria.andCreatedByIdEqualTo(createdById);
        }
        if (ValidateUtils.notNull(inventory)) {
            criteria.andInventoryEqualTo(inventory);
        }
        if (ValidateUtils.notNull(createdTimeFrom)) {
            criteria.andCreatedTimeGreaterThanOrEqualTo(createdTimeFrom);
        }
        if (ValidateUtils.notNull(createdTimeTo)) {
            criteria.andCreatedTimeLessThanOrEqualTo(createdTimeTo);
        }



        return stCommoditiesMapper.selectByExample(example);
    }

    /**
     * 快速新增一条商品信息
     *
     * @param commoditiesName 商品名称
     * @param category        商品类别
     * @param price           价格
     * @param inventory       库存
     * @return
     */
    public Long addCommodities(String commoditiesName, String category, BigDecimal price, Long inventory) {
        Date now = new Date();

        // 新增商品信息
        TClCommodities commodities = new TClCommodities();
        commodities.setCreatedTime(now);
        commodities.setCommoditiesName(commoditiesName);
        commodities.setCategory(category);
        commodities.setPrice(price);
        commodities.setInventory(inventory);
        stCommoditiesMapper.insert(commodities);
        return commodities.getId();
    }

   public void editCommodities(Long id, String commoditiesName, String category,BigDecimal price, Long inventory) {
        Date now = new Date();

        //修改商品信息
       TClCommodities commodities = new TClCommodities();
       commodities.setCreatedTime(now);
       commodities.setCommoditiesName(commoditiesName);
       commodities.setCategory(category);
       commodities.setPrice(price);
       commodities.setInventory(inventory);
       commodities.setId(id);
       stCommoditiesMapper.updateByPrimaryKey(commodities);
   }



    public void deleteCommodities(Long id) {

        //删除商品
        stCommoditiesMapper.deleteByPrimaryKey(id);
    }

    public Long getLatestId(){

        //获取最新id
        Long latestId = stCommoditiesMapper.getLatestId();
        return latestId;
    }

    public boolean isCommoditiesNameExists(String commoditiesName) {
        // 执行数据库查询操作，判断商品名称是否已存在
        // 返回结果为布尔值，表示商品名称是否已存在
        // 可以使用 SQL 语句或者 MyBatis 的映射文件来执行查询操作
        // 例如，你可以使用 SQL 语句执行查询：
        int count = stCommoditiesMapper.countByCommoditiesName(commoditiesName);
        return count > 0;
    }


    public List<TClCommodities> searchCommodities(String commoditiesName, String category, BigDecimal minPrice, BigDecimal maxPrice) {
        TClCommoditiesExample example = new TClCommoditiesExample();
        TClCommoditiesExample.Criteria criteria = example.createCriteria();
        if (ValidateUtils.isNotEmptyString(commoditiesName)) {
            criteria.andCommoditiesNameLike("%" + commoditiesName + "%");
        }
        if (ValidateUtils.isNotEmptyString(category)){
            criteria.andCategoryLike("%" + category + "%");
        }
        if(ValidateUtils.notNull(minPrice)){
            criteria.andPriceGreaterThan(minPrice);
        }
        if (ValidateUtils.notNull(maxPrice)){
            criteria.andPriceLessThan(maxPrice);
        }

        return stCommoditiesMapper.selectByExample(example);

    }

    public boolean purchaseCommodities(Long id, Long quantity){
        Long inventory = stCommoditiesMapper.purchaseCommodities(id, quantity);
        if (inventory != null && quantity <= inventory) {
            // Perform the purchase operation here
            // Update the inventory quantity or perform other operations
            Long newInventory = inventory - quantity;
            boolean updateSuccess = stCommoditiesMapper.updateInventory(id, newInventory);
            if (updateSuccess) {
                // Inventory update successful
                return true;
            } else {
                // Failed to update inventory
                return false;
            }
        } else {
            return false;
        }
    }
}