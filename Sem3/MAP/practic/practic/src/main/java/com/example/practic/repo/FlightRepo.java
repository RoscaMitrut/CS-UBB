package com.example.practic.repo;

import com.example.practic.domain.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightRepo implements Repository<Flight>{
	private String url;
	private String username;
	private String password;

	public FlightRepo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public List<Flight> getAll() {
		List<Flight> all = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("SELECT * from \"zboruri\"");
			 ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Long id =resultSet.getLong("id");
				String from = resultSet.getString("dela");
				String to = resultSet.getString("panala");
				LocalDateTime departure =resultSet.getTimestamp("departuretime").toLocalDateTime();
				LocalDateTime landing =resultSet.getTimestamp("landingtime").toLocalDateTime();
				Integer seats =resultSet.getInt("seats");
				Flight fl=new Flight(id,from,to,departure,landing,seats);
				all.add(fl);
			}
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return all;
	}
}
