package com.dz.ftsp.codelab.manager;

import com.dz.ftsp.codelab.dao.cl.TClDictMapper;
import com.dz.ftsp.codelab.model.cl.TClDict;
import com.dz.ftsp.codelab.model.cl.TClDictExample;
import com.dz.ftsp.model.LoginUser;
import com.dz.ftsp.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典manager
 */
@Component
public class ClDictManager extends ClBaseManager {

    @Autowired
    private TClDictMapper dictMapper;

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 字典信息
     */
    public TClDict getById(Long id) {
        return dictMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据父路径获取
     *
     * @param nodePath 父路径
     * @return 字典列表
     */
    public List<TClDict> getByNodePath(String nodePath) {
        TClDictExample example = new TClDictExample();
        TClDictExample.Criteria criteria = example.createCriteria();
        if (ValidateUtils.isNotEmptyString(nodePath)) {
            criteria.andNodePathEqualTo(nodePath);
        } else {
            criteria.andNodePathIsNull();
        }
        example.setOrderByClause("node_path asc, order_num asc, create_time asc");
        return dictMapper.selectByExample(example);
    }

    /**
     * 根据父路径获取 - 返回 Map<nodeKey,nodeValue>
     * @param nodePath
     * @return
     */
    public Map<String, String> getMapByNodePath(String nodePath) {
        Map<String, String> dict = null;
        List<TClDict> list = this.getByNodePath(nodePath);

        if (ValidateUtils.notNull(list)) {
            dict = list.stream().collect(Collectors.toMap(d -> d.getNodeKey(), d -> d.getNodeValue(), (d1, d2) -> d1));
        }
        return dict;
    }

    /**
     * 根据全路径获取
     *
     * @param fullPath 全路径
     * @return 字典列表
     */
    public TClDict getByFullPath(String fullPath) {
        TClDictExample example = new TClDictExample();
        TClDictExample.Criteria criteria = example.createCriteria();
        if (fullPath.contains(".")) {
            int docIndex = fullPath.lastIndexOf(".");
            criteria.andNodePathEqualTo(fullPath.substring(0, docIndex))
                    .andNodeKeyEqualTo(fullPath.substring(docIndex + 1));
        } else {
            criteria.andNodePathIsNull()
                    .andNodeKeyEqualTo(fullPath);
        }
        List<TClDict> list = dictMapper.selectByExample(example);
        return ValidateUtils.notEmpty(list) ? list.get(0) : null;
    }

    /**
     * 获取字典列表
     */
    public List<TClDict> getAll(boolean exceptUf) {
        TClDictExample example = new TClDictExample();
        if (exceptUf) {
            example.createCriteria().andNodePathNotLike("uf%");
            example.or().andNodePathIsNull().andNodeKeyNotEqualTo("uf");
        }
        example.setOrderByClause("NODE_PATH ASC, ORDER_NUM ASC, CREATE_TIME ASC");
        return dictMapper.selectByExample(example);
    }

    /**
     * 插入记录
     *
     * @param dict 字典信息
     */
    public void insert(TClDict dict, LoginUser operator) {
        Date now = new Date();
        dict.setCreateTime(now);
        dict.setCreateId(operator.id);
        dict.setCreateNickName(operator.userName);
        dict.setUpdateTime(now);
        dict.setUpdateId(operator.id);
        dict.setUpdateNickName(operator.userName);
        dictMapper.insert(dict);
    }

    /**
     * 修改记录
     *
     * @param dict 字典信息
     */
    public void update(TClDict dict, LoginUser operator) {
        dict.setUpdateTime(new Date());
        dict.setUpdateId(operator.id);
        dict.setUpdateNickName(operator.userName);
        dictMapper.updateByPrimaryKey(dict);
    }

    /**
     * 删除记录
     *
     * @param id ID
     */
    public void delete(Long id) {
        dictMapper.deleteByPrimaryKey(id);
    }
}
