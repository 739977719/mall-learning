package com.dz.ftsp.codelab.controller;

import com.dz.ftsp.model.LoginUser;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * Controller基类
 */
@CrossOrigin
public class ClBaseController {

    /**
     * 获取当前登录用户的工作流ID
     */
    protected LoginUser getLoginUser(HttpServletRequest req) {
        // 获取ID
        LoginUser loginUser = new LoginUser();
        loginUser.setId(63L);
        loginUser.setUserName("root");

        return loginUser;
    }

}
