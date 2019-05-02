package cn.oy.service;

public interface UpdatePwdService {
	
	//更新密码
	int updatePwd(String newPwd,String cfPwd,String account);
}
