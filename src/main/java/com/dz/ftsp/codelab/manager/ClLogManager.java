package com.dz.ftsp.codelab.manager;

import com.dz.ftsp.codelab.dao.cl.TClLogMapper;
import com.dz.ftsp.codelab.model.cl.TClLog;
import com.dz.ftsp.codelab.model.cl.TClLogExample;
import com.dz.ftsp.model.LoginUser;
import com.dz.ftsp.utils.JsonUtils;
import com.dz.ftsp.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 员工账号设置manager
 */
@Component
public class ClLogManager extends ClBaseManager {

    @Autowired
    private TClLogMapper stLogMapper;

    /**
     * 查询
     *
     * @param operateCode    操作编号
     * @param operateName    操作名称
     * @param content        内容
     * @param createTimeFrom 创建时间开始
     * @param createTimeTo   创建时间结束
     * @param relateId       关联ID
     */
    public List<TClLog> list(String operateCode, String operateName, String content, Date createTimeFrom,
                             Date createTimeTo, Long relateId) {
        TClLogExample example = new TClLogExample();
        TClLogExample.Criteria criteria = example.createCriteria();
        if (ValidateUtils.isNotEmptyString(operateCode)) {
            criteria.andOperateCodeLike("%" + operateCode + "%");
        }
        if (ValidateUtils.isNotEmptyString(operateName)) {
            criteria.andOperateNameLike(operateName);
        }
        if (ValidateUtils.isNotEmptyString(content)) {
            criteria.andContentLike("%" + content + "%");
        }
        if (ValidateUtils.notNull(createTimeFrom)) {
            criteria.andCreateTimeGreaterThanOrEqualTo(createTimeFrom);
        }
        if (ValidateUtils.notNull(createTimeTo)) {
            criteria.andCreateTimeLessThanOrEqualTo(createTimeTo);
        }
        if (ValidateUtils.notNull(relateId)) {
            criteria.andRelateIdEqualTo(relateId);
        }

        return stLogMapper.selectByExample(example);
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
        Date now = new Date();

        // 截取内容长度
        String content = JsonUtils.writeObjectAsString(contentObj);
        if (content.length() > 2000) {
            content = content.substring(0, 2000);
        }

        // 新增日志
        TClLog log = new TClLog();
        log.setCreateTime(now);
        log.setCreateId(operator.id);
        log.setCreateNickName(operator.userName);
        log.setOperateCode(operateCode);
        log.setOperateName(operateName);
        log.setRelateId(relateId);
        log.setContent(content);
        stLogMapper.insert(log);
    }

}
