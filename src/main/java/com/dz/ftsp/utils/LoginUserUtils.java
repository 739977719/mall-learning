package com.dz.ftsp.utils;

import com.dz.ftsp.model.LoginUser;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoginUserUtils {

    public static LoginUser getLoginUser(HttpServletRequest request) {
        // 获取当前登录的用户
        LoginUser user = new LoginUser();
        user.setId(63L);
        user.setUserName("root");
        return user;
    }

    public static Long getId(HttpServletRequest request) {
        LoginUser loginUser = getLoginUser(request);
        return getId(loginUser);
    }

    public static Long getId(LoginUser loginUser) {
        Long id = null;
        if (loginUser != null) {
            id = loginUser.getId();
        }
        return id;
    }

    public static String getUserId(HttpServletRequest request) {
        LoginUser loginUser = getLoginUser(request);
        return getUserId(loginUser);
    }

    public static String getUserId(LoginUser loginUser) {
        String userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }
        return userId;
    }

    public static String getUserMame(HttpServletRequest request) {
        LoginUser loginUser = getLoginUser(request);
        return getUserMame(loginUser);
    }

    public static String getUserMame(LoginUser loginUser) {
        String userName = null;
        if (loginUser != null) {
            userName = loginUser.getUserName();
        }
        return userName;
    }

    /**
     * 用来根据当前用户调整管理范围
     *
     * @param loginUser
     * @return
     */
    public static PermParam getPermParam(LoginUser loginUser) {
        return getPermParam(loginUser, null, null, null);
    }

    /**
     * 用来根据当前用户调整管理范围
     *
     * @param loginUser
     * @param inputSysList
     * @param inputDeptList
     * @param inputSecondList
     * @return
     */
    public static PermParam getPermParam(LoginUser loginUser, List<String> inputSysList, List<String> inputDeptList, List<String> inputSecondList) {
        // 空及空列表表示不限制，对于没有权限的，筛一条不存在的记录进去
        // 拥有的子系统权限列表
        List<String> sysPermList = null;
        // 拥有的部门权限列表
        List<String> deptPermList = null;
        // 拥有的通用二级授权管理员类型列表
        List<String> secondPermList = null;
        if (!loginUser.hasFtspAdminPerm()) {
            if (loginUser.hasAnyAdminPerm()) {
                if (loginUser.hasAnySubSysAdminPerm()) {
                    sysPermList = loginUser.getSubSysAdminList();
                    if (!ObjectUtils.isEmpty(inputSysList)) {
                        sysPermList = sysPermList.stream().filter(x -> inputSysList.contains(x)).collect(Collectors.toList());
                    }
                }
                if (loginUser.hasAnyDeptAdminPerm()) {
                    deptPermList = loginUser.getDeptAdminList();
                    if (!ObjectUtils.isEmpty(inputDeptList)) {
                        deptPermList = deptPermList.stream().filter(x -> inputDeptList.contains(x)).collect(Collectors.toList());
                    }
                }
                if (loginUser.hasAnySecondAdminPerm()) {
                    secondPermList = loginUser.getSecondAdminList();
                    if (!ObjectUtils.isEmpty(inputSecondList)) {
                        secondPermList = secondPermList.stream().filter(x -> inputSecondList.contains(x)).collect(Collectors.toList());
                    }
                }
            }
            if (ObjectUtils.isEmpty(sysPermList)) {
                sysPermList = Collections.singletonList("-1");
            }
            if (ObjectUtils.isEmpty(deptPermList)) {
                deptPermList = Collections.singletonList("none");
            }
            if (ObjectUtils.isEmpty(secondPermList)) {
                secondPermList = Collections.singletonList("none");
            }
        }
        return new PermParam(sysPermList, deptPermList, secondPermList);
    }

    public static class PermParam {
        // 拥有的子系统权限列表
        private List<String> sysPermList;
        // 拥有的部门权限列表
        private List<String> deptPermList;
        // 拥有的通用二级授权管理员类型列表
        private List<String> secondPermList;

        public PermParam(List<String> sysPermList, List<String> deptPermList, List<String> secondPermList) {
            this.sysPermList = sysPermList;
            this.deptPermList = deptPermList;
            this.secondPermList = secondPermList;
        }

        public List<String> getSysPermList() {
            return sysPermList;
        }

        public void setSysPermList(List<String> sysPermList) {
            this.sysPermList = sysPermList;
        }

        public List<String> getDeptPermList() {
            return deptPermList;
        }

        public void setDeptPermList(List<String> deptPermList) {
            this.deptPermList = deptPermList;
        }

        public List<String> getSecondPermList() {
            return secondPermList;
        }

        public void setSecondPermList(List<String> secondPermList) {
            this.secondPermList = secondPermList;
        }
    }
}
