package de.htw_berlin.aStudent.repository;

import java.util.Set;

import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

/**
 * @author Kevin Goy
 */
public interface UserRepository extends GenericRepository<UserModel> {
	public UserModel getUserByName(String userName);

	public Set<User> castModelToUser(Set<UserModel> userModelSet);

	public Set<UserModel> castUserToModel(Set<User> userSet);
}
