package org.tcse.algorithms.learn.entitymanager;

public class UserRepository extends AbstractJPARepository<User, Long> {

	UserRepository() {
		super(User.class);
	}

}
