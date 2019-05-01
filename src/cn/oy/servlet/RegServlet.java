package cn.oy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.oy.pojo.User;
import cn.oy.service.RegService;



/**
 * Servlet implementation class regServlet
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
		String account=req.getParameter("account");
		String name=req.getParameter("name");
		String pwd=req.getParameter("pwd");
		String sex=req.getParameter("sex");
		int age=Integer.parseInt(req.getParameter("age"));
		String signature=req.getParameter("signature");
		String tel=req.getParameter("tel");	
		String iden=req.getParameter("iden");
		User user=new User(name, sex, signature, tel, age, iden, pwd, account);
		PrintWriter out =resp.getWriter();
		RegService rs=(RegService) ioc.MapIoc.MAP.get("rs");
		int result=rs.regService(user);
		if(result>0) {
//			out.print("注册成功");
//			out.flush();
//			out.close();
			req.setAttribute("str", "注册成功");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}else {
//			out.print("注册失败");
			req.setAttribute("str", "注册失败，用户已存在");
			req.getRequestDispatcher("user/reg.jsp").forward(req, resp);
			
		}
		
    }

}
