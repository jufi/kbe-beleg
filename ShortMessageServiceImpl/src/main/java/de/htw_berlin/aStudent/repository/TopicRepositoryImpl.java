package de.htw_berlin.aStudent.repository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import de.htw_berlin.aStudent.model.TopicModel;

/**
 * @author Kevin Goy
 */
@Repository
public class TopicRepositoryImpl extends AbstractRepository<TopicModel> implements TopicRepository {

	public TopicRepositoryImpl() {
		setaClass(TopicModel.class);
	}

	public Set<String> castTopicModelToString(Set<TopicModel> topicModelSet) {
		Set<String> topicStringSet = new HashSet<String>();
		for (TopicModel topicModel : topicModelSet) {
			String topicString = topicModel.getName();
			topicStringSet.add(topicString);
		}
		return topicStringSet;
	}

}
