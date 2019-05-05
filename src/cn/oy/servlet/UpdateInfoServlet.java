package cn.oy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.oy.pojo.User;
import cn.oy.service.UpdateInfoService;

/**
 * Servlet implementation class UpdateInfoServlet
 */
@WebServlet("/UpdateInfoServlet")
public class UpdateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateInfoServlet() {
    }

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out =resp.getWriter();
		String account=((User)req.getSession().getAttribute("user")).getAccount();
		String name=req.getParameter("name");
		int age=Integer.parseInt(req.getParameter("age"));
		String sex=req.getParameter("sex");
		String tel=req.getParameter("tel");
		String signature=req.getParameter("signature");
		User user=new User(name, sex, signature, tel, age, null, account);
		UpdateInfoService uis=(UpdateInfoService) util.MapIoc.MAP.get("uis");
		int result=uis.updateInfoService(user);
		if(result>0) {
			out.print("true");
		}else {
			out.print("false");
		}
	}
}
