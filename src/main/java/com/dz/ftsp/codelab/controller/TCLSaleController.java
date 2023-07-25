package com.dz.ftsp.codelab.controller;

import com.dz.ftsp.codelab.constant.ClConst;
import com.dz.ftsp.codelab.constant.FrameConstant;
import com.dz.ftsp.codelab.model.cl.TClCommoditiesTransaction;
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


@RestController
@RequestMapping(value = FrameConstant.MAPPING_ROOT + "/" + ClConst.SUB_SYS_CODE + "/sale")
@Api(tags="商品销售额度")
public class TCLSaleController extends ClBaseController{

    @Autowired
    private ClCommoditiesTransactionService clCommoditiesTransactionService;

    @RequestMapping("/one")
    @ApiOperation("获取当日销售冠军")
    public Result SaleOne() {
        TClCommoditiesTransaction product = clCommoditiesTransactionService.SaleOneProduct();
        return ResultUtils.success(product);
    }

    @RequestMapping("/day")
    @ApiOperation("获取日期销售")
    public Result getSale(@RequestBody Map<String, ?> map) {
        List<TClCommoditiesTransaction> sale = clCommoditiesTransactionService.getDailySale(map);
        return ResultUtils.success(sale);
    }
}
