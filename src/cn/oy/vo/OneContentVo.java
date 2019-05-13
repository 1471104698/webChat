package cn.oy.vo;

public class OneContentVo {
	
	private Integer to;
	private String msg;
	
	public Integer getTo() {
		return to;
	}
	public void setTo(Integer to) {
		this.to = to;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "OneContentVo [to=" + to + ", msg=" + msg + "]";
	}
}
