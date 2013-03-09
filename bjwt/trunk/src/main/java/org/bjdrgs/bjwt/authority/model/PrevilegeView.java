package org.bjdrgs.bjwt.authority.model;

public class PrevilegeView extends Previlege {
	private static final long serialVersionUID = -2697542917823522567L;

	private Role role = null;

	private User user = null;
	
	private Menu menu;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}
