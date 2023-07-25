package com.dz.ftsp.codelab.controller;

import com.dz.ftsp.codelab.constant.ClConst;
import com.dz.ftsp.codelab.constant.FrameConstant;
import com.dz.ftsp.codelab.model.cl.TClDict;
import com.dz.ftsp.codelab.service.ClDictService;
import com.dz.ftsp.model.LoginUser;
import com.dz.ftsp.model.TFtspSysDict;
import com.dz.ftsp.server.Result;
import com.dz.ftsp.utils.LoginUserUtils;
import com.dz.ftsp.utils.MapUtils;
import com.dz.ftsp.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 字典controller
 */
@RestController
@RequestMapping(value = FrameConstant.MAPPING_ROOT + "/" + ClConst.SUB_SYS_CODE + "/dict")
public class ClDictController extends ClBaseController {

    @Autowired
    private ClDictService dictService;


    /**
     * 查询
     */
    @RequestMapping("/getList")
    public Result getList(@RequestBody Map<String, ?> map) {
        List<TClDict> list = dictService.getAll();
        return ResultUtils.success(list);
    }

    /**
     * 批量新增
     */
    @RequestMapping("/addBatch")
    public Result addBatch(@RequestBody Map<String, ?> map, HttpServletRequest req) {
        List<Map> list = MapUtils.getListFromMapNotNull(map, "list");
        String nodePath = MapUtils.getStringFromMap(map, "nodePath", "");

        LoginUser operator = super.getLoginUser(req);
        dictService.addBatch(list, nodePath, operator);
        return ResultUtils.success(null);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Map<String, ?> map, HttpServletRequest req) {
        TClDict dict = new TClDict();
        dict.setId(MapUtils.getLongFromMapNotNull(map, "id"));
        dict.setNodeValue(MapUtils.getStringFromMapNotNull(map, "nodeValue"));
        dict.setOrderNum(MapUtils.getIntegerFromMapNotNull(map, "orderNum"));
        dict.setRemark(MapUtils.getStringFromMap(map, "remark", null));

        LoginUser operator = super.getLoginUser(req);
        dictService.update(dict, operator);
        return ResultUtils.success(null);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody Map<String, ?> map, HttpServletRequest req) {
        Long id = MapUtils.getLongFromMapNotNull(map, "id");

        LoginUser operator = LoginUserUtils.getLoginUser(req);
        dictService.delete(id, operator);
        return ResultUtils.success(null);
    }

    /**
     * 准备导入
     */
    /*
    @RequestMapping("/prepareImport")
    public Result prepareImport(@RequestBody Map<String, ?> map, HttpServletRequest req) {
        String filePath = MapUtils.getStringFromMapNotNull(map, "filePath");
        String fileGroup = MapUtils.getStringFromMapNotNull(map, "fileGroup");

        // 读取文件
        byte[] fileBytes = null;
        try {
            String decodeFilePath = new String(Base64.decodeBase64(filePath));
            fileBytes = fastDfsClientUtil.downloadFile(fileGroup, decodeFilePath);
        } catch (Throwable t) {
            throw new FtspException("读取文件失败！原因：" + t.getMessage(), t);
        }
        if (ValidateUtils.isNull(fileBytes) || fileBytes.length <= 0) {
            throw new FtspException("文件内容为空");
        }

        // 将JSON文件转成对象
        String fileJsonStr = new String(fileBytes);
        Object fileJsonObj;
        try {
            fileJsonObj = JsonUtils.getObjectFromString(fileJsonStr);
        } catch (Throwable t) {
            throw new FtspException("转化文件JSON失败！原因：" + t.getMessage(), t);
        }

        // 分析导入数据
        List<StDictImp> result = dictService.prepareImport(fileJsonObj);

        return ResultUtils.success(result);
    }
*/
    /**
     * 确认导入
     */
 /*
    @RequestMapping("/confirmImport")
    public Result confirmImport(@RequestBody Map<String, ?> map, HttpServletRequest req) {
        List<Map> list = MapUtils.getListFromMapNotNull(map, "list");

        LoginUser operator = super.getLoginUser(req);
        dictService.confirmImport(list, operator);
        return ResultUtils.success(null);
    }
*/
    /**
     * 导出
     */
    @RequestMapping("/export")
    public void export(HttpServletResponse resp) {
        dictService.export(resp);
    }

    /**
     * 获取字典
     */
    @RequestMapping("/getDicts")
    public Result getDicts(@RequestBody Map<String, ?> map) {
        List<String> dictPaths = MapUtils.getListFromMapNotNull(map, "dictPaths");
        String parent = MapUtils.getStringFromMap(map, "parent", null);

        Map<String, List<TFtspSysDict>> result = dictService.getDicts(dictPaths, parent);
        return ResultUtils.success(result);
    }

    /**
     * 获取字典
     */
    @RequestMapping("/getDictNodes")
    public Result getDictNodes(@RequestBody Map<String, ?> map) {
        List<String> dictPaths = MapUtils.getListFromMapNotNull(map, "dictPaths");
        String parent = MapUtils.getStringFromMap(map, "parent", null);

        Map<String, TFtspSysDict> result = dictService.getDictNodes(dictPaths, parent);
        return ResultUtils.success(result);
    }

}
