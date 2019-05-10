package cn.oy.serviceImpl;



import cn.oy.dao.PictureDao;
import cn.oy.dao.UserDao;
import cn.oy.daoImpl.ImplD;
import cn.oy.daoImpl.PictureDaoImpl;
import cn.oy.pojo.User;
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
		if(null==ud.isEmpty(user.getAccount())) {
			if(user.getSex()=="男") {
				picPath="upload/male.png";
			}else {
				picPath="upload/female.png";
			}
			result= ud.regDao(user);
			 user=ud.isEmpty(user.getAccount());	//重新拿到id
			 pd.setPicPathDao(user.getId(), picPath);		//设置用户头像
			 return result;
		}
		return -1;
	}




}
