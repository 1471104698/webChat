package cn.oy.serviceImpl;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.pojo.User;

public class UpdateInfoServiceImpl implements cn.oy.service.UpdateInfoService {

	UserDao ud=new ImplD();
	
	@Override
	public int updateInfoService(User user) {
		if(ud.isEmpty(user.getAccount())!=null) {
			return ud.updateInfoDao(user);
		}
		return -1;
	}

}
