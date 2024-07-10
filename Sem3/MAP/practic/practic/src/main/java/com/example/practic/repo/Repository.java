package com.example.practic.repo;

import java.util.List;

public interface Repository<E> {
	List<E> getAll();
}
