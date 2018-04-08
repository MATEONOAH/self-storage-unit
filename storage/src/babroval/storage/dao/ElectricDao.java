package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Electric;
import babroval.storage.mysql.ConnectionPool;


public class ElectricDao implements Dao<Electric> {

	public ElectricDao() {
	}
	
	@Override
	public void insert(Electric ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("insert into "
						+ ob.getClass().getSimpleName()
						+ " (storage_id, date, meter_paid, summ, info) values (?,?,?,?,?)")) {

			ps.setInt(1, ob.getStorage_id());
			ps.setDate(2, ob.getDate());
			ps.setInt(3, ob.getMeter_paid());
			ps.setInt(4, ob.getSumm());
			ps.setString(5, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Electric ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set storage_id=?, date=?, meter_paid=?, summ=?, info=?"
						+ " where electric_id = " + ob.getElectric_id())) {

			ps.setInt(1, ob.getStorage_id());
			ps.setDate(2, ob.getDate());
			ps.setInt(3, ob.getMeter_paid());
			ps.setInt(4, ob.getSumm());
			ps.setString(5, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
