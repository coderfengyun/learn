package org.tcse.algorithms.learn.entitymanager;

import org.junit.Test;

public class TestRepository {
	@Test
	public void testAdd() {
		User user = new User(1, "userToAdd");
		UserRepository userRepo = new UserRepository();
		userRepo.attach(user);
	}
}
