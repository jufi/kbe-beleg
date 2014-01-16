package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserModel;

/**
 * @author Kevin Goy
 */
public class UserRepositoryImpl extends AbstractRepository<UserModel> implements UserRepository {

	public UserRepositoryImpl() {
		setaClass(UserModel.class);
	}
}
