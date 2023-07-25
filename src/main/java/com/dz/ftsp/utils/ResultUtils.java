package com.dz.ftsp.utils;
import com.dz.ftsp.server.PageResult;
import com.dz.ftsp.constants.ErrorCode;
import com.dz.ftsp.server.MultiResult;
import com.dz.ftsp.server.Result;
import com.dz.ftsp.server.SingleResult;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 构造返回结果的工具类
 */
public class ResultUtils {

    /**
     * 构建结果对象
     * @param errorNo
     * @param errorInfo
     * @param pageNo
     * @param pageSize
     * @param total
     * @param result
     * @return
     */
    public static Result build(Integer errorNo, String errorInfo, Integer pageNo, Integer pageSize, Integer total, Object result){
        Result returnResult = null;
        if(result instanceof List){
            List list = (List)result;
            if(ValidateUtils.notNull(pageNo)){
                if (list instanceof Page) {
                    total = new Long(((Page) list).getTotal()).intValue();
                }
                returnResult = new PageResult(pageNo, pageSize, total, list);
            }else{
                returnResult = new MultiResult(list);
            }
        }else{
            returnResult = new SingleResult<Object>(result);
        }
        returnResult.setErrorNo(errorNo);
        returnResult.setErrorInfo(errorInfo);
        return returnResult;
    }


    /**
     * 成功返回
     * @param pageNo
     * @param pageSize
     * @param total
     * @param result
     * @return
     */
    public static Result success(Integer pageNo, Integer pageSize, Integer total, Object result){
        return build(ErrorCode.SUCCESS, "调用成功", pageNo, pageSize, total, result);
    }


    /**
     * 成功返回
     * @param result
     * @return
     */
    public static Result success(Object result){
        return success(null, null, null, result);
    }


    public static Result success(Object result, Map<String, ?> reqParam){
        return success(result, reqParam, "pageNum", "pageSize");
    }

    /**
     * 成功返回
     * @param result
     * @return
     */
    public static Result success(Object result, Map<String, ?> reqParam, String pageNumKey, String pageSizeKey){
        Integer supportPage = MapUtils.getIntegerFromMap(reqParam, "supportPage", 0);
        if(supportPage == 1){
            // 分页页码
            Integer pageNum = MapUtils.getIntegerFromMap(reqParam, pageNumKey, 1);
            // 分页大小
            Integer pageSize = MapUtils.getIntegerFromMap(reqParam, pageSizeKey, 20);
            return ResultUtils.success(pageNum, pageSize, null, result);
        } else {
            return success(result);
        }
    }

    /**
     * 错误返回
     * @param errorNo
     * @param errorInfo
     * @param result
     * @return
     */
    public static Result error(int errorNo, String errorInfo, Object result){
        return build(errorNo, errorInfo, null, null, null, result);
    }

    /**
     * 错误返回
     * @param errorNo
     * @param errorInfo
     * @return
     */
    public static Result error(int errorNo, String errorInfo){
        return error(errorNo, errorInfo, null);
    }

}
