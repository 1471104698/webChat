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
import cn.oy.way.AllWay;
import net.sf.json.JSONArray;
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
		int result=0;
		PrintWriter out =resp.getWriter();
		int fid;
		int uid;
		User user=null;
		/**
		 * 对好友列表的操作
		 */
		String gname=req.getParameter("gname");
		if(gname!=null) {		//不为空执行以下步骤
			int gh=Integer.parseInt(req.getParameter("gh"));
			if(gh==1||gh==2||gh==3) {
				user=(User) req.getSession().getAttribute("user");		//得到当前用户
				List<Group> groups=user.getGroups();
				uid=Integer.parseInt(req.getParameter("uid"));
				if(gh==1) {											//修改好友分组名称
				String oldgname=req.getParameter("oldgname");
				result=fs.moGroupName(gname, uid, oldgname,groups);
			}else if(gh==2){										//创建分组
				result=fs.createGroupName(gname, uid,groups);		
			}else{													//删除分组
				result=fs.deleteGroupName(gname, uid);
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
			}else {							//查找某个分组下的好友
			uid=((User)req.getSession().getAttribute("user")).getId();
			List<User> friends = fs.friendsService(uid, gname);
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
		fid=Integer.parseInt(req.getParameter("fid"));
		uid=Integer.parseInt(req.getParameter("uid"));
		String group=req.getParameter("group");
		int ch=Integer.parseInt(req.getParameter("ch"));
		if(ch==1||ch==2) {											//添加好友或删除好友
			if(ch==1) {	//添加好友			
			result=fs.AddFriendService(fid, uid, group);
			}
			if(ch==2) {	//删除好友
			result=fs.DelFriendService(fid, uid);
			}
		if(result>0) {
			if(ch==1)
				fs.AddFriendService(uid, fid, "我的好友");		
			if(ch==2)
				fs.DelFriendService(uid, fid);				
			out.print("true");
		}else if(result==0) {
			out.print("self");
		}else if(result==-1){
		out.print("false");
		}else {
			if(ch==1)
		out.print("already");
			if(ch==2)
		out.print("no");
		}
		}else if(ch==3){											//查看信息
			if(fid==uid) {
				AllWay aw=(AllWay) util.MapIoc.MAP.get("aw");
				user=aw.getUserByID(uid);
			}
			else
			user=fs.SeeFriendService(fid,uid);
			if(user!=null) {
				req.getSession().setAttribute("friend", user);			
				resp.sendRedirect("features/information.jsp");
			}		
		}else if(ch==4||ch==5){										
			if(ch==4) {													//修改昵称
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
		}	
		out.flush();
		out.close();
	}

}
