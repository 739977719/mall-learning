package com.dz.ftsp.codelab.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("dz.ftsp.codelab.upload")
public class ClUploadProperties {
    /**
     * 组名，默认group4
     */
    private String groupName = "group4";

    /**
     * 文件大小上限，默认100M
     */
    private Long fileMaxSize = 100L * 1024 * 1024;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getFileMaxSize() {
        return fileMaxSize;
    }

    public void setFileMaxSize(Long fileMaxSize) {
        this.fileMaxSize = fileMaxSize;
    }

}
