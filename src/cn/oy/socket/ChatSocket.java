package cn.oy.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;
import cn.oy.vo.ContentVo;
import cn.oy.vo.Message;

@ServerEndpoint("/chatSocket")
public class ChatSocket {

	private String username;
	private Integer id;
	private static Integer webCount=0;
	private static List<Session> sessions=new ArrayList<>();		//将session对象存储起来
	private static Map<String,Session> msu=new HashMap<>();			//通过用户id找session
	private static Map<Integer,Session> miu=new HashMap<>();			//通过用户id找session
	private static Map<String,Integer> mui=new HashMap<>();
	private static List<String>	names=new ArrayList<>();			//将用户名存储起来
	private static List<Integer> ids=new ArrayList<>();			//将用户名存储起来
	Gson gson=(Gson) util.MapIoc.MAP.get("gson");
	
	
	@OnOpen
	public void open(Session session) {
		//当前webSocket的session对象，不是servlet的session,不能从中取到用户信息
		System.out.println("此处是ChatSocket，，config..");
		String queryString = session.getQueryString();		//得到的是username=oy
		System.out.println(queryString);
		
		username=queryString.split("=")[2];
		String str=queryString.split("=")[1];
		id =Integer.parseInt(str.split("&")[0]);
		
	
		addWebCount();
		names.add(username);
		ids.add(id);
		sessions.add(session);
		msu.put(username, session);	//将用户名和session对象关联起来
		miu.put(id, session);	
		mui.put(username,id);
		String msg="欢迎"+username+"进入聊天室!!!<br/>";
		
		Message message=new Message();
		message.setWelcome(msg);
		message.setUsernames(names);
		message.setIds(ids);
		
		broadcast(sessions,message.toJson()); //干嘛的
		}

	
	public void broadcast(List<Session> ss,String msg) {		//广播的实现
		
		for(Iterator<Session> iterator=ss.iterator();iterator.hasNext();) {		//遍历用户，给每个用户都发送一次信息
			Session session=(Session)iterator.next();
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
		
		ContentVo vo=gson.fromJson(json, ContentVo.class);//gson.fromJson()该方法将json对象转换成实体类对象
			//比如json字符串为：[{“name”:”name0”,”age”:0}]
			//Person person = gson.fromJson(str, Person.class);
		
		if(vo.getType()==1) {
			//给所有人广播，还要让所有人知道是谁说的
			Message message=new Message();
			
//			message.setUsernames(names); 没必要再放入名字了
			message.setContent(username, vo.getMsg());
			
			//根据username 如果能找到对应的session对象	
			broadcast(sessions, message.toJson());			//广播，相当于群发
		}else {					//私聊
			String to = vo.getTo();	//得到选中的用户名
			Session to_session = msu.get(to);
			Message message=new Message();			
			message.setContent(username, "<font color=red>私聊："+vo.getMsg()+"</font>");		//
			try {
				to_session.getBasicRemote().sendText(message.toJson());		//给单个人发
			} catch (IOException e) {
				// TODO Auto-generated catch block
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



















