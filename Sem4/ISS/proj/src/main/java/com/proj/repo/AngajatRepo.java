package com.proj.repo;
import com.proj.domain.Angajat;
import com.proj.domain.Produs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AngajatRepo implements IRepository<Angajat> {
	private String url;
	private String username;
	private String password;

	public AngajatRepo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public List<Angajat> getAll() {
		List<Angajat> all = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url,username,password);
		PreparedStatement statement = connection.prepareStatement("Select * from \"Angajati\"");
		ResultSet resultSet = statement.executeQuery()){

			while (resultSet.next()){
				Integer id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String nume = resultSet.getString("nume");

				Angajat angajat = new Angajat(username,password,nume);
				angajat.setId(id);
				all.add(angajat);
			}
			return all;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return all;
	}

	public void add(Angajat user) {
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("Insert INTO \"Angajati\" (username,password,nume) values (?,?,?)")) {
			statement.setString(1,user.getUsername());
			statement.setString(2,user.getPassword());
			statement.setString(3,user.getNume());
			int result = statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error DB "+e);
		}
	}

	public void update(Integer id,String username_nou,String password_nou,String nume_nou) {
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("Update \"Angajati\" set username=?,password=?,nume=? where id=?")) {
			statement.setString(1, username_nou);
			statement.setString(2, password_nou);
			statement.setString(3, nume_nou);
			statement.setInt(4, id);
			int result = statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error DB " + e);
		}
	}

	public void delete(Integer id) {
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("Delete from \"Angajati\" where id=?")) {
			statement.setInt(1,id);
			int result = statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error DB "+ e);
		}
	}
}
