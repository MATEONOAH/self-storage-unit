package babroval.storage.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class TransToMysql {

	private Connection cn;
	private Statement st;
	private ResultSet rs;

	public Connection getCn() {
		return cn;
	}

	public TransToMysql(String url, String login, String pass) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		cn = (Connection) DriverManager.getConnection(url, login, pass);
		st = (Statement) cn.createStatement();

	}

	public void update(String sql) throws Exception {
		st.executeUpdate(sql);
	}

	public ResultSet query(String sql) throws Exception {
		rs = st.executeQuery(sql);
		return rs;
	}

	// disconnect from Data Base
	public void close() throws Exception {
		st.close();
		cn.close();
	}

	public void showResultSet(ResultSet rs) throws Exception {
		ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			System.out.print(rsmd.getColumnName(i) + "\t");
		}
		while (rs.next()) {
			System.out.println("\t");
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.print(rs.getString(i) + "\t");
			}
		}
	}
}
