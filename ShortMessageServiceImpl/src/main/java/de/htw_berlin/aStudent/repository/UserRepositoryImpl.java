package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserModel;

import javax.persistence.Query;
import java.util.Set;

/**
 * @author Kevin Goy
 */
public class UserRepositoryImpl extends AbstractRepository<UserModel> implements UserRepository {

	public UserRepositoryImpl() {
		setaClass(UserModel.class);
	}


}
