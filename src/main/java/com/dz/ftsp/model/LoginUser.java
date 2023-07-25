package com.dz.ftsp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dz.ftsp.util.JsonUtils;
import org.springframework.util.ObjectUtils;

public class LoginUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // 用户表记录的ID
    public Long id;
    // 用户登录名称 用户代码
    public String userId;
    // 用户名称，用户昵称
    public String userName;
    // 所在营业部编号 总部编号为9999，营业部为各子柜台营业部编号
    public String branchNo;
    // CRM登录账号，与crmUserId冗余
    public String crmNo;
    // 当前子系统ID
    public Long sysId;

    // 是否为超级管理员
    public Long adminFlag;

    // 金服中台权限管理员
    private Integer ftspAdmin;

    // 当该用户为子系统权限管理员时， 管理的子系统列表
    private List<String> subSysAdminList = new ArrayList<>();

    // 当该用户为部门权限管理员时， 管理的部门列表
    private List<String> deptAdminList = new ArrayList<>();

    // 当该用户为通用二级授权管理员时，管理的二级授权类型列表
    private List<String> secondAdminList = new ArrayList<>();

    // 上次登录时间
    public String lastLoginTime;

    // 登陆类型 见FrameConstant.LOGIN_TYPE_FRAME 0： 金服  1：oa 2:CRM账户
    public Long loginType;

    // 所在部门代码
    private String deptNo;
    // 部门名称
    private String deptName;
    // 所在一级部门代码
    private String headDeptNo;
    // 所在一级部门名称
    private String headDeptName;
    // 部门代码路径
    private String deptNoPath;
    // 部门名称路径
    private String deptNamePath;

    // 人力资源系统员工编号
    private String hrmEmpNo;
    // CRM系统用户ID
    private String crmUserId;
    // OA登录账号
    private String oaLoginName;
    // 柜台操作员账号
    private String hsufOperatorNo;
    // BOP操作员账号
    private String bopOperatorNo;

    public String getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getCrmNo() {
        return crmNo;
    }

    public void setCrmNo(String crmNo) {
        this.crmNo = crmNo;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Long adminFlag) {
        this.adminFlag = adminFlag;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getFtspAdmin() {
        return ftspAdmin;
    }

    public void setFtspAdmin(Integer ftspAdmin) {
        this.ftspAdmin = ftspAdmin;
    }

    public List<String> getSubSysAdminList() {
        return subSysAdminList;
    }

    public void setSubSysAdminList(List<String> subSysAdminList) {
        this.subSysAdminList = subSysAdminList;
    }

    public List<String> getDeptAdminList() {
        return deptAdminList;
    }

    public void setDeptAdminList(List<String> deptAdminList) {
        this.deptAdminList = deptAdminList;
    }

    public List<String> getSecondAdminList() {
        return secondAdminList;
    }

    public void setSecondAdminList(List<String> secondAdminList) {
        this.secondAdminList = secondAdminList;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public Long getLoginType() {
        return loginType;
    }

    public void setLoginType(Long loginType) {
        this.loginType = loginType;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getHeadDeptNo() {
        return headDeptNo;
    }

    public void setHeadDeptNo(String headDeptNo) {
        this.headDeptNo = headDeptNo;
    }

    public String getHrmEmpNo() {
        return hrmEmpNo;
    }

    public void setHrmEmpNo(String hrmEmpNo) {
        this.hrmEmpNo = hrmEmpNo;
    }

    public String getCrmUserId() {
        return crmUserId;
    }

    public void setCrmUserId(String crmUserId) {
        this.crmUserId = crmUserId;
    }

    public String getOaLoginName() {
        return oaLoginName;
    }

    public void setOaLoginName(String oaLoginName) {
        this.oaLoginName = oaLoginName;
    }

    public String getHsufOperatorNo() {
        return hsufOperatorNo;
    }

    public void setHsufOperatorNo(String hsufOperatorNo) {
        this.hsufOperatorNo = hsufOperatorNo;
    }

    public String getBopOperatorNo() {
        return bopOperatorNo;
    }

    public void setBopOperatorNo(String bopOperatorNo) {
        this.bopOperatorNo = bopOperatorNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getHeadDeptName() {
        return headDeptName;
    }

    public void setHeadDeptName(String headDeptName) {
        this.headDeptName = headDeptName;
    }

    public String getDeptNoPath() {
        return deptNoPath;
    }

    public void setDeptNoPath(String deptNoPath) {
        this.deptNoPath = deptNoPath;
    }

    public String getDeptNamePath() {
        return deptNamePath;
    }

    public void setDeptNamePath(String deptNamePath) {
        this.deptNamePath = deptNamePath;
    }

    // 自定义新增方法

    public Map<String, Object> toMap() {
        return JsonUtils.toMap(this);
    }

    public static LoginUser init(Map<String, Object> map) {
        return JsonUtils.toObject(JsonUtils.toJson(map), LoginUser.class);
    }

    public boolean isSuperAdmin() {
        return this.getAdminFlag() == 1;
    }

    public boolean isFtspAdmin() {
        return this.getFtspAdmin() == 1;
    }

    public boolean isSubSysAdmin() {
        return !ObjectUtils.isEmpty(this.getSubSysAdminList());
    }

    public boolean isDeptAdmin() {
        return !ObjectUtils.isEmpty(this.getDeptAdminList());
    }

    public boolean isSecondAdmin() {
        return !ObjectUtils.isEmpty(this.getSecondAdminList());
    }

    public boolean hasAnyAdminPerm() {
        return this.isDeptAdmin()
                || this.isSubSysAdmin()
                || this.isFtspAdmin()
                || this.isSuperAdmin()
                || this.isSecondAdmin();
    }

    public boolean hasFtspAdminPerm() {
        return this.isSuperAdmin() || this.isFtspAdmin();
    }

    public boolean hasAnySubSysAdminPerm() {
        return this.hasFtspAdminPerm() || this.isSubSysAdmin();
    }

    public boolean hasAnyDeptAdminPerm() {
        return this.hasFtspAdminPerm() || this.isDeptAdmin();
    }

    public boolean hasAnySecondAdminPerm() {
        return this.hasFtspAdminPerm() || this.isSecondAdmin();
    }

    public boolean hasSubSysAdminPerm(String sysId) {
        return this.hasAnySubSysAdminPerm() && this.getSubSysAdminList().contains(sysId);
    }

    public boolean hasDeptAdminPerm(String headquarter) {
        return this.hasAnyDeptAdminPerm() && this.getDeptAdminList().contains(headquarter);
    }

    public boolean hasSecondAdminPerm(String secondPermCode) {
        return this.hasAnySecondAdminPerm() && this.getSecondAdminList().contains(secondPermCode);
    }

}
