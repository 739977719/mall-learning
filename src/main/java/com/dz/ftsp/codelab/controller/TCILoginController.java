package com.dz.ftsp.codelab.controller;

import com.dz.ftsp.codelab.constant.ClConst;
import com.dz.ftsp.codelab.constant.FrameConstant;
import com.dz.ftsp.codelab.service.TCIUserService;
import com.dz.ftsp.server.Result;
import com.dz.ftsp.utils.MapUtils;
import com.dz.ftsp.utils.ResultUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = FrameConstant.MAPPING_ROOT + "/" + ClConst.SUB_SYS_CODE + "/user")
@Api(tags="用户操作")
public class TCILoginController extends ClBaseController {

    @Autowired
    private TCIUserService userService;

    @RequestMapping("/login")
    @ApiOperation("登入")
    public Result login(@RequestBody Map<String, ?> map) {
        String userName = MapUtils.getStringFromMap(map, "userName","admin",true);
        String password = MapUtils.getStringFromMap(map, "password","admin",true);
        boolean isLoggedIn = userService.login(userName,password);
        if (isLoggedIn) {
            return ResultUtils.success(true) ;
        } else {
            return ResultUtils.success(false) ;
        }
    }
}
