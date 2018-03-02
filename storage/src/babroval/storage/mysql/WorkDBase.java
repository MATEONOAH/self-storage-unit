package babroval.storage.mysql;

import babroval.storage.dao.ElectroStorageDao;
import babroval.storage.dao.OrdersStorageDao;
import babroval.storage.dao.UsersStorageDao;
import babroval.storage.entity.Electro;
import babroval.storage.entity.Orders;
import babroval.storage.entity.Users;

public class WorkDBase {

	// set name for Data Base
	public final static String NAME_DB = "storage";

	// create and fill SQL tables
	public static void createDB(String url, String user, String password) throws Exception {
		
		// create new connection
		TransToMysql db;
			db = new TransToMysql(url, user, password);
		
		// create Data Base
		db.update("CREATE DATABASE " + NAME_DB);
		
		// use "storage" Data Base
		db.update("USE " + NAME_DB);
		
		// create tables
		db.update("CREATE TABLE users (" 
		        + "storage_id INT PRIMARY KEY AUTO_INCREMENT," 
		        + "number_storage VARCHAR(10),"
		        + "name VARCHAR(50),"
		        + "person_info VARCHAR(100),"
		        + "quarter1 VARCHAR(15),"
				+ "quarter2 VARCHAR(15),"
				+ "quarter3 VARCHAR(15),"
				+ "quarter4 VARCHAR(15),"
				+ "year VARCHAR(10))");

		db.update("CREATE TABLE orders (" 
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
		
		db.update("CREATE TABLE electro (" 
		        + "electro_id INT PRIMARY KEY AUTO_INCREMENT," 
		        + "storage_id INT,"
				+ "date VARCHAR(20)," 
				+ "last_num INT,"
				+ "new_num INT,"
				+ "kw_h INT," 
				+ "tariff INT,"
				+ "coef VARCHAR(10),"
				+ "summ INT," 
				+ "info VARCHAR(100),"
				+ "FOREIGN KEY(storage_id) REFERENCES users(storage_id))");

		UsersStorageDao daoUser = new UsersStorageDao(db);
		daoUser.insert(new Users("0", "", "","", "", "", "",""));
		daoUser.insert(new Users("1a", "JANE ROE", "tel. 29-78-56-546, 200 E MAIN ST PHOENIX AZ 85123","+", "+", "III", "","2015"));
		daoUser.insert(new Users("1b", "JOHN SMITH", "tel. 44-164-76-389, 795 E DRAGRAM TUCSON AZ 85705","I", "", "", "" ,"2015"));
		daoUser.insert(new Users("2", "CHRIS NISWANDEE", "tel. 25-797-35-91, 300 BOYLSTON AVE E SEATTLE WA 98102", "+","II", "III", "IV", "2015"));

		OrdersStorageDao daoOrder = new OrdersStorageDao(db);
		daoOrder.insert(new Orders(2, "2014-11-18", 400000, "I","II","","","2015", "45325"));
		daoOrder.insert(new Orders(4, "2014-11-19", 200000, "I","", "", "", "2015","67567"));
		daoOrder.insert(new Orders(2, "2014-11-20", 200000, "+", "+", "III", "","2015", "34556"));
		daoOrder.insert(new Orders(4, "2014-11-29", 800000, "+","II", "III", "IV", "2015", "78574"));
		daoOrder.insert(new Orders(3, "2014-11-30", 400000, "I", "", "", "" ,"2015", "353465"));
		
		ElectroStorageDao daoElectro = new ElectroStorageDao(db);
		daoElectro.insert(new Electro(2, "2014-11-18", 45600, 45700, 100, 1000, "1.2", 100000, "56456"));
		daoElectro.insert(new Electro(4, "2014-11-19", 34300, 34500, 200, 1000, "1.2", 200000, "45763"));
		daoElectro.insert(new Electro(2, "2014-11-20", 57800, 57900, 100, 1000, "1.4", 100000, "74535"));
		daoElectro.insert(new Electro(4, "2014-11-21", 43400, 43600, 200, 1000, "1.2", 200000, "34567"));
		daoElectro.insert(new Electro(3, "2014-11-31", 44200, 44600, 400, 1000, "1.5", 400000, "73457"));
		
		// disconnect from Data Base
		db.close();
	}

	public static void deleteDB(String url, String user, String password) throws Exception {
		
		// create new connection
		TransToMysql db;
			db = new TransToMysql(url, user, password);
		
		// delete Data Base car_rent
		db.update("DROP DATABASE " + NAME_DB);
		
		// Data Base disconnect
		db.close();
	}
}
