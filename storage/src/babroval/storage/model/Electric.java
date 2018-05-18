package babroval.storage.model;

import java.math.BigDecimal;
import java.sql.Date;


public class Electric {

	private Integer electric_id = 0;
	private Integer storage_id = 0;
	private Date date = new Date(0);
	private BigDecimal tariff = new BigDecimal("0");
	private Integer meter_paid = 0;
	private BigDecimal sum = new BigDecimal("0");
	private String info = "";

	public Electric() {
	}

	public Electric(Integer electric_id) {
		this.electric_id = electric_id;
	}

	public Electric(Integer storage_id, Date date, BigDecimal tariff, Integer meter_paid, BigDecimal sum, String info) {
		this.storage_id = storage_id;
		this.date = date;
		this.tariff = tariff;
		this.meter_paid = meter_paid;
		this.sum = sum;
		this.info = info;
	}

	public Electric(Integer electric_id, Integer storage_id, Date date, BigDecimal tariff, Integer meter_paid,
			BigDecimal sum, String info) {
		this.electric_id = electric_id;
		this.storage_id = storage_id;
		this.date = date;
		this.tariff = tariff;
		this.meter_paid = meter_paid;
		this.sum = sum;
		this.info = info;
	}

	public Integer getElectric_id() {
		return electric_id;
	}

	public void setElectric_id(Integer electric_id) {
		this.electric_id = electric_id;
	}

	public Integer getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(Integer storage_id) {
		this.storage_id = storage_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getTariff() {
		return tariff;
	}

	public void setTariff(BigDecimal tariff) {
		this.tariff = tariff;
	}

	public Integer getMeter_paid() {
		return meter_paid;
	}

	public void setMeter_paid(Integer meter_paid) {
		this.meter_paid = meter_paid;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
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
		result = prime * result + ((electric_id == null) ? 0 : electric_id.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((meter_paid == null) ? 0 : meter_paid.hashCode());
		result = prime * result + ((storage_id == null) ? 0 : storage_id.hashCode());
		result = prime * result + ((sum == null) ? 0 : sum.hashCode());
		result = prime * result + ((tariff == null) ? 0 : tariff.hashCode());
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
		if (electric_id == null) {
			if (other.electric_id != null)
				return false;
		} else if (!electric_id.equals(other.electric_id))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (meter_paid == null) {
			if (other.meter_paid != null)
				return false;
		} else if (!meter_paid.equals(other.meter_paid))
			return false;
		if (storage_id == null) {
			if (other.storage_id != null)
				return false;
		} else if (!storage_id.equals(other.storage_id))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		} else if (!sum.equals(other.sum))
			return false;
		if (tariff == null) {
			if (other.tariff != null)
				return false;
		} else if (!tariff.equals(other.tariff))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Electric [electric_id=" + electric_id + ", storage_id=" + storage_id + ", date=" + date + ", tariff="
				+ tariff + ", meter_paid=" + meter_paid + ", sum=" + sum + ", info=" + info + "]";
	}

}