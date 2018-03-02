package babroval.storage.dao;

import java.sql.SQLException;

public interface InterfaceStorageDao <T>{
     public void insert(T ob)throws SQLException;

     public void update(T ob)throws SQLException;

}
