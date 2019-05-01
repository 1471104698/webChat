package cn.oy.serviceImpl;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.pojo.User;
import cn.oy.service.RegService;

public class RegServiceImpl implements RegService {

	UserDao ud=new ImplD();
	@Override
	public int regService(User user) {
		if(ud.isEmpty(user.getAccount())==null) {
			return ud.regDao(user);
		}
		return -1;
	}

}
