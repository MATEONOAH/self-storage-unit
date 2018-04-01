package babroval.storage.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {

	
	public static final String NAME_DB = "storage";

	private static final String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";

	private String url;
	private String user;
	private String password;

	static {
		try {
			Class.forName(DB_DRIVER_NAME);
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private ConnectionPool() {
	}

	private static class PoolHolder {
		private static final ConnectionPool POOL = new ConnectionPool();
	}

	public static ConnectionPool getPool() {
		return PoolHolder.POOL;
	}

	public Connection getConnection(String url, String user, String password) {

		this.url = url;
		this.user = user;
		this.password = password;

		try {
			Connection cn = DriverManager.getConnection(url, user, password);
			return cn;
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() {
		try {
			Connection cn = DriverManager.getConnection(url + "/" + NAME_DB, user, password);
			return cn;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
