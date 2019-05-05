package cn.oy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.oy.service.FindPwdService;

/**
 * Servlet implementation class FindPwdServlet
 */
@WebServlet("/FindPwdServlet")
public class FindPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindPwdServlet() {
       
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
    	PrintWriter out =resp.getWriter();
    	FindPwdService fps=(FindPwdService) util.MapIoc.MAP.get("fps");
    	String account=req.getParameter("account");
		String name=req.getParameter("name");
		String tel=req.getParameter("tel");		
    	int result=fps.FindService(account, name, tel);
    	System.out.println("result="+result);
    	if(result>0) {
    		out.print("true");		
        	req.getSession().setAttribute("account", account);
    	}else {
    		out.print("false");	
    	}

    	
    }

}
