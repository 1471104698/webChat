package cn.oy.serviceImpl;

import java.util.List;

import cn.oy.pojo.User;
import cn.oy.service.PageService;

public class PageServiceImpl implements PageService {

	//得到用户总数
	@Override
	public int getTotaService(String uid) {
		
		return 0;
	}

	//得到分页用户数据
	@Override
	public List<User> queryUserService(int currentPage, int pageSize, String uid) {
		
		return null;
	}

}
