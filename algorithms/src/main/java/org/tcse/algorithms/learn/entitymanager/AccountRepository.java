package org.tcse.algorithms.learn.entitymanager;

public class AccountRepository extends AbstractJPARepository<Account, Long> {

	AccountRepository() {
		super(Account.class);
	}

}
