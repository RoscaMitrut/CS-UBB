package com.proj.repo;

import com.proj.domain.Angajat;
import com.proj.domain.Comanda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComandaRepo implements IRepository<Comanda>{
	private String url;
	private String username;
	private String password;

	public ComandaRepo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public List<Comanda> getAll() {
		List<Comanda> all = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url,username,password);
			 PreparedStatement statement = connection.prepareStatement("Select * from \"Comenzi\"");
			 ResultSet resultSet = statement.executeQuery()){

			while (resultSet.next()){
				Integer id = resultSet.getInt("id");
				Integer idprod = resultSet.getInt("idProdus");
				Integer cantitate = resultSet.getInt("cantitate");
				Double pretBucata = resultSet.getDouble("pretBucata");

				Comanda comanda = new Comanda(idprod,cantitate,pretBucata);
				comanda.setId(id);
				all.add(comanda);
			}
			return all;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return all;
	}

	public void add(Comanda comanda){
		try (Connection connection = DriverManager.getConnection(url,username,password);
			 PreparedStatement preStmt = connection.prepareStatement("INSERT INTO \"Comenzi\" (\"idProdus\",cantitate,\"pretBucata\") values (?,?,?)")) {
			preStmt.setInt(1,comanda.getIdProdus());
			preStmt.setInt(2,comanda.getCantitate());
			preStmt.setDouble(3,comanda.getPretBucata());
			int result = preStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error DB "+e);
		}
	}
}
