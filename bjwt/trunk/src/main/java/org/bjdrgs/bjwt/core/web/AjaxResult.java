package org.bjdrgs.bjwt.core.web;
/**
 * ajax请求的返回信息
 * @author ying
 *
 */
public class AjaxResult {
	/**
	 * 具体状态标识
	 */
	private String status;
	private boolean success;
	private String message;
	
	public AjaxResult(){}
	
	public AjaxResult(boolean success){
		this.success = success;
	}
	
	public AjaxResult(boolean success, String message){
		this.success = success;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
