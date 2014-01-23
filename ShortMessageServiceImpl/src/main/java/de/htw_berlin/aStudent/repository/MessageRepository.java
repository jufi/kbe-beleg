package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageModel;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;

/**
 * @author Kevin Goy
 */
public interface MessageRepository extends GenericRepository<MessageModel> {

	public Long getIDByMessageModel(MessageModel messageModel);

	public Message castModelToMessage(MessageModel messageModel);
}
