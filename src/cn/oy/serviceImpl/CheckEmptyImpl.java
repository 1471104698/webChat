package cn.oy.serviceImpl;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.pojo.User;
import cn.oy.service.CheckEmpty;

public class CheckEmptyImpl implements CheckEmpty {
	
//	UserDao ud=(UserDao) ioc.MapIoc.MAP.get("ud");
	UserDao ud=new ImplD();
	@Override
	public User checkEmpty(String account, String pwd) {
		if(ud.LoginDao(account,pwd)!=null) {
			return ud.LoginDao(account, pwd);
		}
		return null;
	}

}
