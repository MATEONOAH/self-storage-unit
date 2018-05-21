package babroval.storage.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import babroval.storage.util.TableStorage;

public interface Service <T>{
	
	void insert(T ob);

    void update(T ob);
    
    void assignTo(T ob);
    
    List<String> getAllNames();
    
    List<String> getAllNumbers();
    
    T getByName(String name);
   
    T getByStorageNumber(String number);
    
    T getLastPaidByStorageNumber(String number);
    
    TableStorage getReadOnlyTable();
    
    TableStorage getEditTable();
    
    TableStorage getSortTable();
    
    TableStorage getTableByStorageNumber(String number);
    
    TableStorage getDebtorsByYearQuarter(String year, String quarter);
    
    Integer getIdByName(String name);
    
    Integer getIdByStorageNumber(String number);

    String getNameByStorageNumber(String number);
    
    Date getLastQuarterPaidByStorageNumber(String number);

	BigDecimal getSum(Integer indicationLastPaid, Integer indication, BigDecimal tariff);
}
