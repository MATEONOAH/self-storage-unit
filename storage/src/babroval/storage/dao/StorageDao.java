package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Storage;
import babroval.storage.mysql.ConnectionPool;


public class StorageDao implements Dao<Storage> {

	public StorageDao() {
	}
	
	@Override
	public void insert(Storage ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("insert into "
						+ ob.getClass().getSimpleName()
						+ " (number, info) values (?,?)")) {

			ps.setString(1, ob.getNumber());
			ps.setString(2, ob.getInfo());
			
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Storage ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update "
						+ ob.getClass().getSimpleName()
						+ " set number=?, info=? "
						+ " where storage_id = " + ob.getStorage_id())) {

			ps.setString(1, ob.getNumber());
			ps.setString(2, ob.getInfo());
			
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
