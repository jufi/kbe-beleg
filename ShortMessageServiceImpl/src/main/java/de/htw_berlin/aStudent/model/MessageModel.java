package de.htw_berlin.aStudent.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;

/**
 * @author Kevin Goy
 */
@Entity
public class MessageModel extends Message implements Serializable, Comparable<MessageModel> {

	@Id
	@GeneratedValue
	private long id = 0;

	@Temporal(TemporalType.DATE)
	@Column
	private Date date;
	@Transient
	final private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@Column
	private String content;

	@Column
	private String topic;

	@OneToOne
	private UserModel user;

	@Column
	private Boolean origin;

	@Column
	private long predecessorId;

	public MessageModel() {
	}

	public MessageModel(Date date, String content, String topic, UserModel user, Boolean origin) {
		this.date = date;
		this.content = content;
		this.topic = topic;
		this.user = user;
		this.origin = origin;
	}

	public long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getContent() {
		return content;
	}

	public String getTopic() {
		return topic;
	}

	public UserModel getUser() {
		return user;
	}

	public Boolean getOrigin() {
		return origin;
	}

	public long getPredecessorId() {
		return predecessorId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public void setOrigin(Boolean origin) {
		this.origin = origin;
	}

	public void setPredecessorId(long predecessorId) {
		this.predecessorId = predecessorId;
	}

	@Override
	public String toString() {
		return "MessageModel{" +
				"id=" + id +
				", date=" + date +
				", dateFormat=" + dateFormat +
				", content='" + content + '\'' +
				", topic='" + topic + '\'' +
				", user=" + user +
				", origin=" + origin +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof MessageModel)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		MessageModel that = (MessageModel) o;
		if (id != that.id) {
			return false;
		}
		if (!content.equals(that.content)) {
			return false;
		}
		if (!date.equals(that.date)) {
			return false;
		}
		if (!dateFormat.equals(that.dateFormat)) {
			return false;
		}
		if (!origin.equals(that.origin)) {
			return false;
		}
		if (!topic.equals(that.topic)) {
			return false;
		}
		if (!user.equals(that.user)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (int) (id ^ (id >>> 32));
		result = 31 * result + date.hashCode();
		result = 31 * result + dateFormat.hashCode();
		result = 31 * result + content.hashCode();
		result = 31 * result + topic.hashCode();
		result = 31 * result + user.hashCode();
		result = 31 * result + origin.hashCode();
		return result;
	}

	@Override
	public int compareTo(MessageModel otherMessageModel) {
		return this.getDate().compareTo(otherMessageModel.getDate());
	}
}
