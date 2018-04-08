package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.User;
import babroval.storage.mysql.ConnectionPool;


public class UserDao implements Dao<User> {

	public UserDao() {
	}

	@Override
	public void insert(User ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement(
						"insert into " + ob.getClass().getSimpleName() 
						+ " (storage_id, name, info)"
						+ " values (?,?,?)")) {

			ps.setInt(1, ob.getStorage_id());
			ps.setString(2, ob.getName());
			ps.setString(3, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement(
						"update " + ob.getClass().getSimpleName() 
						+ " set storage_id=?, name=?, info=?"
						+ " where user_id = "
						+ ob.getUser_id())) {
			
			ps.setInt(1, ob.getStorage_id());
			ps.setString(2, ob.getName());
			ps.setString(3, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
