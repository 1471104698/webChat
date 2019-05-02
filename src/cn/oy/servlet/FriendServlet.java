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
		FriendService fs=(FriendService) ioc.MapIoc.MAP.get("fs");
		PrintWriter out =resp.getWriter();
		
		String gname=req.getParameter("gname");
		if(gname!=null) {
			int uid=((User)req.getSession().getAttribute("user")).getId();
			List<User> friends = fs.friendsService(uid, gname);
			JSONArray mlist = JSONArray.fromObject(friends);
			out.print(mlist.toString());	
			return;
		}
		
		int result=0;
		int fid=Integer.parseInt(req.getParameter("fid"));
		int uid=Integer.parseInt(req.getParameter("uid"));
		String group=req.getParameter("group");
		int ch=Integer.parseInt(req.getParameter("ch"));
		if(ch==1||ch==2) {			//添加好友或删除好友
			if(ch==1)
			result=fs.AddFriendService(fid, uid, group);
			if(ch==2)
			result=fs.DelFriendService(fid, uid);
		if(result>0) {
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
		}else if(ch==3){					//查看信息
			User user=fs.SeeFriendService(fid);
			if(user!=null) {
				req.getSession().setAttribute("friend", user);
//				resp.sendRedirect("${pageContext.request.contextPath}/features/information.jsp");
				out.print("true");
			}else {
				out.print("false");
			}
			
		}else {			//修改昵称
			String nickName=req.getParameter("nickName");
		}
		
		out.flush();
		out.close();
	}

}
