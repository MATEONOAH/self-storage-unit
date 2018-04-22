package babroval.storage.entity;


public class Storage {
	
	private Integer storage_id;
	private String storage_number;
	private String info;

	public Storage() {
	}

	public Storage(Integer storage_id) {
		this.storage_id = storage_id;
	}

	public Storage(String storage_number, String info) {
		this.storage_number = storage_number;
		this.info = info;
	}

	public Storage(Integer storage_id, String storage_number, String info) {
		this.storage_id = storage_id;
		this.storage_number = storage_number;
		this.info = info;
	}

	public Integer getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(Integer storage_id) {
		this.storage_id = storage_id;
	}

	public String getStorage_number() {
		return storage_number;
	}

	public void setStorage_number(String storage_number) {
		this.storage_number = storage_number;
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
		result = prime * result + ((storage_id == null) ? 0 : storage_id.hashCode());
		result = prime * result + ((storage_number == null) ? 0 : storage_number.hashCode());
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
		if (storage_id == null) {
			if (other.storage_id != null)
				return false;
		} else if (!storage_id.equals(other.storage_id))
			return false;
		if (storage_number == null) {
			if (other.storage_number != null)
				return false;
		} else if (!storage_number.equals(other.storage_number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Storage [storage_id=" + storage_id + ", storage_number=" + storage_number + ", info=" + info + "]";
	}

}
