package ioc;

import java.util.HashMap;
import java.util.Map;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.service.CheckEmpty;
import cn.oy.service.FindPwdService;
import cn.oy.service.RegService;
import cn.oy.service.UpdatePwdService;
import cn.oy.serviceImpl.CheckEmptyImpl;
import cn.oy.serviceImpl.FindPwdServiceImpl;
import cn.oy.serviceImpl.RegServiceImpl;
import cn.oy.serviceImpl.UpdatePwdServiceImpl;

public class MapIoc {

	public final static Map<String,Object> MAP=new HashMap<String, Object>();
	
	static {
		CheckEmpty ce=new CheckEmptyImpl();
		MAP.put("ce", ce);
		UserDao ud=new ImplD();
		MAP.put("ud", ud);
		RegService rs=new RegServiceImpl();
		MAP.put("rs", rs);
		FindPwdService fps=new FindPwdServiceImpl();
		MAP.put("fps", fps);
		UpdatePwdService ups=new UpdatePwdServiceImpl();
		MAP.put("ups", ups);
	}
}
