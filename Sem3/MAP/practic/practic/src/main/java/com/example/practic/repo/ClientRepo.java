package com.example.practic.repo;

import com.example.practic.domain.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepo implements Repository<Client> {
	private String url;
	private String username;
	private String password;

	public ClientRepo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public List<Client> getAll() {
		List<Client> all = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("SELECT * from \"clienti\"");
			 ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String username = resultSet.getString("username");
				String name = resultSet.getString("name");

				Client client=new Client(id,username,name);
				all.add(client);
			}
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return all;
	}
}
