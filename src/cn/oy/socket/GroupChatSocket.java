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
import cn.oy.pojo.GroupChat;
import cn.oy.pojo.User;
import cn.oy.service.GroupChatService;
import cn.oy.vo.ContentVo;
import cn.oy.vo.Message;
import cn.oy.way.AllWay;

@ServerEndpoint("/gchat")
public class GroupChatSocket {
	GroupChatService gcs=(GroupChatService) util.MapIoc.MAP.get("gcs");
	AllWay aw=(AllWay) util.MapIoc.MAP.get("aw");
	ChatMsgDao cd=(ChatMsgDao) util.MapIoc.MAP.get("cd");
	User user=null;
	private String username;
	private String account;
	private Integer uid;
	private Integer groupId;
	private String picPath;
	private static Integer webCount=0;
	
	private static Map<Integer,String> iaMap=new HashMap<>();			//通过用户id找账户
	private static Map<Integer,String> ipMap=new HashMap<>();			//通过用户id找头像
	
	private static Map<Integer,List<String>> picPathMap=new HashMap<>();		//存储头像的Map集合
	private static Map<Integer,List<Session>> sessionMap=new HashMap<>();	//存储session的Map集合
	private static Map<Integer,List<Integer>> idMap=new HashMap<>();	//存储id的Map集合
	private static Map<Integer,List<String>> nameMap=new HashMap<>();	//存储name的Map集合
	
	private static List<String>	picPaths=null;			//将用户名存储起来，用于广播
	private static List<Session> sessions=null;		//将session对象存储起来,一个群一个new，用于广播
	private static List<String>	names=null;			//将用户名存储起来，用于广播
	private static List<Integer> ids=null;			//将用户id存储起来，用于广播
	private static List<String>	accounts=new ArrayList<String>();			//将用户账户存储起来，用于广播
	Gson gson=(Gson) util.MapIoc.MAP.get("gson");
	
	@OnOpen
	public void open(Session session) {
		//当前webSocket的session对象，不是servlet的session,不能从中取到用户信息
		String queryString = session.getQueryString();		//得到的是account=xx&groupId=xx
		
		account=queryString.split("=")[1].split("&")[0];
		groupId=Integer.parseInt(queryString.split("=")[2]);
		this.user=aw.getUserByAccount(account);
		uid=user.getId();
		user=aw.getUserByID(uid);
		username=user.getName();
		picPath=user.getPic();
		
		System.out.println("picPathpicPathpicPath="+picPath);

		if(null==sessionMap.get(groupId)) {				//如果Map中不存在此群号的对应的用户session集合，则进行创建,顺便创建相应的用户id和名称集合,再存入Map
			sessions=new ArrayList<Session>();
			names=new ArrayList<String>();
			ids=new ArrayList<Integer>();	
			picPaths=new ArrayList<String>();
		}
		else {
			sessions=sessionMap.get(groupId);			//不为空则得到用户session集合
		}
		
		
		accounts.add(account);
		names.add(username);
		ids.add(uid);
		picPaths.add(picPath);
		sessions.add(session);
		
		picPathMap.put(groupId, picPaths);
		sessionMap.put(groupId, sessions);			//	将群id和对应用户的session集合进行绑定
		idMap.put(groupId, ids);					//	将群id和对应用户的id集合进行绑定
		nameMap.put(groupId, names);				//	将群id和对应用户的用户名name集合进行绑定	

				
		addWebCount();
		
		iaMap.put(uid, account);					//将每一个用户的id和账号进行绑定，便于后续广播通知显示账号
		ipMap.put(uid, picPath);
		
		String msg="欢迎"+username+"进入聊天室!!!<br/>";
		
		Message message=new Message();
		message.setWelcome(msg);
		message.setUsernames(names);
		message.setIds(ids);
		message.setAccounts(accounts);
		message.setPicPaths(picPaths);
		GroupChat groupChat=gcs.isExistById(groupId);//得到群 的相关信息
		message.setNotice(groupChat.getNotice());
		
		broadcast(sessions,message.toJson()); 
		}
	
	public void broadcast(List<Session> ss,String msg) {		//广播的实现
		if(ss!=null)
		for(Session session :ss) {		//遍历用户，给每个用户都发送一次信息
			try {
				session.getBasicRemote().sendText(msg);			//对遍历的每个用户发送此对象信息
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@OnClose
	public void close(Session session) {
		subWebCount();
		sessions = sessionMap.get(groupId);
		names = nameMap.get(groupId);
		ids = idMap.get(groupId);
		picPaths=picPathMap.get(groupId);
		
		picPaths.remove(picPath);
		sessions.remove(session);
		names.remove(username);
		ids.remove(uid);
		
		String msg="欢送"+username+"离开聊天室!!!<br/>";
		
		Message message=new Message();
		message.setWelcome(msg);
		message.setIds(ids);
		message.setAccounts(accounts);
		message.setUsernames(names);
		message.setPicPaths(picPaths);
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
		String useraccount=iaMap.get(uid);			//通过id找到账户
		String userpic=ipMap.get(uid);					
		
		Message message=new Message();	
		//存储到数据库的信息和时间	
		String date=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss").format(new Date())+"\r\n";
		
		msg=aw.replace(vo.getMsg());
		message.setContent(username, msg,userpic);	
		
		int type=vo.getType();		//消息类型
		if(type==1) {		//广播，相当于群发
		
			sessions = sessionMap.get(groupId);
			broadcast(sessions, message.toJson());
			msg=username+"("+useraccount+")"+"："+date+msg+"\r\n";

			//存储到数据库
			 aw.recordGroup(groupId, msg);
			
		}else {
			
			
		}	
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



















