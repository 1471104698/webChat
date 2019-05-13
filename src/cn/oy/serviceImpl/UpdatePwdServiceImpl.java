package cn.oy.serviceImpl;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.service.UpdatePwdService;

public class UpdatePwdServiceImpl implements UpdatePwdService {

	//更新密码
	@Override
	public int updatePwd(String newPwd,String cfPwd, String account) {
		UserDao ud=new ImplD();
		if(!newPwd.equals(cfPwd))
			return -2;

		if(ud.isEmptyByAccount(account)!=null) {
			return ud.updatePwdDao(newPwd, account);
		}
		return -1;
	}
	

}
