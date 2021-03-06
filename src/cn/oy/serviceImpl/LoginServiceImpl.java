package cn.oy.serviceImpl;

import cn.oy.dao.PictureDao;
import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.daoImpl.PictureDaoImpl;
import cn.oy.pojo.User;
import cn.oy.service.FriendService;
import cn.oy.service.GroupChatService;
import cn.oy.service.LoginService;

public class LoginServiceImpl implements LoginService {
	
//	UserDao ud=(UserDao) ioc.MapIoc.MAP.get("ud");
	
	UserDao ud=null;	//		在外面new下面登录校验的时候会出问题
	User user=null;
	PictureDao pd=null;
	FriendService fs=null;
	GroupChatService gcs=null;
	@Override
	public User checkLogin(String account, String pwd) {
		ud=new ImplD();
		if(null!=account&&null!=pwd) {
			fs=new FriendServiceImpl();
			gcs=new GroupChatServiceImpl();
			pd=new PictureDaoImpl();
		user=ud.LoginDao(account,pwd);
		user.setPic(pd.getPicPathDao(user.getId()));
		user.setGroups(fs.groupsService(user.getId()));		//得到用户好友列表菜单
		user.setGroupChats(gcs.getGroupChat(user.getId()));	//得到用户加入的群
		return user;
		}
		return null;

	}
	
	@Override
	public boolean checkCode(String vcode, String ocode) {
		vcode=vcode.toLowerCase();		//转小写，达到不区分大小写的作用
		if(ocode.equals(vcode)) 
		return true;
		return false;
	}

}
