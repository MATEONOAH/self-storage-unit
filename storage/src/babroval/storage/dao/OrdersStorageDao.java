package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Orders;
import babroval.storage.mysql.ConnectionPool;

public class OrdersStorageDao implements InterfaceStorageDao<Orders> {

	public OrdersStorageDao() {
	}

	@Override
	public void insert(Orders ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("insert into "
						+ ob.getClass().getSimpleName()
						+ " (storage_id, date, summ, quarter1, quarter2, quarter3, quarter4, year, info) values (?,?,?,?,?,?,?,?,?)")) {

			ps.setInt(1, ob.getStorage_id());
			ps.setDate(2, ob.getDate());
			ps.setInt(3, ob.getSumm());
			ps.setString(4, ob.getQuarter1());
			ps.setString(5, ob.getQuarter2());
			ps.setString(6, ob.getQuarter3());
			ps.setString(7, ob.getQuarter4());
			ps.setString(8, ob.getYear());
			ps.setString(9, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Orders ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set storage_id=?, date=?, summ=?, quarter1=?, quarter2=?, quarter3=?, quarter4=?, info=? "
						+ " where order_id = " + ob.getOrder_id())) {

			ps.setInt(1, ob.getStorage_id());
			ps.setDate(2, ob.getDate());
			ps.setInt(3, ob.getSumm());
			ps.setString(4, ob.getQuarter1());
			ps.setString(5, ob.getQuarter2());
			ps.setString(6, ob.getQuarter3());
			ps.setString(7, ob.getQuarter4());
			ps.setString(8, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateInfo(Orders ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set info=? " + " where order_id = " + ob.getOrder_id())) {
			
			ps.setString(1, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
