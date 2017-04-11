package com.yzz.entity;

public class SysUser {
    private String sysUserId;

    private String wxPublicAccountId;

    private String sysUserName;

    private String password;

    private String headUrl;

    private String email;

    private String tel;

    private String registerTime;

    private Integer sysUserRole;

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId == null ? null : sysUserId.trim();
    }

    public String getWxPublicAccountId() {
        return wxPublicAccountId;
    }

    public void setWxPublicAccountId(String wxPublicAccountId) {
        this.wxPublicAccountId = wxPublicAccountId == null ? null : wxPublicAccountId.trim();
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName == null ? null : sysUserName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime == null ? null : registerTime.trim();
    }

    public Integer getSysUserRole() {
        return sysUserRole;
    }

    public void setSysUserRole(Integer sysUserRole) {
        this.sysUserRole = sysUserRole;
    }
}