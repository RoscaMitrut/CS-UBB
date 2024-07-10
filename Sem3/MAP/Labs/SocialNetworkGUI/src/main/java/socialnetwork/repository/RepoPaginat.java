package socialnetwork.repository;

import socialnetwork.domain.Entity;
import socialnetwork.domain.exceptions.EntityAlreadyExistsException;
import socialnetwork.domain.exceptions.EntityMissingException;
import socialnetwork.domain.validators.ValidationException;

import java.util.Map;

public interface RepoPaginat<ID, E extends Entity<ID>> extends Repository<ID, E>{
	public Iterable<E> findAllPaginat(Integer limit,Integer offset);
}
