package babroval.storage.dao;

import java.sql.Date;
import java.util.List;

import babroval.storage.view.util.TableStorage;

public interface Dao <T>{
	
     void insert(T ob);

     void update(T ob);
     
     void assignTo(T ob);
     
     List<String> loadAllNames();
     
     List<String> loadAllNumbers();
     
     T loadByName(String name);
    
     T loadByStorageNumber(String number);
     
     T loadLastPaidByStorageNumber(String number);
     
     TableStorage loadReadOnlyTable();
     
     TableStorage loadEditTable();
     
     TableStorage loadSortTable();
     
     TableStorage loadTableByStorageNumber(String number);
     
     TableStorage loadDebtorsByYearQuarter(String year, String quarter);
     
     Integer loadIdByName(String name);
     
     Integer loadIdByStorageNumber(String number);

     String loadNameByStorageNumber(String number);
     
     Date loadLastQuarterPaidByStorageNumber(String number);
  
}
