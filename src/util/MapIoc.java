package util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import cn.oy.service.LoginService;
import cn.oy.service.FindPwdService;
import cn.oy.service.FriendService;
import cn.oy.service.GroupChatService;
import cn.oy.service.PageService;
import cn.oy.service.PictureService;
import cn.oy.service.RegService;
import cn.oy.service.UpdateInfoService;
import cn.oy.service.UpdatePwdService;
import cn.oy.serviceImpl.LoginServiceImpl;
import cn.oy.serviceImpl.FindPwdServiceImpl;
import cn.oy.serviceImpl.FriendServiceImpl;
import cn.oy.serviceImpl.GroupChatServiceImpl;
import cn.oy.serviceImpl.PageServiceImpl;
import cn.oy.serviceImpl.PictureServiceImpl;
import cn.oy.serviceImpl.RegServiceImpl;
import cn.oy.serviceImpl.UpdateInfoServiceImpl;
import cn.oy.serviceImpl.UpdatePwdServiceImpl;

public class MapIoc {

	public final static Map<String,Object> MAP=new HashMap<String, Object>();
	
	static {

		LoginService ls=new LoginServiceImpl();
		MAP.put("ls", ls);
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
		GetWay aw=new GetWay();
		MAP.put("aw", aw);
		GroupChatService gcs=new GroupChatServiceImpl();
		MAP.put("gcs", gcs);
		PictureService pics=new PictureServiceImpl();
		MAP.put("pics", pics);
	}
}
