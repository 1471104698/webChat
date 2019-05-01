package cn.oy.serviceImpl;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.service.UpdatePwdService;

public class UpdatePwdServiceImpl implements UpdatePwdService {

	UserDao ud=new ImplD();
	@Override
	public int updatePwd(String newPwd, String account) {
		if(ud.isEmpty(account)!=null) {
			return ud.updatePwdDao(newPwd, account);
		}
		return -1;
	}

}
