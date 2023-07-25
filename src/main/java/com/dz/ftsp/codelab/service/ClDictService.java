package com.dz.ftsp.codelab.service;


import com.dz.ftsp.codelab.config.ClTransactional;
import com.dz.ftsp.codelab.manager.ClDictManager;
import com.dz.ftsp.codelab.model.ClDictExp;
import com.dz.ftsp.codelab.model.ClDictImp;
import com.dz.ftsp.codelab.model.cl.TClDict;
import com.dz.ftsp.exception.FtspException;
import com.dz.ftsp.model.LoginUser;
import com.dz.ftsp.model.TFtspSysDict;

import com.dz.ftsp.utils.JsonUtils;
import com.dz.ftsp.utils.MapUtils;
import com.dz.ftsp.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典service
 */
@Service
public class ClDictService extends ClBaseService {

    @Autowired
    private ClDictManager dictManager;

    /**
     * 获取字典列表
     */
    public List<TClDict> getAll() {
        return dictManager.getAll(true);
    }

    /**
     * 批量新增
     *
     * @param list     新增节点
     * @param nodePath 父路径
     * @param operator 当前登录用户
     */
    @ClTransactional
    public void addBatch(List<Map> list, String nodePath, LoginUser operator) {
        // 校验是否柜台字典
        if (nodePath.startsWith("uf")) {
            throw new FtspException("不能自行调整柜台字典");
        }

        // 校验父节点是否存在
        if (ValidateUtils.isNotEmptyString(nodePath)) {
            TClDict pDict = dictManager.getByFullPath(nodePath);
            if (ValidateUtils.isNull(pDict)) {
                throw new FtspException("父节点不存在");
            }
        }

        // 获取兄弟节点，判断重复
        Set<String> paramRepeatSet = new HashSet<>();
        Set<String> dbRepeatSet;
        List<TClDict> broDicts = dictManager.getByNodePath(nodePath);
        if (ValidateUtils.notEmpty(broDicts)) {
            dbRepeatSet = broDicts.stream().map(TClDict::getNodeKey).collect(Collectors.toSet());
        } else {
            dbRepeatSet = new HashSet<>();
        }

        // 新增
        List logObj = new ArrayList();
        for (Map dictMap : list) {

            String nodeKey = MapUtils.getStringFromMapNotNull(dictMap, "nodeKey");
            String nodeValue = MapUtils.getStringFromMapNotNull(dictMap, "nodeValue");
            if (paramRepeatSet.contains(nodeKey)) {
                throw new FtspException("字典项（" + nodeKey + "-" + nodeValue + "）已重复，无法添加");
            }
            if (dbRepeatSet.contains(nodeKey)) {
                throw new FtspException("字典项（" + nodeKey + "-" + nodeValue + "）在数据库中已存在，无法添加");
            }

            TClDict dict = new TClDict();
            dict.setNodePath(nodePath);
            dict.setNodeKey(nodeKey);
            dict.setNodeValue(nodeValue);
            dict.setOrderNum(MapUtils.getIntegerFromMapNotNull(dictMap, "orderNum"));
            dict.setRemark(MapUtils.getStringFromMap(dictMap, "remark", null));
            dictManager.insert(dict, operator);
            paramRepeatSet.add(nodeKey);

            logObj.add(dict);
        }

        // 写日志
        logManager.writeLog("dictAddBatch", "批量新增日志", null, logObj,
                operator);
    }

    /**
     * 修改字典
     *
     * @param dict     字典信息
     * @param operator 当前登录用户
     */
    @ClTransactional
    public void update(TClDict dict, LoginUser operator) {
        TClDict old = dictManager.getById(dict.getId());
        if (ValidateUtils.isNull(old)) {
            throw new FtspException("原纪录不存在");
        }

        // 校验是否柜台字典
        if (old.getNodePath().startsWith("uf")) {
            throw new FtspException("不能自行调整柜台字典");
        }

        // 修改
        old.setUpdateId(operator.id);
        old.setUpdateNickName(operator.userName);
        old.setNodeValue(dict.getNodeValue());
        old.setOrderNum(dict.getOrderNum());
        old.setRemark(dict.getRemark());
        dictManager.update(old, operator);

        // 写日志
        logManager.writeLog("dictUpdate", "修改字典", old.getId(), old, operator);
    }

