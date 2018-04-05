package babroval.storage.entity;

import java.sql.Date;

public class Electro {

	private int electro_id;
	private int storage_id;
	private Date date;
	private int last_num;
	private int new_num;
	private int kw_h;
	private int tariff;
	private int summ;
	private String info;

	public Electro() {
	}
	
	public Electro(int storage_id, Date date, int last_num, int new_num, int kw_h, int tariff, int summ,
			String info) {
		this.storage_id = storage_id;
		this.date = date;
		this.last_num = last_num;
		this.new_num = new_num;
		this.kw_h = kw_h;
		this.tariff = tariff;
		this.summ = summ;
		this.info = info;
	}

	public int getElectro_id() {
		return electro_id;
	}

	public void setElectro_id(int electro_id) {
		this.electro_id = electro_id;
	}

	public int getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(int storage_id) {
		this.storage_id = storage_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getLast_num() {
		return last_num;
	}

	public void setLast_num(int last_num) {
		this.last_num = last_num;
	}

	public int getNew_num() {
		return new_num;
	}

	public void setNew_num(int new_num) {
		this.new_num = new_num;
	}

	public int getKw_h() {
		return kw_h;
	}

	public void setKw_h(int kw_h) {
		this.kw_h = kw_h;
	}

	public int getTariff() {
		return tariff;
	}

	public void setTariff(int tariff) {
		this.tariff = tariff;
	}

	public int getSumm() {
		return summ;
	}

	public void setSumm(int summ) {
		this.summ = summ;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + electro_id;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + kw_h;
		result = prime * result + last_num;
		result = prime * result + new_num;
		result = prime * result + storage_id;
		result = prime * result + summ;
		result = prime * result + tariff;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Electro other = (Electro) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (electro_id != other.electro_id)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (kw_h != other.kw_h)
			return false;
		if (last_num != other.last_num)
			return false;
		if (new_num != other.new_num)
			return false;
		if (storage_id != other.storage_id)
			return false;
		if (summ != other.summ)
			return false;
		if (tariff != other.tariff)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Electro [electro_id=" + electro_id + ", storage_id=" + storage_id + ", date=" + date + ", last_num="
				+ last_num + ", new_num=" + new_num + ", kw_h=" + kw_h + ", tariff=" + tariff + ", summ=" + summ
				+ ", info=" + info + "]";
	}

	
}