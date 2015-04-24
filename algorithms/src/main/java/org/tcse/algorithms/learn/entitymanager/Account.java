package org.tcse.algorithms.learn.entitymanager;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	@Id
	private long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	@JoinColumn
	private User user;
	@Column
	private double balance;

	public Account() {
	}

	public Account(long id, User user, double initBalance) {
		this.id = id;
		this.user = user;
		this.balance = initBalance;
	}

	public long getId() {
		return id;
	}

	User getUser() {
		return user;
	}

	public double getBalance() {
		return balance;
	}

	public void deductBalance(double amount) {
		this.balance -= amount;
		this.getUser().deductBalance(amount);
	}
}
