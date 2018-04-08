package babroval.storage.entity;

import java.sql.Date;


public class Rent {
	private int rent_id;
	private int storage_id;
	private Date date;
	private Date quarter_paid;
	private int summ;
	private String info;

	public Rent() {
	}

	public Rent(int rent_id) {
		this.rent_id = rent_id;
	}

	public Rent(int storage_id, Date date, Date quarter_paid, int summ, String info) {
		this.storage_id = storage_id;
		this.date = date;
		this.quarter_paid = quarter_paid;
		this.summ = summ;
		this.info = info;
	}

	public Rent(int rent_id, int storage_id, Date date, Date quarter_paid, int summ, String info) {
		this.rent_id = rent_id;
		this.storage_id = storage_id;
		this.date = date;
		this.quarter_paid = quarter_paid;
		this.summ = summ;
		this.info = info;
	}

	public int getRent_id() {
		return rent_id;
	}

	public void setRent_id(int rent_id) {
		this.rent_id = rent_id;
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

	public Date getQuarter_paid() {
		return quarter_paid;
	}

	public void setQuarter_paid(Date quarter_paid) {
		this.quarter_paid = quarter_paid;
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
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((quarter_paid == null) ? 0 : quarter_paid.hashCode());
		result = prime * result + rent_id;
		result = prime * result + storage_id;
		result = prime * result + summ;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Rent))
			return false;
		Rent other = (Rent) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (quarter_paid == null) {
			if (other.quarter_paid != null)
				return false;
		} else if (!quarter_paid.equals(other.quarter_paid))
			return false;
		if (rent_id != other.rent_id)
			return false;
		if (storage_id != other.storage_id)
			return false;
		if (summ != other.summ)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rent [rent_id=" + rent_id + ", storage_id=" + storage_id + ", date=" + date + ", quarter_paid="
				+ quarter_paid + ", summ=" + summ + ", info=" + info + "]";
	}

}