package babroval.storage.entity;

import java.sql.Date;


public class Electric {

	private int electric_id;
	private int storage_id;
	private Date date;
	private int meter_paid;
	private int summ;
	private String info;

	public Electric() {
	}

	public Electric(int electric_id) {
		this.electric_id = electric_id;
	}

	public Electric(int storage_id, Date date, int meter_paid, int summ, String info) {
		this.storage_id = storage_id;
		this.date = date;
		this.meter_paid = meter_paid;
		this.summ = summ;
		this.info = info;
	}

	public Electric(int electric_id, int storage_id, Date date, int meter_paid, int summ, String info) {
		this.electric_id = electric_id;
		this.storage_id = storage_id;
		this.date = date;
		this.meter_paid = meter_paid;
		this.summ = summ;
		this.info = info;
	}

	public int getElectric_id() {
		return electric_id;
	}

	public void setElectric_id(int electric_id) {
		this.electric_id = electric_id;
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

	public int getMeter_paid() {
		return meter_paid;
	}

	public void setMeter_paid(int meter_paid) {
		this.meter_paid = meter_paid;
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
		result = prime * result + electric_id;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + meter_paid;
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
		if (!(obj instanceof Electric))
			return false;
		Electric other = (Electric) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (electric_id != other.electric_id)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (meter_paid != other.meter_paid)
			return false;
		if (storage_id != other.storage_id)
			return false;
		if (summ != other.summ)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Electric [electric_id=" + electric_id + ", storage_id=" + storage_id + ", date=" + date
				+ ", meter_paid=" + meter_paid + ", summ=" + summ + ", info=" + info + "]";
	}

}