package cn.oy.serviceImpl;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.pojo.User;
import cn.oy.service.LoginService;

public class LoginServiceImpl implements LoginService {
	
//	UserDao ud=(UserDao) ioc.MapIoc.MAP.get("ud");
	UserDao ud=new ImplD();
	@Override
	public User checkLogin(String account, String pwd) {
		if(ud.LoginDao(account,pwd)!=null) {
			return ud.LoginDao(account, pwd);
		}
		return null;
	}
	
	@Override
	public boolean checkCode(String vcode, String ocode) {
		vcode=vcode.toLowerCase();		//转小写，达到不区分大小写的作用
		if(ocode.equals(vcode)) 
		return true;
		return false;
	}

}
