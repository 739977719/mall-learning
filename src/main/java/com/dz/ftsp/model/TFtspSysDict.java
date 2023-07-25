package com.dz.ftsp.model;

import java.util.Date;

public class TFtspSysDict {
    private Long id;

    private Long sysId;

    private Integer sourceType;

    private Long parentId;

    private String routeStr;

    private String routePath;

    private String dictKey;

    private String dictValue;

    private String dictDesc;

    private Integer enabled;

    private Integer frontShowState;

    private Integer frontOrderNum;

    private Integer backShowState;

    private Integer backOrderNum;

    private Integer showState1;

    private Integer showState2;

    private Long dataVer;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRouteStr() {
        return routeStr;
    }

    public void setRouteStr(String routeStr) {
        this.routeStr = routeStr == null ? null : routeStr.trim();
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath == null ? null : routePath.trim();
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey == null ? null : dictKey.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc == null ? null : dictDesc.trim();
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getFrontShowState() {
        return frontShowState;
    }

    public void setFrontShowState(Integer frontShowState) {
        this.frontShowState = frontShowState;
    }

    public Integer getFrontOrderNum() {
        return frontOrderNum;
    }

    public void setFrontOrderNum(Integer frontOrderNum) {
        this.frontOrderNum = frontOrderNum;
    }

    public Integer getBackShowState() {
        return backShowState;
    }

    public void setBackShowState(Integer backShowState) {
        this.backShowState = backShowState;
    }

    public Integer getBackOrderNum() {
        return backOrderNum;
    }

    public void setBackOrderNum(Integer backOrderNum) {
        this.backOrderNum = backOrderNum;
    }

    public Integer getShowState1() {
        return showState1;
    }

    public void setShowState1(Integer showState1) {
        this.showState1 = showState1;
    }

    public Integer getShowState2() {
        return showState2;
    }

    public void setShowState2(Integer showState2) {
        this.showState2 = showState2;
    }

    public Long getDataVer() {
        return dataVer;
    }

    public void setDataVer(Long dataVer) {
        this.dataVer = dataVer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}