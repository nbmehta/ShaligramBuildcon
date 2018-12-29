/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, V extends Serializable> {
	public List<T> getAll();
	public T getById(V key);
	public void deleteById(V key);
	public void delete(T object);
	public void update(T object);
	public void save(T object);
}
