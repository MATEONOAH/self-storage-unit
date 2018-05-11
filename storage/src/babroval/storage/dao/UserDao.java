package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import babroval.storage.entity.User;
import babroval.storage.frames.TableStorage;
import babroval.storage.mysql.ConnectionPool;

public class UserDao implements Dao<User> {

	public UserDao() {
	}

	@Override
	public void insert(User ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement(
						"insert into " + ob.getClass().getSimpleName() + " (name, info)" + " values (?,?)")) {

			ps.setString(1, ob.getName());
			ps.setString(2, ob.getInfo());
			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set name=?, info=?" + " where user_id = '" + ob.getUser_id() + "'")) {

			ps.setString(1, ob.getName());
			ps.setString(2, ob.getInfo());
			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User loadUserByName(String name) {

		User user = new User();

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT user.user_id, user.name, user.info" + " FROM user"
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

	public String loadUserNameByStorageNumber(String number) {

		String userName = "";
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT user.name" + " FROM user, storage"
						+ " WHERE storage.user_id = user.user_id" + " AND storage.storage_number = '" + number + "'")) {

			while (rs.next()) {
				userName = rs.getString(1);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return userName;
	}

	public List<String> loadAllUsersNames() {

		List<String> allUsersNames = new ArrayList<String>();

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT user.name" + " FROM user" + " WHERE user.user_id!=1" + " ORDER BY user.name")) {

			while (rs.next()) {
				allUsersNames.add(rs.getString(1));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return allUsersNames;
	}

	public Integer loadUserIdByName(String name) {
		
		Integer userId = new Integer(0);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT user.user_id" + " FROM user"
						+ " WHERE user.name = '" + name + "'")) {

			while (rs.next()) {
				userId = Integer.valueOf(rs.getString(1));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return userId;
	}

	public TableStorage loadUserTable() {
		
		TableStorage table;
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage.storage_number, user.name, user.info" + " FROM storage, user"
						+ " WHERE storage.user_id=user.user_id"
						+ " AND storage.storage_number!=0 ORDER BY storage.storage_number")) {

			table = new TableStorage(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return table;
	}

	public TableStorage loadSortUserTable() {
		
		TableStorage table;
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage.storage_number, user.name, user.info" + " FROM storage, user"
						+ " WHERE storage.user_id=user.user_id" + " AND storage.storage_number!=0"
						+ " ORDER BY user.name")) {

			table = new TableStorage(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return table;
	}
	
}
