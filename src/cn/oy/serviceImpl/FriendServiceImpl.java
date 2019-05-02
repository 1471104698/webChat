package cn.oy.serviceImpl;

import java.util.List;

import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.pojo.Group;
import cn.oy.pojo.User;
import cn.oy.service.FriendService;

public class FriendServiceImpl implements FriendService {

	//增加好友
	UserDao ud=new ImplD();
	@Override
	public int AddFriendService(Integer fid, Integer uid ,String group) {
		if(fid==uid)		//添加的是自己
			return 0;
		if(ud.CheckFriendDao(fid, uid)>0)	//已经是好友	
			return -2;
		if(ud.isEmpty(fid)==null) 		//添加的用户为空
			return -1;
				
		return ud.AddFriendDao(fid, uid, group);
		
	}
	
	//删除好友
	@Override
	public int DelFriendService(Integer fid, Integer uid) {
		if(fid==uid)		//删除的是自己
			return 0;
		if(ud.isEmpty(fid)==null) 		//删除的用户为空
			return -1;
		if(ud.CheckFriendDao(fid, uid)<0)	//不是自己好友	
			return -2;
		return ud.DelFriendDao(fid, uid);
	}

	//查看好友
	@Override
	public User SeeFriendService(Integer fid) {
		if(ud.isEmpty(fid)!=null) 		//查看的用户为空
			return ud.SeeFriendDao(fid);
		
		return null;
	}
	
	//得到某个分组下的好友
	@Override
	public List<User> friendsService(Integer uid, String group) {
		if(group!=null)
		return ud.friendsDao(uid, group);
		return null;
	}

	//得到用户的所有分组
	@Override
	public List<Group> groupsService(Integer uid) {
		return ud.groupsDao(uid);
	}


}
