package cn.oy.service;

import java.util.List;

import cn.oy.pojo.User;

public interface PageService {

	/**
	 * 查询用户总数
	 */
	int getTotaService(String uid);	
	/**
	 * 查询某一页
	 */
	List<User> queryUserService(int currentPage,int pageSize,String uid);	
}
