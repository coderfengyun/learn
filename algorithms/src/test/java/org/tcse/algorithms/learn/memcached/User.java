package org.tcse.algorithms.learn.memcached;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7931600704727005553L;
	private String password;
	private String userName;

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((this.userName == null) ? 0 : this.userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null || !(obj instanceof User))) {
			return false;
		}
		User input = (User) obj;
		return this.getUserName().equals(input.getUserName())
				&& this.getPassword().equals(input.getPassword());
	}
}
