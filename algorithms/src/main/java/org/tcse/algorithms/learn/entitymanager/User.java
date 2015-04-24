package org.tcse.algorithms.learn.entitymanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	private long id;
	@Column
	private String name;
	@Column
	private double totalBalance;

	public User() {
	}

	public User(long id, String name, double initialBalance) {
		this.id = id;
		this.name = name;
		this.totalBalance = initialBalance;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void deductBalance(double amount) {
		this.totalBalance -= amount;
	}
}
