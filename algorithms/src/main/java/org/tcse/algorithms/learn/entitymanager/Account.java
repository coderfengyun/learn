package org.tcse.algorithms.learn.entitymanager;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	@Id
	private long id;

	public Account() {
	}

	public Account(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

}
