package cn.oy.serviceImpl;

import java.util.List;

import cn.oy.dao.ChatMsgDao;
import cn.oy.daoImpl.ChatMsgDaoImpl;
import cn.oy.pojo.Page;
import cn.oy.service.PageService;

public class PageServiceImpl implements PageService {

	ChatMsgDao cd=new ChatMsgDaoImpl();
	/**
	 * 查询某一页聊天记录
	 */
	@Override
	public Page queryDateService(String current, Integer uid, Integer fid) {
		Page page=null;
		int currentPage=-1;
		if(null==current||""==current) {
			currentPage=1;
		}else {
			currentPage=Integer.parseInt(current);
		}
		System.out.println("currentPage="+currentPage);
		int totalCount=cd.totalSize(uid, fid);
		System.out.println("totalCount="+totalCount);
		List<String> data=cd.pageData(currentPage, 40, uid, fid);
		System.out.println("data="+data);
		if(null!=data)
		page=new Page(currentPage, 20, totalCount, data);
		
		return page;
	}


}
