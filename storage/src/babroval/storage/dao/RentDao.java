package babroval.storage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
			ps.setBigDecimal(4, ob.getSum());
			ps.setString(5, ob.getInfo());
			
			ps.execute();

		} catch (Exception e) {
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
			ps.setBigDecimal(4, ob.getSum());
			ps.setString(5, ob.getInfo());
			
			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void updateInfo(Rent ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set info=? " + " where rent_id = " + ob.getRent_id())) {
			
			ps.setString(1, ob.getInfo());
			
			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Rent loadRentLastPaidByStorageNumber(String number) {
		
		Rent rent = new Rent();
		
		try(Connection cn = ConnectionPool.getPool().getConnection();
    			Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
					    "SELECT rent.rent_id, rent.storage_id, rent.date,"
					    		+ " MAX(rent.quarter_paid), rent.sum, rent.info"
			   	   	 + " FROM rent, storage"
					 + " WHERE storage.storage_number='" + number + "'"
					 + " AND rent.storage_id=storage.storage_id")) {
					 
			while (rs.next()) {
				rent.setRent_id(Integer.valueOf(rs.getString(1)));
				rent.setStorage_id(Integer.valueOf(rs.getString(2)));
				rent.setDate(Date.valueOf(rs.getString(3)));
				rent.setQuarter_paid(Date.valueOf(rs.getString(4)));
				rent.setSum(new BigDecimal(rs.getString(5)));
				rent.setInfo(rs.getString(6));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return rent;
	}
}
