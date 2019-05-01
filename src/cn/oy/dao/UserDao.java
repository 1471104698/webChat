package cn.oy.dao;

import cn.oy.pojo.User;

public interface UserDao {
	
	//检查用户是否为空
	User isEmpty(String account);
	//得到用户信息
	User LoginDao(String account, String pwd);
	//注册用户
	int regDao(User user);
	//找回密码
	int findPwdDao(String account, String name, String tel);	
	//修改密码
	int updatePwdDao(String newPwd,String account);
}
