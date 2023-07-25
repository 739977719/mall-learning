package com.dz.ftsp.utils;

import com.dz.ftsp.exception.FtspException;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DzPageHelper {

    private static Logger logger = LoggerFactory.getLogger(DzPageHelper.class);
    private static List<Integer> pageSizeList = Arrays.asList(10, 15, 20, 25, 50, 100, 200, 500, 1000, 2000, 5000, 10000);

    public static void startPageAndSort(Map<String,?> map){
        startPageAndSort(map, null);
    }

    public static void startPageAndSort(Map<String,?> map, Class clazz){
        startPageAndSort(map, clazz, "pageNum", "pageSize", "sort");
    }

    public static void startPageAndSort(Map<String,?> map, Class clazz, String pageNumKey, String pageSizeKey, String orderByKey){
        startPage(map, pageNumKey, pageSizeKey);
        startSort(map, clazz, orderByKey);
    }


    public static void startPage(Map<String,?> map, String pageNumKey, String pageSizeKey){

        Integer supportPage = MapUtils.getIntegerFromMap(map, "supportPage", 0);

        if(supportPage == 1){
            // 分页页码
            Integer pageNum = MapUtils.getIntegerFromMap(map, pageNumKey, 1);
            // 分页大小
            Integer pageSize = MapUtils.getIntegerFromMap(map, pageSizeKey, 5000);
            if (!pageSizeList.contains(pageSize)) {
                throw new FtspException("违法的分页数量值");
            }
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    public static void startSort(Map<String,?> map, Class clazz, String orderByKey){
        String sort = MapUtils.getStringFromMap(map, orderByKey, null);
        if(ValidateUtils.isEmptyString(sort)){
            return;
        }
        String parseSort = sort;
        PageHelper.startPage(parseSort);
    }
}
