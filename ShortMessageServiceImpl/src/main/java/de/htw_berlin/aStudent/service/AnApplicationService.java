package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.TopicModel;
import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

import java.util.Set;

public interface AnApplicationService {

	
	void doSomeThing();
    public Set<User> castModelToUser(Set<UserModel> userModelSet);
    public Set<UserModel> castUserToModel(Set<User> userSet);
    public Set<String> castTopicModelToString(Set<TopicModel> topicModelSet);
}
