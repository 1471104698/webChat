package cn.oy.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.oy.pojo.User;
import cn.oy.service.CheckEmpty;

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
		CheckEmpty ce=(CheckEmpty) ioc.MapIoc.MAP.get("ce");
		
		String account=req.getParameter("account");
		String pwd=req.getParameter("pwd");
		User u=ce.checkEmpty(account, pwd);
		HttpSession session=req.getSession();
		if(u!=null) {
			session.setAttribute("user", u);
			resp.sendRedirect("chat.jsp");
		}else {
			req.setAttribute("str", "用户或密码错误！！！");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}

}
