package cn.oy.pojo;

public class User {
	
	private String name;
	private String sex;
	private String signature;
	private String tel;
	private Integer age;
	private String iden;
	private String pwd;
	private String account;
	
	public User() {
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
		return "User [name=" + name + ", sex=" + sex + ", signature=" + signature + ", tel=" + tel + ", age=" + age
				+ ", iden=" + iden + ", pwd=" + pwd + ", account=" + account + "]";
	}
}
