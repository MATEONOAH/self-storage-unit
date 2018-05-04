package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import babroval.storage.entity.User;
import babroval.storage.mysql.ConnectionPool;


public class UserDao implements Dao<User> {

	public UserDao() {
	}

	public User loadUserByName(String name) {
		
		User user = new User();
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
					"SELECT user.user_id, user.name, user.info"
				 + " FROM user"
				 + " WHERE user.name = '" + name + "'")) {

			while (rs.next()) {
				user.setUser_id(Integer.valueOf(rs.getString(1)));
				user.setName(rs.getString(2));
				user.setInfo(rs.getString(3));
			}
	
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return user;
	}
	
	public User loadUserByStorageNumber(String number) {
		
		User user = new User();
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
					"SELECT user.user_id, user.name, user.info"
				 + " FROM user, storage"
				 + " WHERE storage.user_id = user.user_id"
				 + " AND storage.storage_number = '" + number + "'")) {

			while (rs.next()) {
				user.setUser_id(Integer.valueOf(rs.getString(1)));
				user.setName(rs.getString(2));
				user.setInfo(rs.getString(3));
			}
	
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return user;
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
