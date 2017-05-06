package com.yzz.util;

public interface UserOperatedState {
	
	public static final String REGISTER_SUCCESS = "注册成功";
	public static final String REGISTER_FAILURE = "注册失败";
	public static final String LOGIN_SUCCESS = "登录成功";
	public static final String LOGIN_USER_NAME_ERROR = "账号错误";
	public static final String LOGIN_PASSWORD_ERROR = "密码错误";
	public static final String LOGOUT_SUCCESS = "注销成功";

	public static final String INSERT_SUCCESS = "添加成功";
	public static final String INSERT_FAILURE = "添加失败";
	public static final String DELETE_SUCCESS = "删除成功";
	public static final String DELETE_FAILURE = "删除失败";
	public static final String UPDATE_SUCCESS = "更新成功";
	public static final String UPDATE_FAILURE = "更新失败";
	public static final String SELECT_SUCCESS = "查询成功";
	public static final String SELECT_FAILURE = "查询失败";
	
	public static final String NO_PARAM = "参数值为空";
	public static final String NO_DATA = "无数据";
	
	public static final String INNER_ERROR = "服务器内部错误";
	
	public static final String CREATE_MENU_SUCCESS = "菜单创建成功";
	public static final String CREATE_MENU_ERROR = "数据库无菜单数据";
	
}
