package de.htw_berlin.aStudent.repository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

/**
 * @author Kevin Goy
 */
@Repository
public class UserRepositoryImpl extends AbstractRepository<UserModel> implements UserRepository {

	public UserRepositoryImpl() {
		setaClass(UserModel.class);
	}

    // TODO should this method be part of the userRepository?
    @Transactional(readOnly = true)
    public User getUserByName(String userName) {
        User user = new User();
        Set<UserModel> userModelSet = findAll();
        for (UserModel userModel : userModelSet) {
            if (userModel.getName().equals(userName)) {
                user.setCity(userModel.getCity());
                user.setName(userModel.getName());
            }
        }
        return user;
    }

	public Set<User> castModelToUser(Set<UserModel> userModelSet) {
		Set<User> userSet = new HashSet<User>();
		for (UserModel userModel : userModelSet) {
			User user = new User();
			user.setCity(userModel.getCity());
			user.setName(userModel.getName());
			userSet.add(user);
		}
		return userSet;
	}


	public Set<UserModel> castUserToModel(Set<User> userSet) {
		Set<UserModel> userModelSet = new HashSet<UserModel>();
		for (User user : userSet) {
			UserModel userModel = new UserModel();
			userModel.setCity(user.getCity());
			userModel.setName(user.getName());
			userModelSet.add(userModel);
		}
		return userModelSet;
	}


}
