package ioc;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.service.LoginService;
import cn.oy.service.FindPwdService;
import cn.oy.service.FriendService;
import cn.oy.service.PageService;
import cn.oy.service.RegService;
import cn.oy.service.UpdateInfoService;
import cn.oy.service.UpdatePwdService;
import cn.oy.serviceImpl.LoginServiceImpl;
import cn.oy.serviceImpl.FindPwdServiceImpl;
import cn.oy.serviceImpl.FriendServiceImpl;
import cn.oy.serviceImpl.PageServiceImpl;
import cn.oy.serviceImpl.RegServiceImpl;
import cn.oy.serviceImpl.UpdateInfoServiceImpl;
import cn.oy.serviceImpl.UpdatePwdServiceImpl;

public class MapIoc {

	public final static Map<String,Object> MAP=new HashMap<String, Object>();
	
	static {
		LoginService ls=new LoginServiceImpl();
		MAP.put("ls", ls);
		UserDao ud=new ImplD();
		MAP.put("ud", ud);
		RegService rs=new RegServiceImpl();
		MAP.put("rs", rs);
		FindPwdService fps=new FindPwdServiceImpl();
		MAP.put("fps", fps);
		UpdatePwdService ups=new UpdatePwdServiceImpl();
		MAP.put("ups", ups);
		Gson gson=new Gson();
		MAP.put("gson", gson);
		FriendService fs=new FriendServiceImpl();
		MAP.put("fs", fs);
		PageService ps=new PageServiceImpl();
		MAP.put("ps", ps);
		UpdateInfoService uis=new UpdateInfoServiceImpl();
		MAP.put("uis", uis);
	}
}
