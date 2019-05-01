package cn.oy.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/GetImageServlet")
public class GetImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static String getRandomText(){
		String str = "";			//将得到的字符串起来变成字符串
		for(int i = 0; i < 4; i++){		//循环四次得到四个随机的数字字母
			Random r = new Random();		//创建随机对象
			int t = r.nextInt(3);  			//得到0-2之间的随机数来进行以下选择
			if(t == 0){				
				int x = 65 + r.nextInt(26);		//当得到0时则将得到A-Z之间随机字符
				char c = (char)x;
				str += c;
			}else if(t == 1){		
				int x = 97 + r.nextInt(26); 	//当得到1时则将得到a-z之间随机字符
				char c = (char)x;
				str += c;
			}else{
				int n = r.nextInt(10); 			//当得到2时则将得到0-9之间随机字符
				str += n;
			}			
		}
		return str;			
	}
	

	
		@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String str = getRandomText();		//将得到的验证码放入str
					
			BufferedImage img = new BufferedImage(120, 30, BufferedImage.TYPE_INT_RGB);		
			Graphics g = img.getGraphics(); 		//创建画笔
		
			g.setColor(new Color(0, 10, 0));  		//设立验证码背景颜色
			g.fillRect(0, 0, 120, 30);				//填充矩形的背景
			
			g.setColor(new Color(255, 0, 0));		//设立验证码颜色
			g.setFont(new Font("Arial", Font.PLAIN, 25));		//设置验证码字体
			g.drawString(str, 10, 25);				//将验证码在背景画出
		
			ImageIO.write(img, "jpeg", resp.getOutputStream());
			str=str.toLowerCase();	//转小写，达到不区分大小写的作用
			HttpSession session = req.getSession();
			System.out.println("验证码："+str);
			session.setAttribute("ocode", str);				//将验证码内容存放到session中以便于后续进行比较
		}
		
		
	

	

}
