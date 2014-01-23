package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Set;

/**
 * @author Kevin Goy
 */
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
}
