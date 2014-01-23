package de.htw_berlin.aStudent.repository;

import org.springframework.stereotype.Repository;

import de.htw_berlin.aStudent.model.MessageModel;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;

/**
 * @author Kevin Goy
 */
@Repository
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
