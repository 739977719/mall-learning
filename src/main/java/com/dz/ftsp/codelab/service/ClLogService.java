package com.dz.ftsp.codelab.service;

import com.dz.ftsp.model.LoginUser;
import com.dz.ftsp.codelab.model.cl.TClLog;
import com.dz.ftsp.utils.DateUtils;
import com.dz.ftsp.utils.MapUtils;
import com.dz.ftsp.utils.ValidateUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClLogService extends ClBaseService {

    /**
     * 查询
     *
     * @param operateCode 操作编号
     * @param operateName 操作名称
     * @param content     内容
     * @param pageNum     页码
     * @param pageSize    每页条数
     * @param sort        排序
     */
    public List<TClLog> pageList(String operateCode, String operateName, String content, Integer pageNum,
                                 Integer pageSize, String sort) {
        if (ValidateUtils.notNull(pageNum) && ValidateUtils.notNull(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        PageHelper.orderBy(sort);

        List<TClLog> list = logManager.list(operateCode, operateName, content, null,
                null, null);

        PageHelper.clearPage();

        return list;
    }


    public List<TClLog> pageList(Map<String, ?> conditions, Integer pageNum, Integer pageSize, String sort) {
        if (ValidateUtils.notNull(pageNum) && ValidateUtils.notNull(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        PageHelper.orderBy(sort);

        String operateCode = MapUtils.getStringFromMap(conditions, "operateCode", null);
        String operateName = MapUtils.getStringFromMap(conditions, "operateName", null);
        String content = MapUtils.getStringFromMap(conditions, "content", null);
        String beginDate = MapUtils.getStringFromMap(conditions, "beginDate", null);
        String endDate = MapUtils.getStringFromMap(conditions, "endDate", null);

        Date createTimeFrom = (beginDate == null) ? null : DateUtils.parse(beginDate, "yyyyMMdd");
        Date createTimeTo = (endDate == null) ? null : DateUtils.parse(endDate, "yyyyMMdd");

        List<TClLog> list = logManager.list(operateCode, operateName, content, createTimeFrom, createTimeTo, null);

        PageHelper.clearPage();

        return list;
    }

    /**
     * 快速新增一条日志
     *
     * @param operateCode 操作代码
     * @param operateName 操作名称
     * @param relateId    关联ID
     * @param contentObj  内容对象
     * @param operator    操作人
     */
    public void writeLog(String operateCode, String operateName, Long relateId, Object contentObj, LoginUser operator) {
        logManager.writeLog(operateCode, operateName, relateId, contentObj, operator);
    }

}
