package babroval.storage.entity;


public class Storage {
	
	private int storage_id;
	private String number;
	private String info;

	public Storage() {
	}

	public Storage(int storage_id) {
		this.storage_id = storage_id;
	}

	public Storage(String number, String info) {
		this.number = number;
		this.info = info;
	}

	public Storage(int storage_id, String number, String info) {
		this.storage_id = storage_id;
		this.number = number;
		this.info = info;
	}

	public int getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(int storage_id) {
		this.storage_id = storage_id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + storage_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Storage))
			return false;
		Storage other = (Storage) obj;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (storage_id != other.storage_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Storage [storage_id=" + storage_id + ", number=" + number + ", info=" + info + "]";
	}
	
}
