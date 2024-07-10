package project.persistence;


import project.model.User;

public interface UserRepository extends Repository<Integer, User> {
	User findUser(String username, String password);
}
