package cn.oy.service;

import cn.oy.pojo.Page;

public interface PageService {
	
	
	Page queryDateService(String current,Integer uid,Integer xid,Integer way);	//查询某一页聊天记录
	
	int totalSize(Integer groupId,Integer way);//得到群或用户的总数
	
	Page getUser(String current, Integer pageSize,Integer groupId,Integer way);	//得到该群下的所有用户及信息或得到所有用户,分页查询			
	
	Page getGroupChats(String current, Integer pageSize,Integer way);		//得到所有群并分页显示
}
