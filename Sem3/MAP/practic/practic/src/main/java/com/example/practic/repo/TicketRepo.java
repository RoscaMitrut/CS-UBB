package com.example.practic.repo;

import com.example.practic.domain.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketRepo implements Repository<Ticket>{
	private String url;
	private String username;
	private String password;

	public TicketRepo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public List<Ticket> getAll() {
		List<Ticket> all = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("SELECT * from \"tichete\"");
			 ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				Long flightId=resultSet.getLong("flightid");
				String usrname = resultSet.getString("username");
				LocalDateTime purchaseTime=resultSet.getTimestamp("purchasetime").toLocalDateTime();

				Ticket tckt=new Ticket(id,usrname,flightId,purchaseTime);
				all.add(tckt);
			}
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return all;
	}

	public void adauga(Ticket ticket) {
		String sql = "insert into \"tichete\" (id,username,flightid,purchasetime) values (?,?,?,?)";

		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setLong(1,ticket.getId());
			ps.setString(2, ticket.getUsername());
			ps.setLong(3, ticket.getFlightId());
			ps.setTimestamp(4,Timestamp.valueOf(ticket.getPurchaseTime()));

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
