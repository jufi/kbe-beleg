package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

/**
 * @author Kevin Goy
 */
public interface UserRepository extends GenericRepository<UserModel> {
    public User getUserByName(String userName);
}
