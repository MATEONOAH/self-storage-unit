package babroval.storage.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import babroval.storage.entity.Storage;
import babroval.storage.frames.TableStorage;
import babroval.storage.mysql.ConnectionPool;

public class StorageDao implements Dao<Storage> {

	public StorageDao() {
	}

	@Override
	public void insert(Storage ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("insert into "
						+ ob.getClass().getSimpleName() + " (user_id, storage_number, info) values (?,?,?)")) {

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
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set storage_number=?, info=? " + " where storage_id = " + ob.getStorage_id())) {

			ps.setString(1, ob.getStorage_number());
			ps.setString(2, ob.getInfo());

			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void assignTo(Storage ob) {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				PreparedStatement ps = (PreparedStatement) cn.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set user_id=?" + " where storage_id =" + ob.getStorage_id())) {

			ps.setInt(1, ob.getUser_id());
			ps.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public List<String> loadAllNumbers() {

		List<String> allStoragesNumbers = new ArrayList<String>();

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage.storage_number" + " FROM storage"
						+ " WHERE storage.storage_id!=1" + " ORDER BY storage.storage_number")) {

			while (rs.next()) {
				allStoragesNumbers.add(rs.getString(1));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return allStoragesNumbers;
	}

	public Integer loadIdByStorageNumber(String number) {

		Integer storageId = Integer.valueOf(0);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage.storage_id" + " FROM storage"
						+ " WHERE storage.storage_number='" + number + "'")) {

			while (rs.next()) {
				storageId = Integer.valueOf(rs.getString(1));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return storageId;
	}

	@Override
	public List<String> loadAllNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Storage loadByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Storage loadByStorageNumber(String number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Storage loadLastPaidByStorageNumber(String number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableStorage loadReadOnlyTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableStorage loadEditTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableStorage loadTableByStorageNumber(String number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableStorage loadDebtorsByYearQuarter(String year, String quarter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadNameByStorageNumber(String number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date loadLastQuarterPaidByStorageNumber(String number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableStorage loadSortTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer loadIdByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
