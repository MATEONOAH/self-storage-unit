package babroval.storage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import babroval.storage.entity.Storage;
import babroval.storage.mysql.ConnectionPool;


public class StorageDao implements Dao<Storage> {

	public StorageDao() {
	}
	
	@Override
	public void insert(Storage ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement(
					"insert into "+ ob.getClass().getSimpleName() 
				 + " (user_id, storage_number, info) values (?,?,?)")) {

			ps.setInt(1, ob.getUser_id());
			ps.setString(2, ob.getStorage_number());
			ps.setString(3, ob.getInfo());
			
			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Storage ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement(
					"update "+ ob.getClass().getSimpleName()
		  		 + " set storage_number=?, info=? "
		  		 + " where storage_id = " + ob.getStorage_id())) {

			ps.setString(1, ob.getStorage_number());
			ps.setString(2, ob.getInfo());
			
			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void assignUserToGarage(Storage ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement(
					"update " + ob.getClass().getSimpleName() 
     			 + " set user_id=?"
				 + " where storage_id ="+ ob.getStorage_id())) {
			
			ps.setInt(1, ob.getUser_id());
			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public List<String> loadAllStoragesNumbers() {
		
		List<String> allStoragesNumbers = new ArrayList<String>();
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT storage.storage_number"
					 + " FROM storage"
					 + " WHERE storage.storage_number!=0"
					 + " ORDER BY storage.storage_number ASC")) {
		
			while (rs.next()) {
				allStoragesNumbers.add(rs.getString(1));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return allStoragesNumbers;
	}

	public Integer loadStorageIdByNumber(String storageNum) {
		
		Integer storageId = new Integer(0);
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT storage.storage_id"
					 + " FROM storage"
					 + " WHERE storage.storage_number='"+ storageNum +"'")) {
		
			while (rs.next()) {
				storageId = Integer.valueOf(rs.getString(1));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return storageId;
	}
	
}
