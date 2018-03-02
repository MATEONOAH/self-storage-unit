package babroval.storage.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Orders;
import babroval.storage.mysql.TransToMysql;

public class OrdersStorageDao implements InterfaceStorageDao<Orders> {
	
	private TransToMysql db;

	public OrdersStorageDao(TransToMysql db) {
		this.db = db;
	}

	@Override
	public void insert(Orders ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement("insert into " 
	    + ob.getClass().getSimpleName()
		+ " (storage_id, date, summ, quarter1, quarter2, quarter3, quarter4, year, info) values (?,?,?,?,?,?,?,?,?)");
		ps.setInt(1, ob.getStorage_id());
		ps.setString(2, ob.getDate());
		ps.setInt(3, ob.getSumm());
		ps.setString(4, ob.getQuarter1());
		ps.setString(5, ob.getQuarter2());
		ps.setString(6, ob.getQuarter3());
		ps.setString(7, ob.getQuarter4());
		ps.setString(8, ob.getYear());
		ps.setString(9, ob.getInfo());
		ps.execute();
	}

	@Override
	public void update(Orders ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement("update " 
	                    + ob.getClass().getSimpleName()
						+ " set storage_id=?, date=?, summ=?, quarter1=?, quarter2=?, quarter3=?, quarter4=?, info=? "
						+ " where order_id = " 
						+ ob.getOrder_id());
		ps.setInt(1, ob.getStorage_id());
		ps.setString(2, ob.getDate());
		ps.setInt(3, ob.getSumm());
		ps.setString(4, ob.getQuarter1());
		ps.setString(5, ob.getQuarter2());
		ps.setString(6, ob.getQuarter3());
		ps.setString(7, ob.getQuarter4());
		ps.setString(8, ob.getInfo());
		ps.execute();
	}
	
	public void updateInfo(Orders ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement("update " 
	                    + ob.getClass().getSimpleName()
						+ " set info=? "
						+ " where order_id = " 
						+ ob.getOrder_id());
		ps.setString(1, ob.getInfo());
		ps.execute();
	}

}
