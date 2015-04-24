package org.tcse.algorithms.learn.entitymanager;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestJavaPersistenceAPI {
	UserRepository userRepo = new UserRepository();
	AccountRepository accountRepo = new AccountRepository();

	@Test
	public void testAdd() {
		User user = new User(1, "TestJavaPersistenceAPI.testAdd", 1000);
		userRepo.store(user);
	}

	@Test
	public void testCascadeUpdate() {
		int initialBalance = 1000, deductAmount = 100;
		int id = 2;
		User user = new User(id, "TestJavaPersistenceAPI.testCascadeUpdate",
				initialBalance);
		this.userRepo.store(user);
		Account account = new Account(id, user, initialBalance);
		this.accountRepo.store(account);

		account.deductBalance(deductAmount);
		this.accountRepo.store(account);

		assertEquals(initialBalance - deductAmount, (long) this.accountRepo
				.findBy((long) id).getBalance());

		assertEquals(initialBalance - deductAmount, (long) this.userRepo
				.findBy((long) id).getTotalBalance());
	}

	@Test
	public void testCascadeDelete() {
		int id = 3, initialBalance = 1000;
		User user = new User(id, "TestJavaPersistenceAPI.testCascadeDelete",
				initialBalance);
		this.userRepo.store(user);
		Account account = new Account(id, user, initialBalance);
		this.accountRepo.store(account);

		this.accountRepo.remove((long) id);

		assertNull(this.accountRepo.findBy((long) id));
		assertNotNull(this.userRepo.findBy((long) id));
	}
}
