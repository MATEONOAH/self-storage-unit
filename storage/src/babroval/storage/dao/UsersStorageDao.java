package babroval.storage.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Users;
import babroval.storage.mysql.TransToMysql;

public class UsersStorageDao implements InterfaceStorageDao<Users> {

	private TransToMysql db;

	public UsersStorageDao(TransToMysql db) {
		this.db = db;
	}

	@Override
	public void insert(Users ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn()
				.prepareStatement("insert into " + ob.getClass()
						.getSimpleName()
				+ " (number_storage, name, person_info, quarter1, quarter2, quarter3, quarter4, year) values (?,?,?,?,?,?,?,?)");
		ps.setString(1, ob.getNumber_storage());
		ps.setString(2, ob.getName());
		ps.setString(3, ob.getPerson_info());
		ps.setString(4, ob.getQuarter1());
		ps.setString(5, ob.getQuarter2());
		ps.setString(6, ob.getQuarter3());
		ps.setString(7, ob.getQuarter4());
		ps.setString(8, ob.getYear());
		ps.execute();
	}

	@Override
	public void update(Users ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn()
				.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set number_storage=?, name=?, person_info=?, quarter1=?, quarter2=?, quarter3=?, quarter4=?, year=?"
						+ " where storage_id = " + ob.getStorage_id());
		ps.setString(1, ob.getNumber_storage());
		ps.setString(2, ob.getName());
		ps.setString(3, ob.getPerson_info());
		ps.setString(4, ob.getQuarter1());
		ps.setString(5, ob.getQuarter2());
		ps.setString(6, ob.getQuarter3());
		ps.setString(7, ob.getQuarter4());
		ps.setString(8, ob.getYear());
		ps.execute();
	}

	public void updateQuarters(Users ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn()
				.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set quarter1=?, quarter2=?, quarter3=?, quarter4=?, year=?" + " where storage_id = "
						+ ob.getStorage_id());
		ps.setString(1, ob.getQuarter1());
		ps.setString(2, ob.getQuarter2());
		ps.setString(3, ob.getQuarter3());
		ps.setString(4, ob.getQuarter4());
		ps.setString(5, ob.getYear());
		ps.execute();
	}
	
	public void deleteQuarters(Users ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn()
				.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set quarter1=?, quarter2=?, quarter3=?, quarter4=?" + " where storage_id = "
						+ ob.getStorage_id());
		ps.setString(1, ob.getQuarter1());
		ps.setString(2, ob.getQuarter2());
		ps.setString(3, ob.getQuarter3());
		ps.setString(4, ob.getQuarter4());
		ps.execute();
	}


}
