package babroval.storage.entity;

public class Electro {

	private int electro_id;
	private int storage_id;
	private String date;
	private int last_num;
	private int new_num;
	private int kw_h;
	private int tariff;
	private String coef;
	private int summ;
	private String info;

	public Electro() {
	}

	public Electro(int electro_id) {
		this.electro_id = electro_id;
	}

	public Electro(int storage_id, String date, int last_num, int new_num, int kw_h, int tariff, String coef, int summ,
			String info) {
		this.storage_id = storage_id;
		this.date = date;
		this.last_num = last_num;
		this.new_num = new_num;
		this.kw_h = kw_h;
		this.tariff = tariff;
		this.coef = coef;
		this.summ = summ;
		this.info = info;
	}

	public Electro(int electro_id, int storage_id, String date, int last_num, int new_num, int kw_h, int tariff,
			String coef, int summ, String info) {
		this.electro_id = electro_id;
		this.storage_id = storage_id;
		this.date = date;
		this.last_num = last_num;
		this.new_num = new_num;
		this.kw_h = kw_h;
		this.tariff = tariff;
		this.coef = coef;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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

	public String getCoef() {
		return coef;
	}

	public void setCoef(String coef) {
		this.coef = coef;
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

}
