package com.dz.ftsp.codelab.controller;

import com.dz.ftsp.codelab.constant.ClConst;
import com.dz.ftsp.codelab.constant.FrameConstant;
import com.dz.ftsp.codelab.model.cl.TClCommodities;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesTransaction;
import com.dz.ftsp.codelab.service.ClCommoditiesService;
import com.dz.ftsp.codelab.service.ClCommoditiesTransactionService;
import com.dz.ftsp.server.Result;
import com.dz.ftsp.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 商品交易
 */
@RestController
@Api(tags="流水接口")
@RequestMapping(value = FrameConstant.MAPPING_ROOT + "/" + ClConst.SUB_SYS_CODE + "/transaction")

public class ClCommoditiesTransactionController extends ClBaseController{

    @Autowired
    private ClCommoditiesTransactionService commoditiesTransactionService;
    @Autowired
    private ClCommoditiesService commoditiesService;
    /**
     * 交易页面查询商品状态
     */
    @RequestMapping("/searchCommodities")
    public Result searchCommodities(@RequestBody Map<String,?> map){
        List<TClCommodities> list = commoditiesService.searchCommodities(map);
        return ResultUtils.success(list);
    }

    @RequestMapping("/purchaseCommodities")
    public Result purchaseCommodities(@RequestBody Map<String,?> map){
        commoditiesTransactionService.purchaseCommodities(map);
        return ResultUtils.success("购买成功");
    }

    @RequestMapping("/getItemPage")
    @ApiOperation("流水查询")
    public Result getItemPage(@RequestBody Map<String,?> map){
        List<TClCommoditiesTransaction> list = commoditiesTransactionService.getComoDittePage(map);

        return ResultUtils.success(list);
    }
}
