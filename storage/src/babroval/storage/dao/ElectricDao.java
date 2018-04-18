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
						+ " (storage_id, date, tariff, meter_paid, sum, info) values (?,?,?,?,?,?)")) {

			ps.setInt(1, ob.getStorage_id());
			ps.setDate(2, ob.getDate());
			ps.setBigDecimal(3, ob.getTariff());
			ps.setInt(4, ob.getMeter_paid());
			ps.setBigDecimal(5, ob.getSum());
			ps.setString(6, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Electric ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set storage_id=?, date=?, tariff=?, meter_paid=?, sum=?, info=?"
						+ " where electric_id = " + ob.getElectric_id())) {

			ps.setInt(1, ob.getStorage_id());
			ps.setDate(2, ob.getDate());
			ps.setBigDecimal(3, ob.getTariff());
			ps.setInt(4, ob.getMeter_paid());
			ps.setBigDecimal(5, ob.getSum());
			ps.setString(6, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
