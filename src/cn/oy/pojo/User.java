package cn.oy.pojo;

import java.util.List;

public class User {
	private Integer id;
	private String name;	
	private String sex;
	private String signature;
	private String tel;
	private Integer age;
	private String iden;
	private String pwd;
	private String account;
	private List<Group> groups;			//好友分组
	private List<User> friends;			//好友
	private String nickName;			//群昵称
	private List<GroupChat> groupChats;	//加入的群
	private String pic;					//头像
	public User() {
	}
	
	public User(Integer id, String name, String sex, String signature, String tel, Integer age, String iden, String pwd,
			String account, List<Group> groups, List<User> friends, String nickName, List<GroupChat> groupChats,
			String pic) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.signature = signature;
		this.tel = tel;
		this.age = age;
		this.iden = iden;
		this.pwd = pwd;
		this.account = account;
		this.groups = groups;
		this.friends = friends;
		this.nickName = nickName;
		this.groupChats = groupChats;
		this.pic = pic;
	}

	public User(String name, String sex, String signature, String tel, Integer age, String pwd, String account) {
		super();
		this.name = name;
		this.sex = sex;
		this.signature = signature;
		this.tel = tel;
		this.age = age;
		this.pwd = pwd;
		this.account = account;
	}

	public User(String name, String sex, String signature, String tel, Integer age, String iden, String pwd,
			String account) {
		super();
		this.name = name;
		this.sex = sex;
		this.signature = signature;
		this.tel = tel;
		this.age = age;
		this.iden = iden;
		this.pwd = pwd;
		this.account = account;
	}

	
	





	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public List<GroupChat> getGroupChats() {
		return groupChats;
	}

	public void setGroupChats(List<GroupChat> groupChats) {
		this.groupChats = groupChats;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIden() {
		return iden;
	}
	public void setIden(String iden) {
		this.iden = iden;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", sex=" + sex + ", signature=" + signature + ", tel=" + tel
				+ ", age=" + age + ", iden=" + iden + ", pwd=" + pwd + ", account=" + account + ", groups=" + groups
				+ ", friends=" + friends + ", nickName=" + nickName + ", grogpChats=" + groupChats + "]";
	}
}
