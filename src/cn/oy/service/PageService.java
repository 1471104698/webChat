package cn.oy.service;

import cn.oy.pojo.Page;

public interface PageService {
	
	/**
	 * 查询某一页聊天记录
	 */
	Page queryDateService(String current,Integer uid,Integer xid,Integer way);	
}
