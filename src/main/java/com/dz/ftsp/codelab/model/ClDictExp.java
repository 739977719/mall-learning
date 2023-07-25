package com.dz.ftsp.codelab.model;

import com.dz.ftsp.codelab.model.cl.TClDict;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * 字典导出bean
 */
@JsonPropertyOrder({"key", "value", "orderNum", "remark", "children"})
public class ClDictExp {

    private String key;
    private String value;
    private Integer orderNum;
    private String remark;
    private List<ClDictExp> children;

    public ClDictExp(TClDict dict) {
        this.key = dict.getNodeKey();
        this.value = dict.getNodeValue();
        this.orderNum = dict.getOrderNum();
        this.remark = dict.getRemark();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        this.remark = remark;
    }

    public List<ClDictExp> getChildren() {
        return children;
    }

    public void setChildren(List<ClDictExp> children) {
        this.children = children;
    }
}
