package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Electro;
import babroval.storage.mysql.ConnectionPool;

public class ElectroStorageDao implements InterfaceStorageDao<Electro> {

	public ElectroStorageDao() {
	}
	
	@Override
	public void insert(Electro ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("insert into "
						+ ob.getClass().getSimpleName()
						+ " (storage_id, date, last_num, new_num, kw_h, tariff, summ, info) values (?,?,?,?,?,?,?,?)")) {

			ps.setInt(1, ob.getStorage_id());
			ps.setString(2, ob.getDate());
			ps.setInt(3, ob.getLast_num());
			ps.setInt(4, ob.getNew_num());
			ps.setInt(5, ob.getKw_h());
			ps.setInt(6, ob.getTariff());
			ps.setInt(7, ob.getSumm());
			ps.setString(8, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Electro ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set storage_id=?, date=?, last_num=?, new_num=?, kw_h=?, tariff=?, summ=?, info=?"
						+ " where electro_id = " + ob.getElectro_id())) {

			ps.setInt(1, ob.getStorage_id());
			ps.setString(2, ob.getDate());
			ps.setInt(3, ob.getLast_num());
			ps.setInt(4, ob.getNew_num());
			ps.setInt(5, ob.getKw_h());
			ps.setInt(6, ob.getTariff());
			ps.setInt(7, ob.getSumm());
			ps.setString(8, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
