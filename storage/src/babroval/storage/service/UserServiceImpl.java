package babroval.storage.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import babroval.storage.dao.Dao;
import babroval.storage.dao.UserDaoImpl;
import babroval.storage.util.TableStorage;

public class UserServiceImpl<T> implements Service<T> {

	@SuppressWarnings("unchecked")
	private Dao<T> dao = (Dao<T>) new UserDaoImpl();

	@Override
	public void insert(T ob) {
		dao.insert(ob);
	}

	@Override
	public void update(T ob) {
		dao.update(ob);
	}

	@Override
	public void assignTo(T ob) {
		dao.assignTo(ob);
	}

	@Override
	public List<String> getAllNames() {
		return dao.loadAllNames();
	}

	@Override
	public List<String> getAllNumbers() {
		return dao.loadAllNumbers();
	}

	@Override
	public T getByName(String name) {
		return dao.loadByName(name);
	}

	@Override
	public T getByStorageNumber(String number) {
		return dao.loadByStorageNumber(number);
	}

	@Override
	public T getLastPaidByStorageNumber(String number) {
		return dao.loadLastPaidByStorageNumber(number);
	}

	@Override
	public TableStorage getReadOnlyTable() {
		return dao.loadReadOnlyTable();
	}

	@Override
	public TableStorage getEditTable() {
		return dao.loadEditTable();
	}

	@Override
	public TableStorage getSortTable() {
		return dao.loadSortTable();
	}

	@Override
	public TableStorage getTableByStorageNumber(String number) {
		return dao.loadTableByStorageNumber(number);
	}

	@Override
	public TableStorage getDebtorsByYearQuarter(String year, String quarter) {
		return dao.loadDebtorsByYearQuarter(year, quarter);
	}

	@Override
	public Integer getIdByName(String name) {
		return dao.loadIdByName(name);
	}

	@Override
	public Integer getIdByStorageNumber(String number) {
		return dao.loadIdByStorageNumber(number);
	}

	@Override
	public String getNameByStorageNumber(String number) {
		return dao.loadNameByStorageNumber(number);
	}

	@Override
	public Date getLastQuarterPaidByStorageNumber(String number) {
		return dao.loadLastQuarterPaidByStorageNumber(number);
	}

	@Override
	public BigDecimal getSum(Integer indicationLastPaid, Integer indication, BigDecimal tariff) {
		throw new UnsupportedOperationException("Method has not implemented yet");
	}

}