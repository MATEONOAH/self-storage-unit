package babroval.storage.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import babroval.storage.model.Storage;
import babroval.storage.util.ConnectionPool;
import babroval.storage.util.TableStorage;

public class StorageDaoImpl implements Dao<Storage> {

	public StorageDaoImpl() {
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

	@Override
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

	@Override
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

	@Override
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
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public Storage loadByName(String name) {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public Storage loadByStorageNumber(String number) {

		Storage storage = new Storage();

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage.storage_id, storage.user_id, storage.storage_number, storage.info"
						+ " FROM user, storage" + " WHERE storage.storage_number = '" + number + "'")) {

			while (rs.next()) {
				storage.setStorage_id(Integer.valueOf(rs.getString(1)));
				storage.setUser_id(Integer.valueOf(rs.getString(2)));
				storage.setStorage_number(rs.getString(3));
				storage.setInfo(rs.getString(4));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return storage;
	}

	@Override
	public Storage loadLastPaidByStorageNumber(String number) {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public TableStorage loadReadOnlyTable() {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public TableStorage loadEditTable() {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public TableStorage loadTableByStorageNumber(String number) {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public TableStorage loadDebtorsByYearQuarter(String year, String quarter) {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public String loadNameByStorageNumber(String number) {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public Date loadLastQuarterPaidByStorageNumber(String number) {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public TableStorage loadSortTable() {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

	@Override
	public Integer loadIdByName(String name) {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}
}
