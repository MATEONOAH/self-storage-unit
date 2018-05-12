package babroval.storage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import babroval.storage.entity.Electric;
import babroval.storage.frames.TableStorage;
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

		} catch (Exception e) {
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

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Electric loadElectricLastPaidByStorageNumber(String number) {
		
		Electric electric = new Electric();
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT f.electric_id, f.storage_id, f.date, f.tariff, f.meter_paid, f.sum, f.info"
					 + " FROM storage, electric AS f"
					 		+ " INNER JOIN (SELECT storage_id, MAX(meter_paid) AS maxmeter"
					 				+ " FROM electric"
					 				+ " GROUP BY storage_id) AS temp"
					 		+ " ON f.storage_id = temp.storage_id"
					 		+ " AND f.meter_paid = temp.maxmeter"
					 + " WHERE storage.storage_number ='" + number + "'"
					 + " AND f.storage_id = storage.storage_id")) {

				while (rs.next()) {
					electric.setElectric_id(Integer.valueOf(rs.getString(1)));
					electric.setStorage_id(Integer.valueOf(rs.getString(2)));
					electric.setDate(Date.valueOf(rs.getString(3)));
					electric.setTariff(new BigDecimal(rs.getString(4)));
					electric.setMeter_paid(Integer.valueOf(rs.getString(5)));
					electric.setSum(new BigDecimal(rs.getString(6)));
					electric.setInfo(rs.getString(7));
				}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return electric;
	}

	public TableStorage loadElectricTable() {
		
		TableStorage table = new TableStorage();
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT electric.date, storage.storage_number, electric.tariff, electric.meter_paid, electric.sum, electric.info"
						+ " FROM storage, electric" + " WHERE storage.storage_id=electric.storage_id"
						+ " AND electric.date!=0 ORDER BY electric.electric_id DESC")) {

			table = new TableStorage(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return table;
	}

	public TableStorage loadElectricEditTable() {
		
		TableStorage table = new TableStorage();
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT electric.electric_id, storage.storage_number, electric.date,"
						+ " electric.tariff, electric.meter_paid, electric.sum, electric.info" + " FROM electric, storage"
						+ " WHERE electric.storage_id=storage.storage_id" + " ORDER BY electric.electric_id DESC")) {

			table = new TableStorage(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return table;		
	}
	
}
