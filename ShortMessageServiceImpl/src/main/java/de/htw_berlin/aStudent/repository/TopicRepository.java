package de.htw_berlin.aStudent.repository;

import java.util.Set;

import de.htw_berlin.aStudent.model.TopicModel;

/**
 * @author Kevin Goy
 */
public interface TopicRepository extends GenericRepository<TopicModel> {
	public Set<String> castTopicModelToString(Set<TopicModel> topicModelSet);

}
