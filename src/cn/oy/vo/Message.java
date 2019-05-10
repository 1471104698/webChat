package cn.oy.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;


public class Message {
	private String welcome;
	
	private List<String> usernames;

	private String content;
	
	private List<Integer> ids;
	
	private List<String> accounts;
	
	private String notice;
	
	private String intro;
	
	private List<String> picPaths;
	
	
	
	
	public List<String> getPicPaths() {
		return picPaths;
	}


	public void setPicPaths(List<String> picPaths) {
		this.picPaths = picPaths;
	}


	public String getNotice() {
		return notice;
	}


	public void setNotice(String notice) {
		this.notice = notice;
	}


	public String getIntro() {
		return intro;
	}


	public void setIntro(String intro) {
		this.intro = intro;
	}


	public List<String> getAccounts() {
		return accounts;
	}


	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}


	public String getContent() {
		return content;
	}

	
	public List<Integer> getIds() {
		return ids;
	}


	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}


	public void setContent(String content) {
		this.content = content;
	}
	public void setContent(String name,String msg,String picPath) {
		//头像+谁说的+说的时间+说的内容
		this.content = "<img src='../"+picPath+"' width='"+50+"px"+"' height='"+50+"px'>"+name+" "+new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss").format(new Date())+":<br/>"+msg+"<br/>";
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	
	
	//这个过后要去看看
	public String toJson() {
		return gson.toJson(this);
	}
	Gson gson=(Gson) util.MapIoc.MAP.get("gson");
}



















