package org.tcse.algorithms.learn.entitymanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "entity_with_private_contructor")
public class EntityWithPrivateConstructor {
	@Id
	@Column
	private int id;

	@SuppressWarnings("unused")
	private EntityWithPrivateConstructor() {
	}

	public EntityWithPrivateConstructor(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
