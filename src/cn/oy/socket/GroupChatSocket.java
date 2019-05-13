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
import cn.oy.vo.GroupContentVo;
import cn.oy.vo.Message;
import util.GetWay;

@ServerEndpoint("/gchat")
public class GroupChatSocket {
	GroupChatService gcs=(GroupChatService) util.MapIoc.MAP.get("gcs");
	GetWay aw=(GetWay) util.MapIoc.MAP.get("aw");
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
	private static Map<Integer,Session> isMap=new HashMap<>();			//通过用户id找session
	private static Map<Integer,String> inMap=new HashMap<>();			//通过用户id找名字
	
	private static Map<Integer,List<String>> picPathMap=new HashMap<>();		//存储头像的Map集合
	private static Map<Integer,List<Session>> sessionMap=new HashMap<>();	//存储session的Map集合
	private static Map<Integer,List<Integer>> idMap=new HashMap<>();	//存储id的Map集合
	private static Map<Integer,List<String>> nameMap=new HashMap<>();	//存储name的Map集合
	private static Map<Integer,List<Integer>> bannedMap=new HashMap<>();	//存储禁言用户id的Map集合
	
	private static List<String>	picPaths=null;			//将用户名存储起来，用于广播
	private static List<Session> sessions=null;		//将session对象存储起来,一个群一个new，用于广播
	private static List<String>	names=null;			//将用户名存储起来，用于广播
	private static List<Integer> ids=null;			//将用户id存储起来，用于广播
	private static List<String>	accounts=new ArrayList<String>();			//将用户账户存储起来，用于广播，所有群用户放在一起
	private static List<Integer> banneds=null;		//存储被禁言的用户id，一个群一个
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
			banneds=new ArrayList<Integer>();	
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
		bannedMap.put(groupId, banneds);
				
		addWebCount();
		
		inMap.put(uid, username);
		iaMap.put(uid, account);					//将每一个用户的id和账号进行绑定，便于后续广播通知显示账号
		ipMap.put(uid, picPath);
		isMap.put(uid, session);
		
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
		String date=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss").format(new Date())+"\r\n";
		String msg="";
		GroupContentVo vo=gson.fromJson(json, GroupContentVo.class);
		Message message=new Message();	
		banneds=bannedMap.get(groupId);
		String useraccount=null;			//通过id找到账户
		String userpic=ipMap.get(uid);	
		
		
			
			Integer to=vo.getTo();		//私法到某个用户
			Integer bid=vo.getBanned();//被禁用户id
			Integer lid=vo.getLift();	//解禁用户
			String name=null;
		if(null!=to||null!=bid||null!=lid) {
			if(null!=to) {				//私发
			msg=vo.getMsg();
			Session to_session=isMap.get(to);
			try {
				to_session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}	
			return;
		}
			if(null!=bid){			//禁言
			for(Integer id:banneds) {		//如果存在该用户已被禁言则直接返回
				if(bid==id)
					return;
			}
			useraccount=iaMap.get(bid);
			userpic=ipMap.get(bid);			//将头像改为被禁言用户头像
			name=inMap.get(bid);
			
			User u=aw.getUserByID(bid);
			System.out.println("user="+u);
			if("1".equals(u.getIden())) {
				msg="<font color=red>"+"该用户是管理员，不能进行禁言操作	</font>";
				message.setContent(name, msg,userpic);	
				try {
					session.getBasicRemote().sendText(message.toJson());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return;
			}else {
				
			System.out.println("禁言1");
			banneds.add(bid);			//将禁言用户添加进列表
			bannedMap.put(groupId, banneds);
			
			msg="<font color=red>"+"被群主禁言	</font>";}
			System.out.println("禁言2");
			message.setContent(name, msg,userpic);	
			System.out.println("禁言3");
			}
			
			if(null!=lid) {			//解禁
				System.out.println("解禁1");
				boolean flag=false;
				for(Integer id:banneds) {
					if(lid==id) {			//如果存在该禁言用户的话
						flag=true;
						banneds.remove(lid);		//将禁言用户移除出列表
						bannedMap.put(groupId, banneds);
						System.out.println("解禁2");
						break;		//跳出循环
					}
				}
				System.out.println("解禁3");
				if(!flag) {		//该用户没有被禁言则直接返回
					return;
				}
				System.out.println("解禁4");
				
			useraccount=iaMap.get(lid);
			userpic=ipMap.get(lid);			//将头像改为被禁言用户头像
			name=inMap.get(lid);
			msg="<font color=red>"+"被群主解除禁言	</font>";
			}
			message.setContent(name, msg,userpic);	
			broadcast(sessions, message.toJson());
			//上面操作完不进行以下广播操作
			return;
		}
		
			
			
			
			boolean flag=false;
			for(Integer id:banneds) {		//查询发言用户是否已经被禁言
				if(id==uid) {
					flag=true;
					break;
				}
			}
		if(flag) {			//被禁言无法发言
			msg="<font color=red>"+"你已被禁言，暂时无法进行发言</font>";
			message.setContent(username, msg, userpic);
			try {
				session.getBasicRemote().sendText(message.toJson());			//对发言人发送消息
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {			//进行广播发送并存储到数据库
			
		if(null==vo.getMsg()||""==vo.getMsg()) {		//禁言后消息为空则直接
			return;
		}
		msg=aw.replace(vo.getMsg());
		message.setContent(username, msg,userpic);	
		useraccount=iaMap.get(uid);
		
			sessions = sessionMap.get(groupId);
			broadcast(sessions, message.toJson());
			msg=username+"("+useraccount+")"+"："+date+msg+"\r\n";
			//存储到数据库
			 aw.recordGroup(groupId, msg);
			 }
		}
		
	
	public void broadcast(List<Session> ss,String msg) {		//广播的实现
		if(ss!=null)
		for(Session session :ss) {		//遍历用户，给每个用户都发送一次信息
//			System.out.println("一次");
			try {
				session.getBasicRemote().sendText(msg);			//对遍历的每个用户发送此对象信息
			} catch (IOException e) {
				e.printStackTrace();
			}
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



















