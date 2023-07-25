package com.dz.ftsp.codelab.controller;


import com.dz.ftsp.codelab.constant.ClConst;
import com.dz.ftsp.codelab.constant.FrameConstant;
import com.dz.ftsp.codelab.model.cl.TClCategory;
import com.dz.ftsp.codelab.service.TClCategoryService;
import com.dz.ftsp.server.Result;
import com.dz.ftsp.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping(value = FrameConstant.MAPPING_ROOT + "/" + ClConst.SUB_SYS_CODE + "/category")
@Api(tags="商品类型")
public class TClCategoryController extends ClBaseController {
    @Autowired
    private TClCategoryService tClCategoryService;

    @RequestMapping("page")
    @ApiOperation("类型列表")
    public Result page(){
        List<TClCategory> data = tClCategoryService.getAll();
        return ResultUtils.success(data);
    }



}