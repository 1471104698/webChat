package cn.oy.serviceImpl;



import cn.oy.dao.PictureDao;
import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.daoImpl.PictureDaoImpl;
import cn.oy.pojo.User;
import cn.oy.service.FriendService;
import cn.oy.service.RegService;


public class RegServiceImpl implements RegService {
	
	int result=-1;
	User user=null;
	UserDao ud=null;
	PictureDao pd=null;
	
	//注册
	@Override
	public int regService(User user) {
		String picPath="";
		ud=new ImplD();
		pd=new PictureDaoImpl();
		FriendService fs=(FriendService) util.MapIoc.MAP.get("fs");
		if(null==ud.isEmptyByAccount(user.getAccount())) {
			if(user.getSex()=="男") {
				picPath="upload/male.png";
			}else {
				picPath="upload/female.png";
			}
			result= ud.regDao(user);		//注册
			
			if(result>0) {
			 user=ud.isEmptyByAccount(user.getAccount());	//重新拿到id
			 pd.setPicPathDao(user.getId(), picPath);		//设置用户头像
			 
			fs.createGroupName("我的好友", user.getId(),null);			//创建一个默认好友分组列表
			}
			 return result;
		}
		return -1;
	}




}
