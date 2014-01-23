package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageModel;

import java.util.Set;

/**
 * @author Kevin Goy
 */
public class MessageRepositoryImpl extends AbstractRepository<MessageModel> implements MessageRepository {

    public MessageRepositoryImpl() {
        setaClass(MessageModel.class);
    }

    @Override
    public Long getIDByMessageModel(MessageModel messageModel) {
        Long id = (long) 0;
        for (MessageModel message : findAll()) {
            if (messageModel.isOrigin() == message.isOrigin() &&
                    messageModel.getContent().equals(message.getContent()) &&
                    messageModel.getDate().compareTo(message.getDate()) == 0 &&
                    messageModel.getTopic().equals(message.getTopic()) &&
                    messageModel.getUser().equals(message.getUser())) {
                id = message.getId();
            }
        }
        return id;
    }
}
