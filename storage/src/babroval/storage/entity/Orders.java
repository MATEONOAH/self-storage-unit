package babroval.storage.entity;

public class Orders {
	private int order_id;
	private int storage_id;
	private String date;
	private int summ;
	private String quarter1;
	private String quarter2;
	private String quarter3;
	private String quarter4;
	private String year;
	private String info;

	public Orders() {
	}

	public Orders(int order_id) {
		this.order_id = order_id;
	}

	
	public Orders(String info) {
		super();
		this.info = info;
	}
	
	
	public Orders(int order_id, String info) {
		super();
		this.order_id = order_id;
		this.info = info;
	}

	public Orders(int storage_id, String date, int summ, String quarter1, String quarter2, String quarter3,
			String quarter4, String year, String info) {
		this.storage_id = storage_id;
		this.date = date;
		this.summ = summ;
		this.quarter1 = quarter1;
		this.quarter2 = quarter2;
		this.quarter3 = quarter3;
		this.quarter4 = quarter4;
		this.year = year;
		this.info = info;
	}

	public Orders(int order_id, int storage_id, String date, int summ, String quarter1, String quarter2, String quarter3,
			String quarter4, String info) {
		super();
		this.order_id = order_id;
		this.storage_id = storage_id;
		this.date = date;
		this.summ = summ;
		this.quarter1 = quarter1;
		this.quarter2 = quarter2;
		this.quarter3 = quarter3;
		this.quarter4 = quarter4;
		this.info = info;
	}

	public Orders(String date, int summ, String quarter1, String quarter2, String quarter3, String quarter4,
			String year, String info) {
		this.date = date;
		this.summ = summ;
		this.quarter1 = quarter1;
		this.quarter2 = quarter2;
		this.quarter3 = quarter3;
		this.quarter4 = quarter4;
		this.year = year;
		this.info = info;
	}

	public Orders(int order_id, int storage_id, String date, int summ, String quarter1, String quarter2, String quarter3,
			String quarter4, String year, String info) {
		this.order_id = order_id;
		this.storage_id = storage_id;
		this.date = date;
		this.summ = summ;
		this.quarter1 = quarter1;
		this.quarter2 = quarter2;
		this.quarter3 = quarter3;
		this.quarter4 = quarter4;
		this.year = year;
		this.info = info;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(int storage_id) {
		this.storage_id = storage_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSumm() {
		return summ;
	}

	public void setSumm(int summ) {
		this.summ = summ;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}