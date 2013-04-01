package org.bjdrgs.bjwt.authority.parameter;

public class NewPassword {
	/**
	 * 旧密码
	 */
	private String oldPswd;
	/**
	 * 新密码
	 */
	private String newPswd;
	/**
	 * 新密码确认
	 */
	private String newPswd2;

	public String getOldPswd() {
		return oldPswd;
	}

	public void setOldPswd(String oldPswd) {
		this.oldPswd = oldPswd;
	}

	public String getNewPswd() {
		return newPswd;
	}

	public void setNewPswd(String newPswd) {
		this.newPswd = newPswd;
	}

	public String getNewPswd2() {
		return newPswd2;
	}

	public void setNewPswd2(String newPswd2) {
		this.newPswd2 = newPswd2;
	}

}
