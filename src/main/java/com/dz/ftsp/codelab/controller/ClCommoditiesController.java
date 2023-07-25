package com.dz.ftsp.codelab.controller;

import com.dz.ftsp.codelab.constant.ClConst;
import com.dz.ftsp.codelab.constant.FrameConstant;
import com.dz.ftsp.codelab.model.cl.TClCommodities;
import com.dz.ftsp.codelab.service.ClCommoditiesService;
import com.dz.ftsp.server.Result;
import com.dz.ftsp.utils.MapUtils;
import com.dz.ftsp.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品台账controller
 */
@RestController
@RequestMapping(value = FrameConstant.MAPPING_ROOT + "/" + ClConst.SUB_SYS_CODE + "/commodities")
@Api(tags="商品管理")
public class ClCommoditiesController extends ClBaseController {
    @Autowired
    private ClCommoditiesService commoditiesService;

    /**
     * 分页查询
     */

    @RequestMapping("/list")
    @ApiOperation("分页查询")
    public Result list(@RequestBody Map<String, ?> map) {
        Integer pageNum = MapUtils.getIntegerFromMap(map, "pageNum", 1);
        Integer pageSize = MapUtils.getIntegerFromMap(map, "pageSize", Integer.MAX_VALUE);
        String sort = MapUtils.getStringFromMap(map, "sort", "id desc", true);
        List<TClCommodities> list = commoditiesService.pageList(map, pageNum, pageSize, sort);
        return ResultUtils.success(pageNum, pageSize, null, list);
    }

    /**
     * 新增商品
     */

    @RequestMapping("/addCommodities")
    @ApiOperation("新增商品")
    public Result addCommodities(@RequestBody Map<String, ?> map) {
        //调用service层的方法，判断商品名称是否已存在
        if(commoditiesService.isCommoditiesNameExists(map)){
            return ResultUtils.success("商品已存在");
        }else{
            // 调用服务层方法，执行上架商品
            Long id = commoditiesService.addCommodities(map);
            return ResultUtils.success("上架成功");
        }
    }


    @RequestMapping("/editCommodities")
    @ApiOperation("编辑商品")
    public Result editCommodities(@RequestBody Map<String, ?> map) {
        //调用service层执行商品修改逻辑
        commoditiesService.editCommodities(map);

        return ResultUtils.success("修改成功");
    }

    @RequestMapping("/deleteCommodities")
    @ApiOperation("删除商品")
    public Result deleteCommodities(@RequestBody Map<String, ?> map) {
        //调用service层执行商品下架
        commoditiesService.deleteCommodities(map);

        return ResultUtils.success("下架成功");
    }


    @RequestMapping("/getLatestId")
    public Result getLatestId() {
        //调用service层执行获取最新id
        Long latestId = commoditiesService.getLatestId();
        return ResultUtils.success(latestId);
    }


}
