package cn.oy.serviceImpl;

import java.util.List;

import cn.oy.dao.ChatMsgDao;
import cn.oy.dao.GroupChatDao;
import cn.oy.dao.GroupChatMsgDao;
import cn.oy.daoImpl.ChatMsgDaoImpl;
import cn.oy.daoImpl.GroupChatDaoImpl;
import cn.oy.daoImpl.GroupChatMsgDaoImpl;
import cn.oy.pojo.GroupChat;
import cn.oy.pojo.Page;
import cn.oy.pojo.User;
import cn.oy.service.PageService;

public class PageServiceImpl implements PageService {
	Page page=null;
	GroupChatDao gcd=null;
	User user =null;
	List<GroupChat> groups=null;
	List<User> users=null;
	int currentPage=-1;
	int totalCount=-1;
	ChatMsgDao cd=null;
	int pageSize=-1;
	GroupChatMsgDao gcmd=null;
	
	
	//查询某一页聊天记录
	@Override
	public Page queryDateService(String current, Integer uid, Integer xid,Integer way) {
		cd=new ChatMsgDaoImpl();
		gcmd=new GroupChatMsgDaoImpl();
		List<String> data=null;
		pageSize=40;
		if(null==current||""==current) {
			currentPage=1;
		}else {
			currentPage=Integer.parseInt(current);
		}
		if(way==1) {
			totalCount=cd.totalSize(uid, xid);
			data=cd.pageData(currentPage, pageSize, uid, xid);
		}else{
			totalCount=gcmd.totalSize(xid);
			data=gcmd.pageData(currentPage, pageSize, xid);
		}
		if(null!=data)
		page=new Page(currentPage, pageSize, totalCount, data);
		
		return page;
	}
	
	//得到用户群或用户总数
		@Override
		public int totalSize(Integer groupId, Integer way) {
		
			if(null!=way) {
				
				gcd=new GroupChatDaoImpl();
				return gcd.totalSizeDao(groupId, way);
			}
			return -1;
		}
		
		//得到该群下的所有用户及信息或得到所有用户,分页查询	
		@Override
		public Page getUser(String current, Integer pageSize,Integer groupId,Integer way) {	
			gcd=new GroupChatDaoImpl();
				if(null==current||""==current) {
					currentPage=0;
				}else {
					currentPage=Integer.parseInt(current);
				}
				totalCount=gcd.totalSizeDao(groupId, way);
				users = gcd.getUserDao(currentPage,pageSize,groupId);
				page=new Page(currentPage, pageSize, totalCount, null, users);
				return page;

		}
		
		//得到所有群并分页显示
		@Override
		public Page getGroupChats(String current, Integer pageSize,Integer way) {
			System.out.println("current="+current);
			System.out.println("pageSize="+pageSize);
			System.out.println("此处的way="+way);
			gcd=new GroupChatDaoImpl();
				if(null==current||""==current) {
					currentPage=0;
				}else {
					currentPage=Integer.parseInt(current);
				}
				totalCount=gcd.totalSizeDao(null, way);
				System.out.println("totalCount"+totalCount);
				groups=gcd.getGroupChatsDao(currentPage, pageSize);
				System.out.println("groups="+groups);
				System.out.println("currentPage="+currentPage);
				page=new Page(currentPage, pageSize, totalCount, groups, null);
				System.out.println("totalPage="+page.getTotalPage());
			
			return page;
		}


}
