package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageModel;

import java.util.List;

/**
 * @author Kevin Goy
 */
public class MessageRepositoryImpl extends AbstractRepository<MessageModel> implements MessageRepository {

	public MessageRepositoryImpl() {
		setaClass(MessageModel.class);
	}

}
