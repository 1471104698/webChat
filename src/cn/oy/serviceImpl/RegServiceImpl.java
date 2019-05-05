package cn.oy.serviceImpl;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.pojo.User;
import cn.oy.service.RegService;

public class RegServiceImpl implements RegService {

	
	@Override
	public int regService(User user) {
		UserDao ud=new ImplD();
//		UserDao ud=(UserDao) util.MapIoc.MAP.get("ud");
		if(null==ud.isEmpty(user.getAccount())) {
			return ud.regDao(user);
		}
		return -1;
	}

}
