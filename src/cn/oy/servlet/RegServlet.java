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
		String iden="0";
		User user=new User(name, sex, signature, tel, age, iden, pwd, account);
		System.out.println("user="+user);
		PrintWriter out =resp.getWriter();
		RegService rs=(RegService) ioc.MapIoc.MAP.get("rs");
		int result=rs.regService(user);
		if(result>0) {
			out.print("true");
		}else {
			out.print("false");
		}
		out.flush();
		out.close();
		
    }

}
