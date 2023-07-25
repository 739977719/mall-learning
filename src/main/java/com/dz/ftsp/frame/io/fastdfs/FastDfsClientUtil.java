package com.dz.ftsp.frame.io.fastdfs;

import com.dz.ftsp.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

public class FastDfsClientUtil {

    private String dataPath = "D:\\data\\dz-ftsp\\";

    public byte[] downloadFile(String groupName, String remoteFileName) throws Exception {
        String filePath = dataPath + remoteFileName;
        InputStream fis = new FileInputStream(filePath);
        int length = fis.available();
        byte[] bytes = new byte[length];
        fis.read(bytes);
        fis.close();
        return bytes;
    }

    public String[] uploadFile(String groupName, byte[] fileContent, String fileName) throws Exception {
        String[] result = new String[2];
        result[0] = groupName;
        String ext = getFileExt(fileName);
        StringBuilder builder = new StringBuilder();
        builder.append(UUID.randomUUID());
        builder.append(".");
        builder.append(ext);
        result[1] = builder.toString();
        ByteArrayInputStream bis = new ByteArrayInputStream(fileContent);
        FileUtils.saveFile(bis, dataPath + result[1]);
        return result;
    }

    private final String getFileExt(String fileName) {
        String ext = "un";// unknown
        if (StringUtils.isNotBlank(fileName)) {
            int pos = fileName.lastIndexOf(".");
            if (pos > -1) {
                ext = fileName.substring(pos + 1);
            }
        }
        return ext;
    }

}
