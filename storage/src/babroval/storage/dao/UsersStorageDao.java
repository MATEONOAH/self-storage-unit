package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Users;
import babroval.storage.mysql.ConnectionPool;

public class UsersStorageDao implements InterfaceStorageDao<Users> {

	public UsersStorageDao() {
	}

	@Override
	public void insert(Users ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement(
						"insert into " + ob.getClass().getSimpleName() 
						+ " (number_storage, name, person_info,"
						+ " quarter1, quarter2, quarter3, quarter4, year)"
						+ " values (?,?,?,?,?,?,?,?)")) {

			ps.setString(1, ob.getNumber_storage());
			ps.setString(2, ob.getName());
			ps.setString(3, ob.getPerson_info());
			ps.setString(4, ob.getQuarter1());
			ps.setString(5, ob.getQuarter2());
			ps.setString(6, ob.getQuarter3());
			ps.setString(7, ob.getQuarter4());
			ps.setString(8, ob.getYear());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Users ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement(
						"update " + ob.getClass().getSimpleName() 
						+ " set number_storage=?, name=?, person_info=?,"
						+ " quarter1=?, quarter2=?, quarter3=?, quarter4=?, year=?"
						+ " where storage_id = "
						+ ob.getStorage_id())) {
			
			ps.setString(1, ob.getNumber_storage());
			ps.setString(2, ob.getName());
			ps.setString(3, ob.getPerson_info());
			ps.setString(4, ob.getQuarter1());
			ps.setString(5, ob.getQuarter2());
			ps.setString(6, ob.getQuarter3());
			ps.setString(7, ob.getQuarter4());
			ps.setString(8, ob.getYear());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateQuarters(Users ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " 
						+ ob.getClass().getSimpleName()
						+ " set quarter1=?, quarter2=?, quarter3=?, quarter4=?,"
						+ " year=?" + " where storage_id = "
						+ ob.getStorage_id())) {
			
			ps.setString(1, ob.getQuarter1());
			ps.setString(2, ob.getQuarter2());
			ps.setString(3, ob.getQuarter3());
			ps.setString(4, ob.getQuarter4());
			ps.setString(5, ob.getYear());
			ps.execute();
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteQuarters(Users ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " 
						+ ob.getClass().getSimpleName()
						+ " set quarter1=?, quarter2=?, quarter3=?, quarter4=?" 
						+ " where storage_id = "
						+ ob.getStorage_id())) {
			
			ps.setString(1, ob.getQuarter1());
			ps.setString(2, ob.getQuarter2());
			ps.setString(3, ob.getQuarter3());
			ps.setString(4, ob.getQuarter4());
			ps.execute();
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
