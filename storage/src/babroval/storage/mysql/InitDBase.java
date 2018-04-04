package babroval.storage.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import babroval.storage.dao.ElectroStorageDao;
import babroval.storage.dao.OrdersStorageDao;
import babroval.storage.dao.UsersStorageDao;
import babroval.storage.entity.Electro;
import babroval.storage.entity.Orders;
import babroval.storage.entity.Users;

public class InitDBase {

	public static void createDB(String url, String user, String password) {
		
		try (Connection cn = ConnectionPool.getPool().getConnection(url,user,password);
				Statement st = cn.createStatement()) {
		
				st.executeUpdate("CREATE DATABASE " + ConnectionPool.NAME_DB);
		
				st.executeUpdate("USE " + ConnectionPool.NAME_DB);
				
				st.executeUpdate("CREATE TABLE users (" 
						+ "storage_id INT PRIMARY KEY AUTO_INCREMENT," 
						+ "number_storage VARCHAR(10),"
						+ "name VARCHAR(50),"
						+ "person_info VARCHAR(100),"
						+ "quarter1 VARCHAR(15),"
						+ "quarter2 VARCHAR(15),"
						+ "quarter3 VARCHAR(15),"
						+ "quarter4 VARCHAR(15),"
						+ "year VARCHAR(10))");

				st.executeUpdate("CREATE TABLE orders (" 
						+ "order_id INT PRIMARY KEY AUTO_INCREMENT,"
						+ "storage_id INT,"
						+ "date VARCHAR(20)," 
						+ "summ INT,"
						+ "quarter1 VARCHAR(15),"
						+ "quarter2 VARCHAR(15),"
						+ "quarter3 VARCHAR(15),"
						+ "quarter4 VARCHAR(15),"
						+ "year VARCHAR(10),"
						+ "info VARCHAR(100),"
						+ "FOREIGN KEY(storage_id) REFERENCES users(storage_id))");
		
				st.executeUpdate("CREATE TABLE electro (" 
						+ "electro_id INT PRIMARY KEY AUTO_INCREMENT," 
						+ "storage_id INT,"
						+ "date VARCHAR(20)," 
						+ "last_num INT,"
						+ "new_num INT,"
						+ "kw_h INT," 
						+ "tariff INT,"
						+ "summ INT," 
						+ "info VARCHAR(100),"
						+ "FOREIGN KEY(storage_id) REFERENCES users(storage_id))");
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		UsersStorageDao daoUser = new UsersStorageDao();
		daoUser.insert(new Users("0", "", "","", "", "", "",""));
		daoUser.insert(new Users("1a", "JANE ROE", "tel. 29-78-56-546, 200 E MAIN ST PHOENIX AZ 85123","+", "+", "III", "","2015"));
		daoUser.insert(new Users("1b", "JOHN SMITH", "tel. 44-164-76-389, 795 E DRAGRAM TUCSON AZ 85705","I", "", "", "" ,"2015"));
		daoUser.insert(new Users("2", "CHRIS NISWANDEE", "tel. 25-797-35-91, 300 BOYLSTON AVE E SEATTLE WA 98102", "+","II", "III", "IV", "2015"));

		OrdersStorageDao daoOrder = new OrdersStorageDao();
		daoOrder.insert(new Orders(2, "18-11-2017", 400000, "I","II","","","2018", "45325"));
		daoOrder.insert(new Orders(4, "19-11-2017", 200000, "I","", "", "", "2018","67567"));
		daoOrder.insert(new Orders(2, "20-11-2017", 200000, "+", "+", "III", "","2018", "34556"));
		daoOrder.insert(new Orders(4, "29-11-2017", 800000, "+","II", "III", "IV", "2018", "78574"));
		daoOrder.insert(new Orders(3, "30-11-2017", 400000, "I", "", "", "" ,"2018", "353465"));
		
		ElectroStorageDao daoElectro = new ElectroStorageDao();
		daoElectro.insert(new Electro(2, "18-11-2017", 45600, 45700, 100, 1000, 100000, "56456"));
		daoElectro.insert(new Electro(4, "19-11-2017", 34300, 34500, 200, 1000, 200000, "45763"));
		daoElectro.insert(new Electro(2, "20-11-2017", 57800, 57900, 100, 1000, 100000, "74535"));
		daoElectro.insert(new Electro(4, "21-11-2017", 43400, 43600, 200, 1000, 200000, "34567"));
		daoElectro.insert(new Electro(3, "31-11-2017", 44200, 44600, 400, 1000, 400000, "73457"));
		
	}

	public static void deleteDB(String url, String user, String password){
		
		try (Connection con = ConnectionPool.getPool().getConnection(url,user,password);
				Statement st = con.createStatement()) {
			
				st.executeUpdate("DROP DATABASE " + ConnectionPool.NAME_DB);
		
		} catch (SQLException e) {
				throw new RuntimeException(e);
		}
	}

}