    /**
     * 删除
     *
     * @param id ID
     */
    @ClTransactional
    public void delete(Long id, LoginUser operator) {
        TClDict old = dictManager.getById(id);
        if (ValidateUtils.isNull(old)) {
            throw new FtspException("原纪录不存在");
        }

        // 校验是否柜台字典
        if (old.getNodePath().startsWith("uf")) {
            throw new FtspException("不能自行调整柜台字典");
        }

        // 查询是否有子节点
        String nodePath = (ValidateUtils.isNotEmptyString(old.getNodePath()) ? old.getNodePath() + "." : "") + old.getNodeKey();
        List<TClDict> children = dictManager.getByNodePath(nodePath);
        if (ValidateUtils.notEmpty(children)) {
            throw new FtspException("该节点仍有子节点，不能删除");
        }

        // 删除
        dictManager.delete(id);

        // 写日志
        logManager.writeLog("dictDelete", "删除字典", id, old, operator);
    }

    /**
     * 分析数据准备导入
     *
     * @param fileJsonObj 文件json对象
     * @return 分析结果
     */
    public List<ClDictImp> prepareImport(Object fileJsonObj) {
        if (!(fileJsonObj instanceof List)) {
            throw new FtspException("文件数据的格式不合法");
        }

        // 获取所有现存数据
        List<TClDict> oldList = dictManager.getAll(true);
        Map<String, TClDict> oldMap;
        if (ValidateUtils.notEmpty(oldList)) {
            oldMap = oldList.stream().collect(Collectors.toMap(
                    d -> (d.getNodePath() == null ? "" : d.getNodePath() + ".") + d.getNodeKey(),
                    d -> d,
                    (d1, d2) -> d1
            ));
        } else {
            oldMap = new HashMap<>();
        }

        // 开始递归分析
        List<ClDictImp> result = new ArrayList<>();
        List<Map> nodeList = (List) fileJsonObj;
        for (Map node : nodeList) {
            doFiltNode(node, result, null, oldMap);
        }

        return result;
    }

