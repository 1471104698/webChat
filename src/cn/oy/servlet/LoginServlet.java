package cn.oy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.oy.pojo.User;
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
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		LoginService ls=(LoginService) util.MapIoc.MAP.get("ls");
		PrintWriter out =resp.getWriter();
		String vcode=req.getParameter("vcode");
		String ocode=(String) req.getSession().getAttribute("ocode");
		if(vcode!=null) {
		boolean result=ls.checkCode(vcode, ocode);
		if(result) {			
			out.print("true");		
		}else {
			out.print("false");		
			}
		return;
		}
		Map<Integer,String> map=(Map<Integer, String>) new HashMap<Integer, String>();
		String account=req.getParameter("account");
		String pwd=req.getParameter("pwd");
		User u=ls.checkLogin(account, pwd);
		if(u!=null) {
			int uid=u.getId();
			map.put(uid, req.getSession().getId());
			req.getServletContext().setAttribute("map", map);
			out.print("yes");
			req.getSession().setAttribute("user", u);
		}else {
			out.print("error");				  
		}
		out.flush();
		out.close();
	}

}
