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
						+ " (name, info)"
						+ " values (?,?)")) {

			ps.setString(1, ob.getName());
			ps.setString(2, ob.getInfo());
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
						+ " set name=?, info=?"
						+ " where user_id = '"+ ob.getUser_id() +"'")) {
			
			ps.setString(1, ob.getName());
			ps.setString(2, ob.getInfo());
			ps.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
