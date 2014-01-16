package de.htw_berlin.aStudent.repository;

import java.util.List;

/**
 * @author Kevin Goy
 */
public interface GenericRepository<T> {

	public T findById(Long id);

	public List<T> findAll();

	public void save(T entity);

	public T update(T entity);

	public void deleteById(Long id);

	public void delete(T entity);
}
