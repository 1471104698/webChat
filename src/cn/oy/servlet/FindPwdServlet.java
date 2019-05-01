package cn.oy.servlet;

import java.io.IOException;
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
    	FindPwdService fps=(FindPwdService) ioc.MapIoc.MAP.get("fps");
    	String account=req.getParameter("account");
		String name=req.getParameter("name");
		String tel=req.getParameter("tel");	
    	int result=fps.FindService(account, name, tel);
    	if(result>0) {
    		req.getSession().setAttribute("account", account);
			req.getRequestDispatcher("user/updatePwd.jsp").forward(req, resp);
    	}else {
    		req.setAttribute("str", "信息填写错误！！！");
    		req.getRequestDispatcher("user/find.jsp").forward(req, resp);
    	}
    }

}
