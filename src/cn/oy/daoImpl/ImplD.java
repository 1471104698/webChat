package cn.oy.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.oy.dao.UserDao;
import cn.oy.pojo.Group;
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
				u.setAccount(rs.getString("account"));
				u.setPwd(rs.getString("pwd"));
				u.setId(rs.getInt("uid"));
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
	
	//通过账户查询用户是否为空
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
				u.setId(rs.getInt("uid"));
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
	//通过id查询账户是否为空
		@Override
		public User isEmpty(Integer id) {
			try {
				con=du.getCon();
				//创建Sql命令
				String sql="select *from user where uid =?";	// 
				//创建Sql命令对象
				psta=(PreparedStatement) con.prepareStatement(sql);
				//给占位符赋值
				psta.setInt(1, id);
				rs=psta.executeQuery();
				if(rs.next()) {   //只有一个，因此不需要用while，用if 	
					u=new User();
					u.setId(rs.getInt("uid"));
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


	//添加好友
	@Override
	public int AddFriendDao(Integer fid, Integer uid,String group) {
		int result=-1;
		try {		
			con=du.getCon();
			String sql="insert into friends (f_fid,f_uid,f_groupname) values (?,?,?)";
			psta=con.prepareStatement(sql);
			psta.setInt(1, fid);
			psta.setInt(2, uid);
			psta.setString(3, group);
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
	
	//删除好友
	@Override
	public int DelFriendDao(Integer fid, Integer uid) {
		int result=-1;
		try {		
			con=du.getCon();
			String sql="delete from friends where f_fid=? and f_uid=?";
			psta=con.prepareStatement(sql);
			psta.setInt(1, fid);
			psta.setInt(2, uid);
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

	//查询用户信息
	@Override
	public User SeeFriendDao(Integer fid) {
		try {
			con=du.getCon();
			String sql="select * from user where uid=?";
			psta=con.prepareStatement(sql);
			
			psta.setInt(1, fid);
	
			rs=psta.executeQuery();
			if(rs.next()) {
				u=new User();
				u.setAccount(rs.getString("account"));
				u.setId(rs.getInt("uid"));
				u.setName(rs.getString("true_name"));
				u.setAge(rs.getInt("age"));
				u.setSex(rs.getString("sex"));
				u.setSignature(rs.getString("signature"));
				u.setTel(rs.getString("tel"));
				u.setIden(rs.getString("iden"));
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
			return u;	
	}
	
	
	//查询是否已经是好友
	@Override
	public int CheckFriendDao(Integer fid, Integer uid) {
		int result=-1;
		try {
			con=du.getCon();
			String sql="select * from friends where f_fid=? and f_uid=?";
			psta=con.prepareStatement(sql);
			
			psta.setInt(1, fid);
			psta.setInt(2, uid);
			
			
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

	//修改用户信息
	@Override
	public int updateInfoDao(User user) {
		int result=-1;
		try {
			
			con=du.getCon();
			String sql="update user set sex=?,tel=?,true_name=?,age=?,signature=? where account=?";
			psta=con.prepareStatement(sql);
			psta.setString(1, user.getSex());
			psta.setString(2, user.getTel());
			psta.setString(3, user.getName());
			psta.setInt(4, user.getAge());
			psta.setString(5, user.getSignature());
			psta.setString(6, user.getAccount());
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

	
	//得到某个分组下的好友
	@Override
	public List<User> friendsDao(Integer uid,String group) {
		List<User> friends=new ArrayList<User>();
		try {
			con=du.getCon();
			//创建Sql命令
			String sql="  select * from user where uid in "
					+ " (select f_fid from friends where f_uid=? and f_groupname=?)";	// 
			//创建Sql命令对象
			psta=(PreparedStatement) con.prepareStatement(sql);
			//给占位符赋值
			psta.setInt(1, uid);
			psta.setString(2, group);
			rs=psta.executeQuery();
			while(rs.next()) {  
				u=new User();
				u.setId(rs.getInt("uid"));
				u.setName(rs.getString("true_name"));
				u.setAge(rs.getInt("age"));
				u.setSex(rs.getString("sex"));
				u.setSignature(rs.getString("signature"));
				u.setTel(rs.getString("tel"));
				u.setIden(rs.getString("iden"));
				friends.add(u);
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
		return friends;
	}

	//得到用户的所有分组
	@Override
	public List<Group> groupsDao(Integer uid) {
		List<Group> groups=new ArrayList<Group>();
		Group group=null;
		try {
			con=du.getCon();
			//创建Sql命令
			String sql="  select * from user_group where ug_uid =? ";
						
			//创建Sql命令对象
			psta=(PreparedStatement) con.prepareStatement(sql);
			//给占位符赋值
			psta.setInt(1, uid);
			rs=psta.executeQuery();
			while(rs.next()) {  
				group=new Group();
				group.setId(rs.getInt("ug_id"));
				group.setId(rs.getInt("ug_uid"));
				group.setName(rs.getString("ug_name"));
				groups.add(group);
				System.out.println("用户分组为"+group);
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
		return groups;
	}

	

	

	
	
	

}
