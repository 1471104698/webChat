package cn.oy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.oy.pojo.User;
import cn.oy.service.FriendService;
import cn.oy.service.LoginService;
import cn.oy.service.RegService;



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
		LoginService ls=(LoginService) util.MapIoc.MAP.get("ls");
		String account=req.getParameter("account");
		String name=req.getParameter("name");
		String pwd=req.getParameter("pwd");
		String sex=req.getParameter("sex");
		int age=Integer.parseInt(req.getParameter("age"));
		String signature=req.getParameter("signature");
		String tel=req.getParameter("tel");	
		String iden="0";
		User user=new User(name, sex, signature, tel, age, iden, pwd, account);
		PrintWriter out =resp.getWriter();
		int result=rs.regService(user);
		if(result>0) {
			user=ls.checkLogin(account, pwd);
			fs.createGroupName("我的好友", user.getId());			//创建一个默认好友分组列表
			out.print("true");
		}else {
			out.print("false");
		}
		out.flush();
		out.close();
		
    }

}
