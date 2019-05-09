package cn.oy.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.oy.pojo.User;
import cn.oy.service.FriendService;
import cn.oy.service.RegService;
import cn.oy.way.AllWay;



/**
 * Servlet implementation class regServlet
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		FriendService fs=(FriendService) util.MapIoc.MAP.get("fs");
		RegService rs=(RegService) util.MapIoc.MAP.get("rs");
		AllWay aw=(AllWay) util.MapIoc.MAP.get("aw");
		String account=req.getParameter("account");
		String name=req.getParameter("name");
		String pwd=req.getParameter("pwd");
		String sex=req.getParameter("sex");
		int age=Integer.parseInt(req.getParameter("age"));
		String signature=req.getParameter("signature");
		String tel=req.getParameter("tel");	
		User user=new User(name, sex, signature, tel, age, "0", pwd, account);
		PrintWriter out =resp.getWriter();
		int result=rs.regService(user);
		if(result>0) {
			user=aw.getUserByAccount(account);
			fs.createGroupName("我的好友", user.getId(),null);			//创建一个默认好友分组列表
			out.print("true");
		}else {
			out.print("false");
		}
		out.flush();
		out.close();
		
    }

}
