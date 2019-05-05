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
	User SeeFriendService(Integer fid,Integer uid);
	//得到某个分组下的好友
	List<User> friendsService(Integer uid,String group);
	//得到用户的所有分组
	List<Group> groupsService(Integer uid);
	//修改昵称
	int moNickName(Integer fid,Integer uid,String nickName);
	//修改好友分组昵称
	int moGroupName(String newName,Integer uid,String oldName,List<Group> groups);
	//创建好友分组
	int createGroupName(String newName,Integer uid,List<Group> groups);
	//修改好友所在分组
	int moveFriend(Integer fid,Integer uid,String group);
	
}
