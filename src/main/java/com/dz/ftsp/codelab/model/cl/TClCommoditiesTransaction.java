package com.dz.ftsp.codelab.model.cl;



import java.math.BigDecimal;
import java.util.Date;

public class TClCommoditiesTransaction {
    private Long id;

    private Long commoditiesId;

    private String commoditiesName;

    private Date transactionDate;

    private BigDecimal transactionAmount;

    private Long quantity;

    private String buyerId;

    private String sellerId;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommoditiesId() {
        return commoditiesId;
    }

    public void setCommoditiesId(Long commoditiesId) {
        this.commoditiesId = commoditiesId;
    }

    public String getCommoditiesName() {
        return commoditiesName;
    }

    public void setCommoditiesName(String commoditiesName) {
        this.commoditiesName = commoditiesName == null ? null : commoditiesName.trim();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }
}