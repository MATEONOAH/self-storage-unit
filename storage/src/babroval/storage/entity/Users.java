package babroval.storage.entity;

public class Users {
	private int storage_id;
	private String number_storage;
	private String name;
	private String person_info;
	private String quarter1;
	private String quarter2;
	private String quarter3;
	private String quarter4;
	private String year;

	public Users() {
	}

	public Users(int storage_id) {
		this.storage_id = storage_id;
	}

	public Users(int storage_id, String number_storage, String name, String person_info) {
		this.storage_id = storage_id;
		this.number_storage = number_storage;
		this.name = name;
		this.person_info = person_info;
	}

	public Users(int storage_id, String quarter1, String quarter2, String quarter3, String quarter4) {
		this.storage_id = storage_id;
		this.quarter1 = quarter1;
		this.quarter2 = quarter2;
		this.quarter3 = quarter3;
		this.quarter4 = quarter4;
	}

	public Users(int storage_id, String quarter1, String quarter2, String quarter3, String quarter4, String year) {
		this.storage_id = storage_id;
		this.quarter1 = quarter1;
		this.quarter2 = quarter2;
		this.quarter3 = quarter3;
		this.quarter4 = quarter4;
		this.year = year;
	}

	public Users(int storage_id, String number_storage, String name, String person_info, String quarter1, String quarter2,
			String quarter3, String quarter4, String year) {
		this.storage_id = storage_id;
		this.number_storage = number_storage;
		this.name = name;
		this.person_info = person_info;
		this.quarter1 = quarter1;
		this.quarter2 = quarter2;
		this.quarter3 = quarter3;
		this.quarter4 = quarter4;
		this.year = year;
	}

	public Users(String number_storage, String name, String person_info, String quarter1, String quarter2,
			String quarter3, String quarter4, String year) {
		this.number_storage = number_storage;
		this.name = name;
		this.person_info = person_info;
		this.quarter1 = quarter1;
		this.quarter2 = quarter2;
		this.quarter3 = quarter3;
		this.quarter4 = quarter4;
		this.year = year;
	}

	public int getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(int storage_id) {
		this.storage_id = storage_id;
	}

	public String getNumber_storage() {
		return number_storage;
	}

	public void setNumber_storage(String number_storage) {
		this.number_storage = number_storage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPerson_info() {
		return person_info;
	}

	public void setPerson_info(String person_info) {
		this.person_info = person_info;
	}

	public String getQuarter1() {
		return quarter1;
	}

	public void setQuarter1(String quarter1) {
		this.quarter1 = quarter1;
	}

	public String getQuarter2() {
		return quarter2;
	}

	public void setQuarter2(String quarter2) {
		this.quarter2 = quarter2;
	}

	public String getQuarter3() {
		return quarter3;
	}

	public void setQuarter3(String quarter3) {
		this.quarter3 = quarter3;
	}

	public String getQuarter4() {
		return quarter4;
	}

	public void setQuarter4(String quarter4) {
		this.quarter4 = quarter4;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}