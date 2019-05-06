package cn.oy.serviceImpl;

import java.util.List;

import cn.oy.dao.ChatMsgDao;
import cn.oy.dao.GroupChatMsgDao;
import cn.oy.daoImpl.ChatMsgDaoImpl;
import cn.oy.daoImpl.GroupChatMsgDaoImpl;
import cn.oy.pojo.Page;
import cn.oy.service.PageService;

public class PageServiceImpl implements PageService {
	/**
	 * 查询某一页聊天记录
	 */
	@Override
	public Page queryDateService(String current, Integer uid, Integer xid,Integer way) {
		ChatMsgDao cd=new ChatMsgDaoImpl();
		GroupChatMsgDao gcmd=new GroupChatMsgDaoImpl();
		Page page=null;
		int currentPage=-1;
		int totalCount=-1;
		List<String> data=null;
		if(null==current||""==current) {
			currentPage=1;
		}else {
			currentPage=Integer.parseInt(current);
		}
		if(way==1) {
			totalCount=cd.totalSize(uid, xid);
			data=cd.pageData(currentPage, 40, uid, xid);
		}else {
			totalCount=gcmd.totalSize(xid);
			data=gcmd.pageData(currentPage, 40, xid);
		}
		if(null!=data)
		page=new Page(currentPage, 20, totalCount, data);
		
		return page;
	}


}
