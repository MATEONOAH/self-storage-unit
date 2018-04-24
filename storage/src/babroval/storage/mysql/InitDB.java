package babroval.storage.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import babroval.storage.dao.ElectricDao;
import babroval.storage.dao.RentDao;
import babroval.storage.dao.StorageDao;
import babroval.storage.dao.UserDao;
import babroval.storage.entity.Electric;
import babroval.storage.entity.Rent;
import babroval.storage.entity.Storage;
import babroval.storage.entity.User;

public class InitDB {

	public static void createDB(String url, String user, String password) {

		try (Connection cn = ConnectionPool.getPool().getConnection(url, user, password);
				Statement st = cn.createStatement()) {

			st.executeUpdate("CREATE DATABASE " + ConnectionPool.NAME_DB);

			st.executeUpdate("USE " + ConnectionPool.NAME_DB);

			st.executeUpdate("CREATE TABLE storage (" 
							+ "storage_id INT PRIMARY KEY AUTO_INCREMENT,"
							+ " storage_number VARCHAR(50),"
							+ " info VARCHAR(100))");
			
			st.executeUpdate("CREATE TABLE user (" 
							+ "user_id INT PRIMARY KEY AUTO_INCREMENT,"
							+ " storage_id INT," 
							+ " name VARCHAR(50)," 
							+ " info VARCHAR(100),"
							+ " FOREIGN KEY(storage_id) REFERENCES storage(storage_id))");

			st.executeUpdate("CREATE TABLE rent (" 
							+ "rent_id INT PRIMARY KEY AUTO_INCREMENT," 
							+ " storage_id INT,"
							+ " date DATE," 
							+ " quarter_paid DATE," 
							+ " sum DECIMAL(10,2)," 
							+ " info VARCHAR(100),"
							+ " FOREIGN KEY(storage_id) REFERENCES storage(storage_id))");

			st.executeUpdate("CREATE TABLE electric (" 
							+ "electric_id INT PRIMARY KEY AUTO_INCREMENT," 
							+ " storage_id INT,"
							+ " date DATE," 
							+ " tariff DECIMAL(10,3),"
							+ " meter_paid INT,"
							+ " sum DECIMAL(10,2),"
							+ " info VARCHAR(100)," 
							+ " FOREIGN KEY(storage_id) REFERENCES storage(storage_id))");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		StorageDao daoStorage = new StorageDao();
		daoStorage.insert(new Storage("2", "double"));
		daoStorage.insert(new Storage("1a", "standard"));
		daoStorage.insert(new Storage("1b", "small"));
		
		UserDao daoUser = new UserDao();
		daoUser.insert(new User(3, "JANE ROE", "tel. 29-78-56-546, 200 E MAIN ST PHOENIX AZ 85123"));
		daoUser.insert(new User(2, "JOHN SMITH", "tel. 44-164-76-389, 795 E DRAGRAM TUCSON AZ 85705"));
		daoUser.insert(new User(1, "CHRIS NISWANDEE", "tel. 25-797-35-91, 300 BOYLSTON AVE E SEATTLE WA 98102"));

		RentDao daoRent = new RentDao();
		daoRent.insert(new Rent(1, stringToDate("18-11-2017", "dd-MM-yyyy"), stringToDate("01-01-2018", "dd-MM-yyyy"), BigDecimal.valueOf(20.00), "45325"));
		daoRent.insert(new Rent(3, stringToDate("19-11-2017", "dd-MM-yyyy"), stringToDate("01-04-2018", "dd-MM-yyyy"), BigDecimal.valueOf(40.00), "67567"));
		daoRent.insert(new Rent(1, stringToDate("20-11-2017", "dd-MM-yyyy"), stringToDate("01-04-2018", "dd-MM-yyyy"), BigDecimal.valueOf(20.00), "34556"));
		daoRent.insert(new Rent(3, stringToDate("29-11-2017", "dd-MM-yyyy"), stringToDate("01-07-2018", "dd-MM-yyyy"), BigDecimal.valueOf(20.00), "78574"));
		daoRent.insert(new Rent(2, stringToDate("30-11-2017", "dd-MM-yyyy"), stringToDate("01-10-2018", "dd-MM-yyyy"), BigDecimal.valueOf(60.00), "353465"));

		ElectricDao daoElectric = new ElectricDao();
		daoElectric.insert(new Electric(1, stringToDate("18-11-2017", "dd-MM-yyyy"), BigDecimal.valueOf(0.178), 45700, BigDecimal.valueOf(17.80), "56456"));
		daoElectric.insert(new Electric(3, stringToDate("19-11-2017", "dd-MM-yyyy"), BigDecimal.valueOf(0.178), 34500, BigDecimal.valueOf(26.70), "45763"));
		daoElectric.insert(new Electric(1, stringToDate("20-11-2017", "dd-MM-yyyy"), BigDecimal.valueOf(0.178), 45800, BigDecimal.valueOf(17.80), "74535"));
		daoElectric.insert(new Electric(3, stringToDate("01-12-2017", "dd-MM-yyyy"), BigDecimal.valueOf(0.170), 34650, BigDecimal.valueOf(25.50), "34567"));
		daoElectric.insert(new Electric(2, stringToDate("03-12-2017", "dd-MM-yyyy"), BigDecimal.valueOf(0.170), 44600, BigDecimal.valueOf(8.50), "73457"));
	}

	public static void deleteDB(String url, String user, String password) {

		try (Connection con = ConnectionPool.getPool().getConnection(url, user, password);
				Statement st = con.createStatement()) {

			st.executeUpdate("DROP DATABASE " + ConnectionPool.NAME_DB);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Date stringToDate(String dateStr, String dateFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date sqlDate;

		try {
			sqlDate = new Date(sdf.parse(dateStr).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return sqlDate;

	}
	
	public static void showResultSet(ResultSet rs) {
		ResultSetMetaData rsmd;
		try {
			rsmd = (ResultSetMetaData) rs.getMetaData();

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.print(rsmd.getColumnName(i) + "\t");
			}
			while (rs.next()) {
				System.out.println("\t");
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					System.out.print(rs.getString(i) + "\t");
				}
			}
			System.out.println();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}