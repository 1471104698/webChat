package cn.oy.socket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;

import cn.oy.dao.ChatMsgDao;
import cn.oy.pojo.User;
import cn.oy.vo.ContentVo;
import cn.oy.vo.Message;
import cn.oy.way.AllWay;

@ServerEndpoint("/gchat")
public class GroupChatSocket {

	AllWay aw=(AllWay) util.MapIoc.MAP.get("aw");
	ChatMsgDao cd=(ChatMsgDao) util.MapIoc.MAP.get("cd");
	User user=null;
	private String username;
	private String account;
	private Integer id;
	private Integer groupId;
	private static Integer webCount=0;
	private static List<Session> sessions=new ArrayList<>();		//将session对象存储起来
	private static Map<String,Session> msu=new HashMap<>();			//通过用户名找session
	private static Map<Integer,String> miu=new HashMap<>();			//通过用户id找用户名
	private static Map<Integer,Session> mis=new HashMap<>();			//通过用户id找session
	private static Map<String,Integer> mui=new HashMap<>();			//通过用户名找id
	private static Map<Integer,String> mia=new HashMap<>();			//通过用户id找账户
	private static List<String>	names=new ArrayList<>();			//将用户名存储起来
	private static List<Integer> ids=new ArrayList<>();			//将用户名存储起来
	private static List<String>	accounts=new ArrayList<>();			//将用户账户存储起来
	Gson gson=(Gson) util.MapIoc.MAP.get("gson");
	
	
	@OnOpen
	public void open(Session session) {
		//当前webSocket的session对象，不是servlet的session,不能从中取到用户信息
		String queryString = session.getQueryString();		//得到的是username=oy
		System.out.println("queryString="+queryString);
		account=queryString.split("=")[1].split("&")[0];
		groupId=Integer.parseInt(queryString.split("=")[2]);
		System.out.println("account111="+account);
		System.out.println("groupId="+groupId);
		this.user=aw.getUserByAccount(account);
		id=user.getId();
		username=user.getName();
		
		addWebCount();
		accounts.add(account);
		names.add(username);
		ids.add(id);
		sessions.add(session);
		msu.put(username, session);	//将用户名和session对象关联起来
		miu.put(id, username);	//将用户名和id对象关联起来
		mis.put(id, session);	
		mui.put(username,id);
		mia.put(id, account);
		String msg="欢迎"+username+"进入聊天室!!!<br/>";
		
		Message message=new Message();
		message.setWelcome(msg);
		message.setUsernames(names);
		message.setIds(ids);
		message.setAccounts(accounts);
		
		broadcast(sessions,message.toJson()); 
		}

	
	public void broadcast(List<Session> ss,String msg) {		//广播的实现
		for(Session session :ss) {		//遍历用户，给每个用户都发送一次信息
			try {
				session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@OnClose
	public void close(Session session) {
		subWebCount();
		sessions.remove(session);
		names.remove(username);
		ids.remove(id);
		String msg="欢送"+username+"离开聊天室!!!<br/>";
		Message message=new Message();
		message.setWelcome(msg);
		message.setUsernames(names);
		broadcast(sessions, message.toJson());		//播放给所有已存储的session对象
	}
	
	@OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error");
        error.printStackTrace();
    }

	//Gson是谷歌推出的一个用于生成和解析JSON数据格式的工具；
	@OnMessage
	public void message(Session session,String json) {
		String msg="";
		ContentVo vo=gson.fromJson(json, ContentVo.class);
		String useraccount=mia.get(id);			//通过id找到账户
		Message message=new Message();	
		//存储到数据库的信息和时间	
		String date=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss").format(new Date())+"\r\n";
		int type=vo.getType();		//消息类型
		if(type==1) {		//广播，相当于群发
			message.setContent(username, vo.getMsg());			
			broadcast(sessions, message.toJson());
			msg=username+"("+useraccount+")"+"："+date+vo.getMsg()+"\r\n";

			//存储到数据库
			 aw.recordGroup(groupId, msg);
			
		}
//		else {						//群员私聊
//			Integer to = vo.getTo();	//得到选中的用户id
//			Session to_session = mis.get(to);
//			System.out.println("to_session="+to_session);
//			message.setContent(username, "<font color=red>私人信息："+vo.getMsg()+"</font>");		//
//			try {
//				to_session.getBasicRemote().sendText(message.toJson());		//给单个人发
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			msg=username+"("+useraccount+")"+"："+date+"<font color=red>私人信息："+vo.getMsg()+"</font>"+"\r\n";	
//			//存储到数据库
//			 aw.recordGroup(id,to, msg);		//以私聊信息形式存到聊天记录		
//		}	
	}
	
	
	public static int getWebCount() {
		return webCount;
	}
	public static void addWebCount() {
		webCount++;
	}
	public static void subWebCount() {
		webCount--;
	}
}



















