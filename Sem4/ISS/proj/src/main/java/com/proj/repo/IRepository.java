package com.proj.repo;

import java.util.List;

public interface IRepository<E> {
	List<E> getAll();
}
