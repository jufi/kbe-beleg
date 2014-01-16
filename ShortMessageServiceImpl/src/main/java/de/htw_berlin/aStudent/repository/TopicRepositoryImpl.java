package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.TopicModel;

/**
 * @author Kevin Goy
 */
public class TopicRepositoryImpl extends AbstractRepository<TopicModel> implements TopicRepository {

	public TopicRepositoryImpl() {
		setaClass(TopicModel.class);
	}
}
