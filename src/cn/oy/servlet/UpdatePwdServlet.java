package cn.oy.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.oy.service.UpdatePwdService;

/**
 * Servlet implementation class UpdatePwdServlet
 */
@WebServlet("/UpdatePwdServlet")
public class UpdatePwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePwdServlet() {
     
    }

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String newPwd=req.getParameter("newPwd");
		String account=(String) req.getSession().getAttribute("account");
		
		UpdatePwdService ups=(UpdatePwdService) ioc.MapIoc.MAP.get("ups");
		
		int result=ups.updatePwd(newPwd, account);
		if(result>0) {
			req.setAttribute("str", "密码修改成功");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}else {
			req.setAttribute("str", "密码修改失败");
			req.getRequestDispatcher("user/updatePwd.jsp").forward(req, resp);
		}
	}

}
