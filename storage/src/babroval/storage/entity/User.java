package babroval.storage.entity;


public class User {
	
	private Integer user_id;
	private Integer storage_id;
	private String name;
	private String info;

	public User() {
	}

	public User(Integer user_id) {
		this.user_id = user_id;
	}

	public User(Integer user_id, Integer storage_id) {
		this.user_id = user_id;
		this.storage_id = storage_id;
	}

	public User(Integer storage_id, String name) {
		this.storage_id = storage_id;
		this.name = name;
	}

	public User(Integer storage_id, String name, String info) {
		this.storage_id = storage_id;
		this.name = name;
		this.info = info;
	}

	public User(Integer user_id, Integer storage_id, String name, String info) {
		this.user_id = user_id;
		this.storage_id = storage_id;
		this.name = name;
		this.info = info;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(Integer storage_id) {
		this.storage_id = storage_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((storage_id == null) ? 0 : storage_id.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (storage_id == null) {
			if (other.storage_id != null)
				return false;
		} else if (!storage_id.equals(other.storage_id))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", storage_id=" + storage_id + ", name=" + name + ", info=" + info + "]";
	}
	
}