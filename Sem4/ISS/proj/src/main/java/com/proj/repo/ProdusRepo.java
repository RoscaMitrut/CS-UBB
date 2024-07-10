package com.proj.repo;

import com.proj.domain.Angajat;
import com.proj.domain.Produs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdusRepo implements IRepository<Produs> {
	private String url;
	private String username;
	private String password;

	public ProdusRepo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	@Override
	public List<Produs> getAll() {
		List<Produs> all = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url,username,password);
			 PreparedStatement statement = connection.prepareStatement("Select * from \"Produse\"");
			 ResultSet resultSet = statement.executeQuery()){

			while (resultSet.next()){
				Integer id = resultSet.getInt("id");
				String numeProdus = resultSet.getString("numeProdus");
				Double pret = resultSet.getDouble("pret");
				Integer cantitate = resultSet.getInt("cantitate");

				Produs produs = new Produs(numeProdus,pret,cantitate);
				produs.setId(id);
				all.add(produs);
			}
			return all;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return all;
	}

	public void add(Produs prod) {
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("Insert INTO \"Produse\" (\"numeProdus\",pret,cantitate) values (?,?,?)")) {
			statement.setString(1,prod.getNumeProdus());
			statement.setDouble(2,prod.getPret());
			statement.setInt(3,prod.getCantitate());
			int result = statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error DB checkStoc "+e);
		}
	}

		public Integer checkStoc(Integer id){
		Integer stoc =0;
		try(Connection connection = DriverManager.getConnection(url,username,password);
			PreparedStatement statement = connection.prepareStatement("SELECT cantitate FROM \"Produse\" WHERE id =?")){
				statement.setInt(1,id);
				ResultSet result = statement.executeQuery();
				if(result.next()){
					stoc = result.getInt("cantitate");
				}
		} catch (SQLException e) {
			System.err.println("Error DB checkStoc "+e);
		}
		return stoc;
	}
	public void delete(Integer id) {
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("Delete from \"Produse\" where id=?")) {
			statement.setInt(1,id);
			int result = statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error DB "+ e);
		}
	}

	public void update(Integer id,String nume_nou,Double pret_nou,Integer cantitate_noua) {
		try (Connection connection = DriverManager.getConnection(url, username, password);
			 PreparedStatement statement = connection.prepareStatement("Update \"Produse\" set \"numeProdus\"=?,pret=?,cantitate=? where id=?")) {
			statement.setString(1, nume_nou);
			statement.setDouble(2, pret_nou);
			statement.setInt(3, cantitate_noua);
			statement.setInt(4, id);
			int result = statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error DB " + e);
		}
	}
		public void updateCantitate(int id,int cantit_dorita){
		try(Connection connection = DriverManager.getConnection(url,username,password);
			PreparedStatement statement = connection.prepareStatement("Update \"Produse\" SET cantitate=cantitate-? WHERE id=?")){
				statement.setInt(1,cantit_dorita);
				statement.setInt(2,id);
				int result = statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error DB updateCantitate " + e);
		}
	}
}
