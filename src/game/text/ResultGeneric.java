package game.text;

public class ResultGeneric implements Result {
	private String message;
	private Boolean success;
	
	public ResultGeneric(boolean isSuccessful, String message) {
		this.success = isSuccessful;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
