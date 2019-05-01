package cn.oy.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.oy.dao.UserDao;
import cn.oy.pojo.User;
import util.DbUtil;

public class ImplD implements UserDao {
	DbUtil du=new DbUtil();
	Connection con=null;
	PreparedStatement psta=null;
	ResultSet rs=null;
	User u=null;
	
	
	
	//登录并得到用户信息
	@Override
	public User LoginDao(String account, String pwd) {
		try {
			con=du.getCon();
			//创建Sql命令
			String sql="select *from user where account =? and pwd=MD5(?)";	// 
			//创建Sql命令对象
			psta=(PreparedStatement) con.prepareStatement(sql);
			//给占位符赋值
			psta.setString(1, account);
			psta.setString(2, pwd);
			//执行，执行多少条语句，返回值则为多少
			rs=psta.executeQuery();
			//遍历执行结果
			if(rs.next()) {   //只有一个，因此不需要用while，用if 	
				//给变量赋值
				u=new User();
				u.setName(rs.getString("true_name"));
				u.setAge(rs.getInt("age"));
				u.setSex(rs.getString("sex"));
				u.setSignature(rs.getString("signature"));
				u.setTel(rs.getString("tel"));
				u.setIden(rs.getString("iden"));
			}
		} catch (SQLException e) {	
			e.printStackTrace();
		}catch(Exception e2) {
			e2.printStackTrace();
		}finally {
			try {	//关闭资源
				rs.close();
				du.close(psta, con);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}catch(Exception e3) {
				e3.printStackTrace();
			}
		}
		//返回
		return u;
	}
	
	//查询用户是否为空
	public User isEmpty(String account) {
		try {
			con=du.getCon();
			//创建Sql命令
			String sql="select *from user where account =?";	// 
			//创建Sql命令对象
			psta=(PreparedStatement) con.prepareStatement(sql);
			//给占位符赋值
			psta.setString(1, account);
			rs=psta.executeQuery();
			if(rs.next()) {   //只有一个，因此不需要用while，用if 	
				u=new User();
				u.setName(rs.getString("true_name"));
				u.setAge(rs.getInt("age"));
				u.setSex(rs.getString("sex"));
				u.setSignature(rs.getString("signature"));
				u.setTel(rs.getString("tel"));
				u.setIden(rs.getString("iden"));
			}
			
		} catch (SQLException e) {	
			e.printStackTrace();
		}catch(Exception e2) {
			e2.printStackTrace();
		}finally {
			try {	//关闭资源
				rs.close();
				du.close(psta, con);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}catch(Exception e3) {
				e3.printStackTrace();
			}
		}
		return u;
	}

	//用户注册
	@Override
	public int regDao(User user) {
		int result=-1;
		try {
			con=du.getCon();
			//创建Sql命令
			String sql="insert into user (account,pwd,tel,sex,age,signature,iden,true_name) values(?,MD5(?),?,?,?,?,?,?)";	//注册用户
			//创建Sql命令对象
			psta=con.prepareStatement(sql);
			//给占位符赋值
			psta.setString(1, user.getAccount());
			psta.setString(2, user.getPwd());
			psta.setString(3, user.getTel());
			psta.setString(4, user.getSex());
			psta.setInt(5, user.getAge());
			psta.setString(6, user.getSignature());
			psta.setString(7, user.getIden());
			psta.setString(8, user.getName());
			//执行，执行多少条语句，返回值则为多少						
			result=psta.executeUpdate();
		}catch (SQLException e) {	
			e.printStackTrace();
		}catch(Exception e2) {
			e2.printStackTrace();
		}finally {
			try {	//关闭资源
				du.close(psta, con);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}catch(Exception e3) {
				e3.printStackTrace();
			}
		}
		return result;
	}

	//找回密码
	@Override
	public int findPwdDao(String account, String name, String tel) {
		int result=-1;
		try {
			con=du.getCon();
			String sql="select * from user where account=? and true_name=? and tel=?";
			psta=con.prepareStatement(sql);
			
			psta.setString(1, account);
			psta.setString(2, name);
			psta.setString(3, tel);
			
			rs=psta.executeQuery();
			if(rs.next()) {
				result=1;
			}
			}catch (SQLException e) {	
				e.printStackTrace();
			}catch(Exception e2) {
				e2.printStackTrace();
			}finally {
				try {	//关闭资源
					rs.close();
					du.close(psta, con);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}catch(Exception e3) {
					e3.printStackTrace();
				}
			}
			return result;	
	}

	//修改密码
	@Override
	public int updatePwdDao(String newPwd, String account) {
		int result=-1;
		try {
			
			con=du.getCon();
			String sql="update user set pwd=MD5(?) where account=?";
			psta=con.prepareStatement(sql);
			psta.setString(1, newPwd);
			psta.setString(2, account);
			result=psta.executeUpdate();
		} catch (SQLException e) {	
			e.printStackTrace();
		}catch(Exception e2) {
			e2.printStackTrace();
		}finally {
			try {	//关闭资源
			
				du.close(psta, con);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}catch(Exception e3) {
				e3.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	

}
