package cn.oy.dao;

import java.util.List;
import cn.oy.pojo.Group;
import cn.oy.pojo.User;

public interface UserDao {
	
	//检查用户是否为空
	User isEmpty(String account);
	//检查用户是否为空
	User isEmpty(Integer id);
	//得到用户信息
	User LoginDao(String account, String pwd);
	//注册用户
	int regDao(User user);
	//找回密码
	int findPwdDao(String account, String name, String tel);	
	//修改密码
	int updatePwdDao(String newPwd,String account);
	//添加好友
	int AddFriendDao(Integer fid, Integer uid,String group);
	//删除好友
	int DelFriendDao(Integer fid, Integer uid);
	//查看好友信息
	User SeeFriendDao(Integer fid,Integer uid);
	//查询是否已经是好友
	int CheckFriendDao(Integer fid, Integer uid);
	//修改个人信息
	int updateInfoDao(User user);
	//得到某个分组下的好友
	List<User> friendsDao(Integer uid,String group);
	//得到用户的所有分组
	List<Group> groupsDao(Integer uid);
	//修改昵称
	int moNickName(Integer fid, Integer uid, String nickName);
	//修改好友分组昵称
	int moGroupNameDao(String newName,Integer uid,String oldName);
	//创建好友分组
	int createGroupNameDao(String newName,Integer uid);
	//修改好友所在分组
	int moveFriendDao(Integer fid,Integer uid,String group);
	//删除分组
	int deleteGroupNameDao(String groupName,Integer uid);
	
//	// 查询用户总数
//	int getTotalDao(String uid);
//	//查询某一页
//	List<User> queryUserDao(int currentPage,int pageSize,String dire,String uname,String uid,String identity,String idd);
}
