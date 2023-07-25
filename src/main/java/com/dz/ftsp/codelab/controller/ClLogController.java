package com.dz.ftsp.codelab.controller;

import com.dz.ftsp.server.Result;
import com.dz.ftsp.codelab.constant.FrameConstant;
import com.dz.ftsp.codelab.constant.ClConst;
import com.dz.ftsp.codelab.model.cl.TClLog;
import com.dz.ftsp.codelab.service.ClLogService;
import com.dz.ftsp.utils.MapUtils;
import com.dz.ftsp.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *
 * 日志controller
 */
@RestController
@RequestMapping(value = FrameConstant.MAPPING_ROOT + "/" + ClConst.SUB_SYS_CODE + "/log")
public class ClLogController extends ClBaseController {

    @Autowired
    private ClLogService logService;

    /**
     * 分页查询
     */
    @RequestMapping("/list")
    public Result list(@RequestBody Map<String, ?> map) {

        Integer pageNum = MapUtils.getIntegerFromMap(map, "pageNum", 1);
        Integer pageSize = MapUtils.getIntegerFromMap(map, "pageSize", Integer.MAX_VALUE);
        String sort = MapUtils.getStringFromMap(map, "sort", "id desc", true);
        List<TClLog> list = logService.pageList(map, pageNum, pageSize, sort);
        return ResultUtils.success(pageNum, pageSize, null, list);
    }

}
