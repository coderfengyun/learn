package org.tcse.algorithms.hibernate_ehcache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "cached_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CachedEntity {
	@Id
	private int id;

	@Column
	private int name;

	public CachedEntity() {
	}

	public CachedEntity(int i, String string) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public int getName() {
		return name;
	}

}
