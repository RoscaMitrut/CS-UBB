package project.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.model.Excursie;
import project.persistence.ExcursieRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class ExcursieDBRepository implements ExcursieRepository {
	private JdbcUtils dbUtils;
	private static final Logger logger= LogManager.getLogger();

	@Autowired
	public ExcursieDBRepository(Properties props) {
		logger.info("Initializing ExcursieDBRepository with properties: {} ",props);
		dbUtils=new JdbcUtils(props);
	}

	@Override
	public void add(Excursie elem) {
		logger.traceEntry("saving task {}",elem);
		Connection con = dbUtils.getConnection();
		try (PreparedStatement preStmt = con.prepareStatement("insert into Excursii (obiectivVizitat, oraPlecare, firmaTransport, pret, locuriDisponibile) values (?,?,?,?,?)")) {
			preStmt.setString(1, elem.getObiectivVizitat());
			//preStmt.setTimestamp(2, Timestamp.valueOf(elem.getOraPlecare()));
			preStmt.setString(2, String.valueOf(Timestamp.valueOf(elem.getOraPlecare())));// DACA NU MERGE ^
			preStmt.setString(3, elem.getFirmaTransport());
			preStmt.setDouble(4, elem.getPret());
			preStmt.setInt(5,elem.getLocuriDisponibile());
			int result = preStmt.executeUpdate();
			logger.trace("Saved {} instances", result);
		} catch (SQLException ex) {
			logger.error(ex);
			System.err.println("Error DB " + ex);
		}
		logger.traceExit();
	}
	@Override
	public void update(Integer integer, Excursie elem) {
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		try(PreparedStatement preStmt = con.prepareStatement("update Excursii set obiectivVizitat = ?, oraPlecare = ?,firmaTransport = ?,pret = ?,locuriDisponibile=?  WHERE id = ?")){
			preStmt.setString(1, elem.getObiectivVizitat());
			//preStmt.setTimestamp(2, Timestamp.valueOf(elem.getOraPlecare()));
			preStmt.setString(2, String.valueOf(Timestamp.valueOf(elem.getOraPlecare())));// DACA NU MERGE ^
			preStmt.setString(3, elem.getFirmaTransport());
			preStmt.setDouble(4, elem.getPret());
			preStmt.setInt(5,elem.getLocuriDisponibile());
			preStmt.setInt(6,integer);
			int result = preStmt.executeUpdate();
			logger.trace(result);
		}catch (SQLException ex) {
			logger.error(ex);
			System.err.println("Error DB " + ex);
		}
		logger.traceExit();
	}

	@Override
	public Iterable<Excursie> findAll() {
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		List<Excursie> excursii = new ArrayList<>();
		try (PreparedStatement preStmt = con.prepareStatement("select * from Excursii")) {
			try (ResultSet result = preStmt.executeQuery()) {
				while (result.next()) {
					int id = result.getInt("id");
					String obiectivVizitat = result.getString("obiectivVizitat");
					LocalDateTime oraPlecare = result.getTimestamp("oraPlecare").toLocalDateTime();
					String firmaTransport = result.getString("firmaTransport");
					Double pret = result.getDouble("pret");
					int locuriDisponibile = result.getInt("locuriDisponibile");
					Excursie excursie = new Excursie(obiectivVizitat,oraPlecare,firmaTransport,pret,locuriDisponibile);
					excursie.setId(id);
					excursii.add(excursie);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			System.err.println("Error DB " + e);
		}
		logger.traceExit(excursii);
		return excursii;
	}





	@Override
	public int checkLocuriDisponibile(int id){
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		int nrLocuri = 0;
		try(PreparedStatement preStmt = con.prepareStatement("SELECT locuriDisponibile FROM Excursii Where id=?")) {
			preStmt.setInt(1,id);
			ResultSet result = preStmt.executeQuery();
			nrLocuri = result.getInt("locuriDisponibile");

		}catch (SQLException e) {
			logger.error(e);
			System.err.println("Error DB " + e);
		}
		logger.traceExit(nrLocuri);
		return nrLocuri;
	}

	@Override
	public void updateLocuriDisponibile(int id, int numarLocuriDorite) {
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		try(PreparedStatement preStmt = con.prepareStatement("update Excursii SET locuriDisponibile=locuriDisponibile-? WHERE id=?")){
			preStmt.setInt(1,numarLocuriDorite);
			preStmt.setInt(2,id);
			int result = preStmt.executeUpdate();
			logger.trace(result);
		}catch (SQLException ex){
			logger.error(ex);
			System.err.println("Error DB "+ex);
		}
	}

	@Override
	public Iterable<Excursie> findExcursiiLaLocSiOra(String obiectiv,LocalDateTime oraMin,LocalDateTime oraMax) {
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		List<Excursie> excursii = new ArrayList<>();
		try (PreparedStatement preStmt = con.prepareStatement("select * from Excursii WHERE obiectivVizitat=? AND oraPlecare BETWEEN ? AND ?")) {
			preStmt.setString(1, obiectiv);
			preStmt.setString(2, Timestamp.valueOf(oraMin).toString());
			preStmt.setString(3, Timestamp.valueOf(oraMax).toString());
			try (ResultSet result = preStmt.executeQuery()) {
				while (result.next()) {
					int id = result.getInt("id");
					String obiectivVizitat = result.getString("obiectivVizitat");
					LocalDateTime oraPlecare = result.getTimestamp("oraPlecare").toLocalDateTime();
					String firmaTransport = result.getString("firmaTransport");
					Double pret = result.getDouble("pret");
					int locuriDisponibile = result.getInt("locuriDisponibile");
					Excursie excursie = new Excursie(obiectivVizitat,oraPlecare,firmaTransport,pret,locuriDisponibile);
					excursie.setId(id);
					excursii.add(excursie);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			System.err.println("Error DB " + e);
		}
		logger.traceExit(excursii);
		return excursii;
	}

	public void delete(Integer id){
		logger.traceEntry();
		Connection con = dbUtils.getConnection();
		try(PreparedStatement preStmt = con.prepareStatement("DELETE FROM Excursii WHERE id=?")) {
			preStmt.setInt(1,id);
			int result = preStmt.executeUpdate();
			logger.trace(result);
		}catch (SQLException e){
			logger.error(e);
			System.err.println("Error DB" + e);

		}
		logger.traceExit();
	}
}
