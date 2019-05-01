package cn.oy.serviceImpl;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.service.FindPwdService;

public class FindPwdServiceImpl implements FindPwdService {

	UserDao ud=new ImplD();
	@Override
	public int FindService(String account, String name, String tel) {
		if(ud.findPwdDao(account, name, tel)>0) {
			return 1;
		}
		return -1;
	}

}
