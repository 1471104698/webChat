package cn.oy.service;

import java.util.List;

import cn.oy.pojo.Group;
import cn.oy.pojo.User;

public interface FriendService {

	//增加好友
	int AddFriendService(Integer fid,Integer uid,String group);
	//增加好友
	int DelFriendService(Integer fid,Integer uid);
	//查看好友
	User SeeFriendService(Integer fid);
	//得到某个分组下的好友
	List<User> friendsService(Integer uid,String group);
	//得到用户的所有分组
	List<Group> groupsService(Integer uid);
	
}