    /**
     * 递归方法：分析其中一个节点
     */
    private void doFiltNode(Map node, List<ClDictImp> result, String path, Map<String, TClDict> oldMap) {
        int operate = 0;
        String operateRemark = null;
        String selfPath;

        // 分析自身
        boolean pass = true;
        String key = "?";
        String value = null;
        try {
            String keyStr = MapUtils.getStringFromMap(node, "key", null);

            // 校验是否柜台字典
            if ((ValidateUtils.isNotEmptyString(path) && path.startsWith("uf")) ||
                    (ValidateUtils.isEmptyString(path) && "uf".equals(keyStr))) {
                throw new FtspException("不能自行调整柜台字典");
            }

            // 没有key或key带问号带小数点时失败
            if (ValidateUtils.isEmptyString(keyStr)) {
                throw new FtspException("缺少key");
            } else if (keyStr.contains("?") || keyStr.contains(".")) {
                throw new FtspException("key不能带问号或小数点");
            }
            key = keyStr;

            // 父节点分析失败时，子节点跟着失败
            if (ValidateUtils.isNotEmptyString(path) && path.contains("?")) {
                throw new FtspException("父节点分析失败");
            }

            // 没有value时
            value = MapUtils.getStringFromMap(node, "value", null);
            if (ValidateUtils.isEmptyString(value)) {
                throw new FtspException("缺少value");
            }
        } catch (Throwable t) {
            pass = false;
            if (t instanceof FtspException) {
                operateRemark = "忽略：" + t.getMessage();
            } else {
                operateRemark = "忽略：未知错误-" + t.getMessage();
            }
        }
        selfPath = (path == null ? "" : path + ".") + key;

        // 决定操作类型
        TClDict old = oldMap.get(selfPath);
        Integer orderNum = MapUtils.getIntegerFromMap(node, "orderNum", null);
        String remark = MapUtils.getStringFromMap(node, "remark", null);
        if (pass) {
            orderNum = orderNum == null ? 999999 : orderNum;
            remark = remark == null ? "" : remark;
            if (ValidateUtils.notNull(old)) {
                // 已有记录，不变或修改
                if (!value.equals(old.getNodeValue())) {
                    operate = 2;
                    operateRemark = "值调整";
                }
                if (!orderNum.equals(old.getOrderNum())) {
                    operate = 2;
                    operateRemark = "排序值调整";
                }
                String oldRemark = old.getRemark() == null ? "" : old.getRemark();
                if (!remark.equals(oldRemark)) {
                    operate = 2;
                    operateRemark = "备注调整";
                }
            } else {
                // 新增
                operate = 1;
                operateRemark = "新增";
            }
        }

        // 组装结果
        ClDictImp resultItem = new ClDictImp();
        resultItem.setId(operate == 2 ? old.getId() : null);
        resultItem.setNodePath(path);
        resultItem.setNodeKey(key);
        resultItem.setNodeValue(value);
        resultItem.setOrderNum(orderNum);
        resultItem.setRemark(remark);
        resultItem.setOperate(operate);
        resultItem.setOperateRemark(operateRemark);
        resultItem.setFullPath(selfPath);
        result.add(resultItem);

        // 遍历子节点
        Object childrenObj = node.get("children");
        if (childrenObj != null && childrenObj instanceof List) {
            List<Map> children = (List<Map>) childrenObj;
            for (Map nodeNode : children) {
                this.doFiltNode(nodeNode, result, selfPath, oldMap);
            }
        }
    }

    /**
     * 确认导入
     *
     * @param list     导入列表
     * @param operator 当前登录用户
     */
    @ClTransactional
    public void confirmImport(List<Map> list, LoginUser operator) {
        // 获取所有现存数据
        List<TClDict> oldList = dictManager.getAll(true);
        Map<String, TClDict> oldMap;
        if (ValidateUtils.notEmpty(oldList)) {
            oldMap = oldList.stream().collect(Collectors.toMap(
                    d -> (d.getNodePath() == null ? "" : d.getNodePath() + ".") + d.getNodeKey(),
                    d -> d,
                    (d1, d2) -> d1
            ));
        } else {
            oldMap = new HashMap<>();
        }

        // 循环操作
        List<TClDict> addList = new ArrayList();
        List<TClDict> updateList = new ArrayList();
        for (Map item : list) {
            Integer operate = MapUtils.getIntegerFromMap(item, "operate", 0);
            if (operate == 1) {
                // 新增
                TClDict dict = new TClDict();
                String nodePath = MapUtils.getStringFromMap(item, "nodePath", null);
                dict.setNodePath(ValidateUtils.isNotEmptyString(nodePath) ? nodePath : null);
                dict.setNodeKey(MapUtils.getStringFromMapNotNull(item, "nodeKey"));
                dict.setNodeValue(MapUtils.getStringFromMapNotNull(item, "nodeValue"));
                dict.setOrderNum(MapUtils.getIntegerFromMapNotNull(item, "orderNum"));
                dict.setRemark(MapUtils.getStringFromMap(item, "remark", null));
                dictManager.insert(dict, operator);
                addList.add(dict);
            } else if (operate == 2) {
                TClDict old = oldMap.get(MapUtils.getStringFromMapNotNull(item, "fullPath"));
                old.setNodeValue(MapUtils.getStringFromMapNotNull(item, "nodeValue"));
                old.setOrderNum(MapUtils.getIntegerFromMapNotNull(item, "orderNum"));
                old.setRemark(MapUtils.getStringFromMap(item, "remark", null));
                dictManager.update(old, operator);
                updateList.add(old);
            }
        }

        // 写日志
        Map<String, List> logObj = new HashMap<>();
        logObj.put("addList", addList);
        logObj.put("updateList", updateList);
        logManager.writeLog("dictImport", "导入字典", null, logObj, operator);
    }

