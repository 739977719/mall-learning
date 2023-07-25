package com.dz.ftsp.codelab.model.cl;

import java.math.BigDecimal;
import java.util.Date;

public class TClCommodities {
    private Long id;

    private String commoditiesName;

    private String category;

    private BigDecimal price;

    private Long inventory;

    private Date createdTime;

    private String createdByName;

    private Long createdById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommoditiesName() {
        return commoditiesName;
    }

    public void setCommoditiesName(String commoditiesName) {
        this.commoditiesName = commoditiesName == null ? null : commoditiesName.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName == null ? null : createdByName.trim();
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }
}