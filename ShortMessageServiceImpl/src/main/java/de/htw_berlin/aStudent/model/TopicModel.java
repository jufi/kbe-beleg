package de.htw_berlin.aStudent.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Kevin Goy
 */
@Entity
public class TopicModel implements Serializable {

	public TopicModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id = 0;

	@Column(nullable = false, length = 30)
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TopicModel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
