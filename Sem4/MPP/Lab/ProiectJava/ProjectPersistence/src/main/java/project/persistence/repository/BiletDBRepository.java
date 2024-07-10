package project.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.Bilet;
import project.persistence.BiletRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BiletDBRepository implements BiletRepository {
	private JdbcUtils dbUtils;
	private static final Logger logger= LogManager.getLogger();
	public BiletDBRepository(Properties props) {
		logger.info("Initializing BiletDBRepository with properties: {} ",props);
		dbUtils=new JdbcUtils(props);
	}
	@Override
	public void add(Bilet elem) {
		logger.traceEntry("saving task {}",elem);
		Connection con = dbUtils.getConnection();
		//
		try (PreparedStatement preStmt = con.prepareStatement("insert into Bilete (numarPersoane, numeClient, numarTelefonClient, idExcursie) values (?,?,?,?)")) {
			preStmt.setInt(1, elem.getNumarPersoane());
			preStmt.setString(2, elem.getNumeClient());
			preStmt.setInt(3, elem.getNumarTelefonClient());
			preStmt.setInt(4, elem.getIdExcursie());
			int result = preStmt.executeUpdate();
			logger.trace("Saved {} instances", result);
		} catch (SQLException ex) {
			logger.error(ex);
			System.err.println("Error DB " + ex);
		}
		logger.traceExit();
	}

	@Override
	public void update(Integer integer, Bilet elem) {
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		try(PreparedStatement preStmt = con.prepareStatement("update Bilete set numarPersoane = ?, numeClient = ?,numarTelefonClient = ?,idExcursie = ?  WHERE id = ?")){
			preStmt.setInt(1, elem.getNumarPersoane());
			preStmt.setString(2, elem.getNumeClient());
			preStmt.setInt(3, elem.getNumarTelefonClient());
			preStmt.setInt(4, elem.getIdExcursie());
			preStmt.setInt(5,integer);
			int result = preStmt.executeUpdate();
			logger.trace(result);
		}catch (SQLException ex) {
			logger.error(ex);
			System.err.println("Error DB " + ex);
		}
		logger.traceExit();
	}

	@Override
	public Iterable<Bilet> findAll() {
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		List<Bilet> bilete = new ArrayList<>();
		try (PreparedStatement preStmt = con.prepareStatement("select * from Bilete")) {
			try (ResultSet result = preStmt.executeQuery()) {
				while (result.next()) {
					int id = result.getInt("id");
					int nrPers = result.getInt("numarPersoane");
					String numeClient = result.getString("numeClient");
					int nrTelClient = result.getInt("numarTelefonClient");
					int idExcursie = result.getInt("idExcursie");
					Bilet bilet = new Bilet(nrPers,numeClient,nrTelClient,idExcursie);
					bilet.setId(id);
					bilete.add(bilet);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			System.err.println("Error DB " + e);
		}
		logger.traceExit(bilete);
		return bilete;
	}
}
