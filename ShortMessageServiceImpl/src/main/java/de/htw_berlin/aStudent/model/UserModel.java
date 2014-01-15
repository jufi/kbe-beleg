package de.htw_berlin.aStudent.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

/**
 * @author Kevin Goy
 */
@Entity
@Table(name = "User")
public class UserModel extends User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id = 0;
	@Column(name = "name", length = 30)
	private String name = "";
	@Column(name = "city", length = 30)
	private String city = "";

	public UserModel() {
	}

	public UserModel(String name, String city) {
		this.name = name;
		this.city = city;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "UserModel{" +
				"id=" + id +
				", name='" + name + '\'' +
				", city='" + city + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserModel)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		UserModel userModel = (UserModel) o;
		if (id != userModel.id) {
			return false;
		}
		if (!city.equals(userModel.city)) {
			return false;
		}
		if (!name.equals(userModel.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (int) (id ^ (id >>> 32));
		result = 31 * result + name.hashCode();
		result = 31 * result + city.hashCode();
		return result;
	}
}
