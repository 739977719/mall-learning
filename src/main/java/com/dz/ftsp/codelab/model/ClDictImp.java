package com.dz.ftsp.codelab.model;


import com.dz.ftsp.codelab.model.cl.TClDict;

public class ClDictImp extends TClDict {

    private Integer operate;
    private String operateRemark;
    private String fullPath;

    public Integer getOperate() {
        return operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
    }

    public String getOperateRemark() {
        return operateRemark;
    }

    public void setOperateRemark(String operateRemark) {
        this.operateRemark = operateRemark;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
