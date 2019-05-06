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
		if(null==ud.isEmpty(fid)) 		//添加的用户为空
			return -1;
				
		return ud.AddFriendDao(fid, uid, group);
		
	}
	
	//删除好友
	@Override
	public int DelFriendService(Integer fid, Integer uid) {
		if(fid==uid)		//删除的是自己
			return 0;
		if(null==ud.isEmpty(fid)) 		//删除的用户为空
			return -1;
		if(ud.CheckFriendDao(fid, uid)<0)	//不是自己好友	
			return -2;
		return ud.DelFriendDao(fid, uid);
	}

	//查看好友
	@Override
	public User SeeFriendService(Integer fid,Integer uid) {
		if(null!=ud.isEmpty(fid)) 		//查看的用户为空
			return ud.SeeFriendDao(fid,uid);
		
		return null;
	}
	
	//得到某个分组下的好友
	@Override
	public List<User> friendsService(Integer uid, String group) {
		if(null!=group)
		return ud.friendsDao(uid, group);
		return null;
	}

	//得到用户的所有分组
	@Override
	public List<Group> groupsService(Integer uid) {
		if(null!=ud.isEmpty(uid)) {
		return ud.groupsDao(uid);
		}
		return null;
	}

	//修改昵称
	@Override
	public int moNickName(Integer fid, Integer uid, String nickName) {
		if(null!=ud.isEmpty(fid)) {
			return ud.moNickName(fid, uid, nickName);
		}
			
		return 0;
	}

	//修改好友分组名称
	@Override
	public int moGroupName(String newName, Integer uid, String oldName,List<Group> groups) {
		if(null!=ud.isEmpty(uid)) {
			if(null!=groups) {
			for(Group group:groups) {
				if(group.getName().equals(newName))
					return -2;							
				}		
			}
			return ud.moGroupNameDao(newName, uid, oldName);
		}
		else
		return -1;
	}

	//创建好友分组
	@Override
	public int createGroupName(String newName, Integer uid,List<Group> groups) {
		if(null!=ud.isEmpty(uid)) {
		if(null!=groups) {
		for(Group group:groups) {
			if(group.getName().equals(newName))
				return -2;
			}		
		}
			return ud.createGroupNameDao(newName, uid);
		}
		return -1;
	}

	@Override
	//修改好友所在分组
	public int moveFriend(Integer fid,Integer uid,String group) {
		if(ud.CheckFriendDao(fid, uid)<0)
			return -1;
		if(null!=ud.isEmpty(fid)&&null!=ud.isEmpty(uid))
			return ud.moveFriendDao(fid, uid, group);
		return -1;
	}

	//删除分组
	@Override
	public int deleteGroupName(String groupName, Integer uid) {
		if(null!=groupName&&null!=ud.isEmpty(uid)) {
			return ud.deleteGroupNameDao(groupName, uid);
		}
		return -1;
	}


}
