package cn.oy.service;

import cn.oy.pojo.User;

public interface LoginService {
	
	User checkLogin(String account,String pwd);
	
	boolean checkCode(String vcode, String ocode) ;
}
