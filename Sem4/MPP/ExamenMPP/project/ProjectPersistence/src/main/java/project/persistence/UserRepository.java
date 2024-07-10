package project.persistence;
import project.model.User;

import java.util.Optional;

public interface UserRepository extends Repository<Integer, User>{
	Optional<User> findOne(String alias);
}
