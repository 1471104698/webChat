package cn.oy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.oy.service.FriendService;
import net.sf.json.JSONArray;
import util.GetWay;
import cn.oy.pojo.Group;
import cn.oy.pojo.User;
/**
 * Servlet implementation class FriendServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendServlet() {
    }

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		FriendService fs=(FriendService) util.MapIoc.MAP.get("fs");
		int result=-1;
		PrintWriter out =resp.getWriter();
		int fid=-1;
		int uid=-1;
		String group=null;		//好友分组名
		User user=null;
		List<User> users=null;
		/**
		 * 对好友列表的操作
		 */
		String groupName=req.getParameter("gname");				//分组名
		if(groupName!=null) {		//不为空执行以下步骤
			int gh=Integer.parseInt(req.getParameter("gh"));
			if(gh==1||gh==2||gh==3) {
				user=(User) req.getSession().getAttribute("user");		//得到当前用户
				List<Group> groups=user.getGroups();
				uid=Integer.parseInt(req.getParameter("uid"));
				if(gh==1) {											//修改好友分组名称
				String oldgname=req.getParameter("oldgname");
				result=fs.moGroupName(groupName, uid, oldgname,groups);
			}else if(gh==2){										//创建分组
				result=fs.createGroupName(groupName, uid,groups);		
			}else{													//删除分组
				result=fs.deleteGroupName(groupName, uid);
			}
				if(result>0) {
				user.setGroups(fs.groupsService(uid));					//更新好友列表菜单
				req.getSession().setAttribute("user", user);
				out.print("true");
				}
				else if(result==-1){
					out.print("false");
					}
				else
					out.print("exist");
			}
			else if(gh==4){							//查找某个分组下的好友
			uid=((User)req.getSession().getAttribute("user")).getId();
			List<User> friends = fs.friendsService(uid, groupName);
			JSONArray mlist = JSONArray.fromObject(friends);
			out.print(mlist.toString());	
			}
			out.flush();
			out.close();
			return;	
		}
			
			
		
		/**
		 * 对好友的操作
		 */
		
		if(null!=req.getParameter("fid")&&null!=req.getParameter("uid")) {
		fid=Integer.parseInt(req.getParameter("fid"));
		uid=Integer.parseInt(req.getParameter("uid"));
		group=req.getParameter("group");		//好友分组名
		}
		int account=-1;	//好友查询条件--账号
		
		int ch=Integer.parseInt(req.getParameter("ch"));
		if(ch==1||ch==2) {			//添加好友或删除好友
			if(ch==1) //添加好友			
			result=fs.AddFriendService(fid, uid, group);			
			else 	//删除好友
			result=fs.DelFriendService(fid, uid);
			
		if(result>0) {
			if(ch==1)
				fs.AddFriendService(uid, fid, "我的好友");		
			else
				fs.DelFriendService(uid, fid);				
			out.print("true");
		}else if(result==0) {
			out.print("self");
		}else if(result==-1){
		out.print("false");
		}else {
			if(ch==1)
		out.print("already");
			else
		out.print("no");
		}
		}else if(ch==3){				//查看信息
			if(fid==uid) {
				GetWay aw=(GetWay) util.MapIoc.MAP.get("aw");
				user=aw.getUserByID(uid);
			}
			else
			user=fs.SeeFriendService(fid,uid);
			if(user!=null) {
				req.getSession().setAttribute("friend", user);			
				resp.sendRedirect("features/information.jsp");
			}		
		}else if(ch==4||ch==5){										
			if(ch==4) {					//修改昵称
			String nickName=req.getParameter("nickName");
			User friend=(User) req.getSession().getAttribute("friend");
			friend.setNickName(nickName);
			req.getSession().setAttribute("friend", friend);		
			result = fs.moNickName(fid, uid, nickName);
			}else {														//修改好友所在分组
				result=fs.moveFriend(fid, uid, group);		
			}
			if(result>0) {
				out.print("true");
			}else
				out.print("false");
		}else if(ch==6){					//查找好友
			System.out.println("调用了6");
			String value=req.getParameter("value");	//查询好友条件--用户名||账号
			JSONArray mlist =new JSONArray();
			if(GetWay.isNum(value)) {			//如果全数字则按账号查询
				System.out.println("value="+account);
				user = fs.isExistByAccount(value);
				mlist = JSONArray.fromObject(user);
				out.print(mlist.toString());
			}
			else {			//按名字查询
				users=fs.isExistByName(value);
				mlist = JSONArray.fromObject(users);
				out.print(mlist.toString());
			}
		}	
		out.flush();
		out.close();
	}

}
