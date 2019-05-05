package cn.oy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String newPwd=req.getParameter("newPwd");
		String cfPwd=req.getParameter("cfPwd");
		String account=(String) req.getSession().getAttribute("account");
		PrintWriter out =resp.getWriter();
		UpdatePwdService ups=(UpdatePwdService) util.MapIoc.MAP.get("ups");
		int result=ups.updatePwd(newPwd,cfPwd,account);
		if(result>0) {
			out.print("true");	
		}else if(result==-2) {
			out.print("unsame");	
		}else {
			out.print("false");
		}		
	}

}
