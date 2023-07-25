package com.dz.ftsp.codelab.model.cl;


public class TClProduct {

    /**
     * 产品详情id
     */
	private Long pId;
    /**
     * 产品描述
     */
	private String pDescription;
    /**
     * 图片路径
     */
    private String pImage;
    /**
     * 产品id
     */
	private Integer pCId;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public Integer getpCId() {
        return pCId;
    }

    public void setpCId(Integer pCId) {
        this.pCId = pCId;
    }
}