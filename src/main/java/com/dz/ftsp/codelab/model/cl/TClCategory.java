package com.dz.ftsp.codelab.model.cl;



public class TClCategory {

    /**
     * 类型id
     */
	private Long cId;
    /**
     * 类型名称
     */
	private String cName;

    public Long getCId() {
        return cId;
    }

    public void setCId(Long cId) {
        this.cId = cId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    @Override
    public String toString() {
        return "TClCategory{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                '}';
    }
}