    /**
     * 导出
     *
     * @param resp 响应对象
     */
    public void export(HttpServletResponse resp) {
        // 查询全表
        List<TClDict> dicts = dictManager.getAll(true);

        // 获取对象树
        List<ClDictExp> expTree = getExportTree(dicts, null);

        try {
            // 设置头信息
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("dw-dicts.json", "UTF-8"));

            // 转JSON
            String json = JsonUtils.writeObjectAsString(expTree);
            resp.getOutputStream().write(json.getBytes());
        } catch (Exception e) {
            throw new FtspException("写入JSON文件遇到问题：" + e.getMessage(), e);
        }
    }

    /**
     * 递归方法，获取导出的对象树
     *
     * @param dicts 字典列表
     * @param path  当前字典路径
     */
    private List<ClDictExp> getExportTree(List<TClDict> dicts, String path) {
        List<ClDictExp> list = new ArrayList<>();
        dicts.stream().filter(dict ->
                (path == null && dict.getNodePath() == null) ||
                        (path != null && path.equals(dict.getNodePath()))
        ).forEach(dict -> {
            ClDictExp exp = new ClDictExp(dict);
            exp.setChildren(getExportTree(dicts, (dict.getNodePath() == null ? "" : dict.getNodePath() + ".") + dict.getNodeKey()));
            list.add(exp);
        });
        return list;
    }

    /**
     * 根据父路径获取
     *
     * @param nodePath 父路径
     * @return 字典列表
     */
    public List<TClDict> getByNodePath(String nodePath) {
        return dictManager.getByNodePath(nodePath);
    }

    /**
     * 获取字典
     *
     * @param dictPaths 路径列表
     * @param parent    父路径
     * @return 字典map
     */
    public Map<String, List<TFtspSysDict>> getDicts(List<String> dictPaths, String parent) {
        Map<String, List<TFtspSysDict>> result = new HashMap<>();
        if (ValidateUtils.notEmpty(dictPaths)) {
            String finalParent = ValidateUtils.isNotEmptyString(parent) ? parent + "." : "";
            dictPaths.forEach(nodePath -> {
                List<TClDict> entries = dictManager.getByNodePath(finalParent + nodePath);
                if (ValidateUtils.notNull(entries)) {
                    result.put(nodePath, entries.stream().map(entry -> {
                        TFtspSysDict dict = new TFtspSysDict();
                        dict.setDictValue(entry.getNodeKey());
                        dict.setDictKey(entry.getNodeValue());
                        return dict;
                    }).collect(Collectors.toList()));
                } else {
                    result.put(nodePath, new ArrayList<>());
                }
            });
        }
        return result;
    }

    /**
     * 获取字典节点
     *
     * @param dictPaths 路径列表
     * @param parent    父路径
     * @return 字典map
     */
    public Map<String, TFtspSysDict> getDictNodes(List<String> dictPaths, String parent) {
        Map<String, TFtspSysDict> result = new HashMap<>();
        if (ValidateUtils.notEmpty(dictPaths)) {
            String finalParent = ValidateUtils.isNotEmptyString(parent) ? parent + "." : "";
            dictPaths.forEach(nodePath -> {
                TClDict dictEntry = dictManager.getByFullPath(finalParent + nodePath);
                if (ValidateUtils.notNull(dictEntry)) {
                    TFtspSysDict dict = new TFtspSysDict();
                    dict.setDictValue(dictEntry.getNodeKey());
                    dict.setDictKey(dictEntry.getNodeValue());
                    result.put(nodePath, dict);
                }
            });
        }
        return result;
    }

    /**
     * 获取单个字典节点
     *
     * @param path 字典路径
     * @param key  字典key
     */
    public TClDict getNode(String path, String key) {
        String fullPath = (ValidateUtils.isEmptyString(path) ? "" : path + ".") + key;
        return dictManager.getByFullPath(fullPath);
    }
}
