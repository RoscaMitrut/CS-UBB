package project.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.User;
import project.persistence.UserRepository;
import utils.Crypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDBRepository implements UserRepository {

	private JdbcUtils dbUtils;
	private static final Logger logger= LogManager.getLogger();
	public UserDBRepository(Properties props) {
		logger.info("Initializing UserDBRepository with properties: {} ",props);
		dbUtils=new JdbcUtils(props);
	}

	@Override
	public void add(User elem) {
		logger.traceEntry("saving task {}",elem);
		Connection con = dbUtils.getConnection();
		try (PreparedStatement preStmt = con.prepareStatement("insert into Useri (username, password) values (?,?)")) {
			preStmt.setString(1, elem.getUsername());
			preStmt.setString(2, elem.getPassword());
			int result = preStmt.executeUpdate();
			logger.trace("Saved {} instances", result);
		} catch (SQLException ex) {
			logger.error(ex);
			System.err.println("Error DB " + ex);
		}
		logger.traceExit();
	}

	@Override
	public void update(Integer integer, User elem) {
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		try(PreparedStatement preStmt = con.prepareStatement("update Useri set username = ?, password = ? WHERE id = ?")){
			preStmt.setString(1,elem.getUsername());
			preStmt.setString(2, elem.getPassword());
			preStmt.setInt(3,integer);
			int result = preStmt.executeUpdate();
			logger.trace(result);
		}catch (SQLException ex) {
			logger.error(ex);
			System.err.println("Error DB " + ex);
		}
		logger.traceExit();
	}

	@Override
	public Iterable<User> findAll() {
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		List<User> useri = new ArrayList<>();
		try (PreparedStatement preStmt = con.prepareStatement("select * from Useri")) {
			try (ResultSet result = preStmt.executeQuery()) {
				while (result.next()) {
					int id = result.getInt("id");
					String username = result.getString("username");
					String password = result.getString("password");
					User user = new User();
					user.setUsername(username);
					user.setPassword(password);
					user.setId(id);
					useri.add(user);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			System.err.println("Error DB " + e);
		}
		logger.traceExit(useri);
		return useri;
	}


	@Override
	public User findUser(String username, String password) {
		password = Crypt.encrypt(password);
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		User userGasit = new User();
		try (PreparedStatement preStmt = con.prepareStatement("select * from Useri WHERE username=? AND password=?")){
			preStmt.setString(1,username);
			preStmt.setString(2,password);
			try(ResultSet result = preStmt.executeQuery()){
				while (result.next()) {
					int id = result.getInt("id");
					String usernameGasit = result.getString("username");
					String passwordGasit = result.getString("password");

					userGasit.setUsername(usernameGasit);
					userGasit.setPassword(passwordGasit);
					userGasit.setId(id);
				}
			}
		}catch (SQLException e){
			logger.error(e);
			System.err.println("Error DB " + e);
		}

		logger.traceExit(userGasit);
		return userGasit;
	}
}
