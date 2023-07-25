package com.dz.ftsp.codelab.model.cl;

import java.util.Date;

public class TClDict {
    private Long id;

    private Date createTime;

    private Long createId;

    private String createNickName;

    private Date updateTime;

    private Long updateId;

    private String updateNickName;

    private String nodeKey;

    private String nodePath;

    private String nodeValue;

    private Integer orderNum;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateNickName() {
        return createNickName;
    }

    public void setCreateNickName(String createNickName) {
        this.createNickName = createNickName == null ? null : createNickName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateNickName() {
        return updateNickName;
    }

    public void setUpdateNickName(String updateNickName) {
        this.updateNickName = updateNickName == null ? null : updateNickName.trim();
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey == null ? null : nodeKey.trim();
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath == null ? null : nodePath.trim();
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue == null ? null : nodeValue.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}