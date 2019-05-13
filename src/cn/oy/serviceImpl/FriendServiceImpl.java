package cn.oy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import cn.oy.dao.PictureDao;
import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.daoImpl.PictureDaoImpl;
import cn.oy.pojo.Group;
import cn.oy.pojo.User;
import cn.oy.service.FriendService;

public class FriendServiceImpl implements FriendService {
	
	int result=-1;
	UserDao ud=null;
	User user=null;
	List<User> users=null;
	PictureDao pd=new PictureDaoImpl();
	//通过号账号判断查找用户	
	@Override
	public User isExistByAccount(String account) {
		ud=new ImplD();
		if(null!=account) {
			System.out.println("account="+account);
			user= ud.isEmptyByAccount(account);
			System.out.println("user111="+user);
			if(null!=user) {
				String picPath=pd.getPicPathDao(user.getId());
				user.setPic(picPath);
				System.out.println("user="+user);
				return user;
			}
		}
		return null;
	}
	//通过用户名称查找用户
	@Override
	public List<User> isExistByName(String name) {
		ud=new ImplD();
		if(null!=name) {
			List<User> users1=users=ud.isExistByNameDao(name);
			List<User> users2=new ArrayList<User>();
			for(User u:users1) {			
				String picPath=pd.getPicPathDao(u.getId());
				u.setPic(picPath);
				users2.add(u);
			}
			
			return users2;
		}
		return null;
	}
	//增加好友
	@Override
	public int AddFriendService(Integer fid, Integer uid ,String group) {
		ud=new ImplD();
		if(fid==uid)		//添加的是自己
			return 0;
		if(ud.CheckFriendDao(fid, uid)>0) {	//已经是好友
			System.out.println("uid="+uid);
			System.out.println("fid="+fid);
			return -2;
			}
		if(null==ud.isEmptyById(fid)) 		//添加的用户为空
			return -1;
				
		result=ud.AddFriendDao(fid, uid, group);
		if(result>0) {
		return ud.AddFriendDao(uid, fid, group);
		}
		return -1;
		
	}
	
	//删除好友
	@Override
	public int DelFriendService(Integer fid, Integer uid) {
		ud=new ImplD();
		if(fid==uid)		//删除的是自己
			return 0;
		if(null==ud.isEmptyById(fid)) 		//删除的用户为空
			return -1;
		if(ud.CheckFriendDao(fid, uid)<0)	//不是自己好友	
			return -2;
		return ud.DelFriendDao(fid, uid);
	}

	//查看好友
	@Override
	public User SeeFriendService(Integer fid,Integer uid) {
		ud=new ImplD();
		if(null!=ud.isEmptyById(fid)) 		//查看的用户为空
			return ud.SeeFriendDao(fid,uid);
		
		return null;
	}
	
	//得到某个分组下的好友
	@Override
	public List<User> friendsService(Integer uid, String group) {
		ud=new ImplD();
		if(null!=group)
		return ud.friendsDao(uid, group);
		return null;
	}

	//得到用户的所有分组
	@Override
	public List<Group> groupsService(Integer uid) {
		ud=new ImplD();
		if(null!=ud.isEmptyById(uid)) {
		return ud.groupsDao(uid);
		}
		return null;
	}

	//修改昵称
	@Override
	public int moNickName(Integer fid, Integer uid, String nickName) {
		ud=new ImplD();
		if(null!=ud.isEmptyById(fid)) {
			return ud.moNickName(fid, uid, nickName);
		}
			
		return 0;
	}

	//修改好友分组名称
	@Override
	public int moGroupName(String newName, Integer uid, String oldName,List<Group> groups) {
		ud=new ImplD();
		if(null!=ud.isEmptyById(uid)) {
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
		ud=new ImplD();
		if(null!=ud.isEmptyById(uid)) {
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
		ud=new ImplD();
		if(ud.CheckFriendDao(fid, uid)<0)
			return -1;
		if(null!=ud.isEmptyById(fid)&&null!=ud.isEmptyById(uid))
			return ud.moveFriendDao(fid, uid, group);
		return -1;
	}

	//删除分组
	@Override
	public int deleteGroupName(String groupName, Integer uid) {
		ud=new ImplD();
		if(null!=groupName&&null!=ud.isEmptyById(uid)) {
			return ud.deleteGroupNameDao(groupName, uid);
		}
		return -1;
	}




}
