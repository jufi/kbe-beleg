package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.MessageModel;
import de.htw_berlin.aStudent.model.TopicModel;
import de.htw_berlin.aStudent.model.UserModel;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class AnApplicationServiceImpl implements AnApplicationService {

	public void doSomeThing() {
		// TODO Auto-generated method stub
		System.out.println("doSomeThing was called");
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

    public Set<String> castTopicModelToString(Set<TopicModel> topicModelSet) {
        Set<String> topicStringSet = new HashSet<String>();
        for (TopicModel topicModel  : topicModelSet) {
            String topicString = topicModel.getName();
            topicStringSet.add(topicString);
        }
        return topicStringSet;
    }

    @Override
    public Message castModelToMessage(MessageModel messageModel) {
        Message message = new Message();
        message.setDate(messageModel.getDate());
        message.setUser(messageModel.getUser());
        message.setTopic(messageModel.getTopic());
        message.setOrigin(messageModel.getOrigin());
        message.setContent(messageModel.getContent());
        message.setMessageId(messageModel.getMessageId());
        return message;
    }


}
