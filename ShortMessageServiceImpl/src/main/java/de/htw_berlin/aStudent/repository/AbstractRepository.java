package de.htw_berlin.aStudent.repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public abstract class AbstractRepository<T extends Serializable> {

	private Class<T> aClass;

	@PersistenceContext
	EntityManager entityManager;

	private static final Logger logger = Logger.getLogger(AbstractRepository.class);

	public void setaClass(Class<T> aClass) {
		this.aClass = aClass;
	}

	public T findById(Long id) {

		return entityManager.find(aClass, id);
	}

	@SuppressWarnings("unchecked")
	public Set<T> findAll() {
		logger.debug("example log4j message: findAll called!");
        List<T> list = entityManager.createQuery("from " + aClass.getName()).getResultList();
        return new HashSet<T>(list);
	}

	public void save(T entity) {
		entityManager.persist(entity);
	}

	public T update(T entity) {
		return entityManager.merge(entity);
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}

	public void deleteById(Long entityId) {
		T entity = findById(entityId);
		delete(entity);
	}
}