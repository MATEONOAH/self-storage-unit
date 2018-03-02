package babroval.storage.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import babroval.storage.entity.Electro;
import babroval.storage.mysql.TransToMysql;

public class ElectroStorageDao implements InterfaceStorageDao<Electro> {

	private TransToMysql db;

	public ElectroStorageDao(TransToMysql db) {
		this.db = db;
	}

	@Override
	public void insert(Electro ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn()
				.prepareStatement("insert into " + ob.getClass()
						.getSimpleName()
				+ " (storage_id, date, last_num, new_num, kw_h, tariff, coef, summ, info) values (?,?,?,?,?,?,?,?,?)");
		ps.setInt(1, ob.getStorage_id());
		ps.setString(2, ob.getDate());
		ps.setInt(3, ob.getLast_num());
		ps.setInt(4, ob.getNew_num());
		ps.setInt(5, ob.getKw_h());
		ps.setInt(6, ob.getTariff());
		ps.setString(7, ob.getCoef());
		ps.setInt(8, ob.getSumm());
		ps.setString(9, ob.getInfo());
		ps.execute();
	}

	@Override
	public void update(Electro ob) throws SQLException {
		PreparedStatement ps = (PreparedStatement) db.getCn()
				.prepareStatement("update " + ob.getClass().getSimpleName()
						+ " set storage_id=?, date=?, last_num=?, new_num=?, kw_h=?, tariff=?, coef=?, summ=?, info=?"
						+ " where electro_id = " + ob.getElectro_id());
		ps.setInt(1, ob.getStorage_id());
		ps.setString(2, ob.getDate());
		ps.setInt(3, ob.getLast_num());
		ps.setInt(4, ob.getNew_num());
		ps.setInt(5, ob.getKw_h());
		ps.setInt(6, ob.getTariff());
		ps.setString(7, ob.getCoef());
		ps.setInt(8, ob.getSumm());
		ps.setString(9, ob.getInfo());
		ps.execute();
	}
}
