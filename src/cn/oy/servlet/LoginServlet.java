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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
    }
    
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginService ls=(LoginService) ioc.MapIoc.MAP.get("ls");
		FriendService fs=(FriendService) ioc.MapIoc.MAP.get("fs");
		PrintWriter out =resp.getWriter();
		String vcode=req.getParameter("vcode");
		String ocode=(String) req.getSession().getAttribute("ocode");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		if(vcode!=null) {
		boolean result=ls.checkCode(vcode, ocode);
		if(result) {			
			out.print("true");		
		}else {
			out.print("false");		
			}
		return;
		}
		String account=req.getParameter("account");
		String pwd=req.getParameter("pwd");
		User u=ls.checkEmpty(account, pwd);
		if(u!=null) {
			out.print("yes");
			u.setGroups(fs.groupsService(u.getId()));
			System.out.println("user="+u);
			req.getSession().setAttribute("user", u);
		}else {
			out.print("error");				  
		}
		out.flush();
		out.close();
	}

}
