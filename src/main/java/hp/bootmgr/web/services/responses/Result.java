package hp.bootmgr.web.services.responses;

public class Result {
	private String message;
	private int code;
							
	public Result(String msg, int code) {
		this.message = msg;
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
