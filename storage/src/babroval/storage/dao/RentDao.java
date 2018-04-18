package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Rent;
import babroval.storage.mysql.ConnectionPool;


public class RentDao implements Dao<Rent> {

	public RentDao() {
	}

	@Override
	public void insert(Rent ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("insert into "
						+ ob.getClass().getSimpleName()
						+ " (storage_id, date, quarter_paid, sum, info) values (?,?,?,?,?)")) {

			ps.setInt(1, ob.getStorage_id());
			ps.setDate(2, ob.getDate());
			ps.setDate(3, ob.getQuarter_paid());	
			ps.setInt(4, ob.getSum());
			ps.setString(5, ob.getInfo());
			
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Rent ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set storage_id=?, date=?, quarter_paid=?, sum=?, info=? "
						+ " where rent_id = " + ob.getRent_id())) {

			ps.setInt(1, ob.getStorage_id());
			ps.setDate(2, ob.getDate());
			ps.setDate(3, ob.getQuarter_paid());
			ps.setInt(4, ob.getSum());
			ps.setString(8, ob.getInfo());
			
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateInfo(Rent ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set info=? " + " where rent_id = " + ob.getRent_id())) {
			
			ps.setString(1, ob.getInfo());
			
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
