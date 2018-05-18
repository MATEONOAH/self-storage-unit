package babroval.storage.view.util;

import java.awt.Component;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import babroval.storage.dao.resources.InitDB;

public class TableStorage extends JTable {

	private static final long serialVersionUID = 1L;
	private Component panel;

	public TableStorage() {
	}

	public TableStorage(ResultSet rs) {
		
		DefaultTableModel dtm = new DefaultTableModel();
		try {
			int dateColumnNumber = 0;
			int quarterColumnNumber = 0;
			
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	
				if (rsmd.getColumnName(i).equals("quarter_paid") 
					| rsmd.getColumnName(i).equals("MAX(rent.quarter_paid)")) {
					
					dtm.addColumn("quarter_paid");
					quarterColumnNumber = i;
				
				}else if (rsmd.getColumnName(i).equals("date")) {
					
					dtm.addColumn("date");
					dateColumnNumber = i;
				
				}else dtm.addColumn(rsmd.getColumnName(i));
			
			}
			
			while (rs.next()) {
				Vector<String> v = new Vector<String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					
					if(rs.getString(i).equals("1970-01-01")){
						break;
					}
					if(rs.getString(i).equals("0")){
						for(int j = 1; j<rsmd.getColumnCount()-1; j++) {
							v.add("DELETED");
						}
						i=rsmd.getColumnCount()-1;
					
					}else if(i == dateColumnNumber) {
						
						Date date= InitDB.stringToDate(rs.getString(i), "yyyy-MM-dd");
						String dateFormat = new SimpleDateFormat("dd-MM-yyyy").format(date);
						v.add(dateFormat);
						
					}else if(i == quarterColumnNumber){
						
						Date date= InitDB.stringToDate(rs.getString(i), "yyyy-MM-dd");
						String quarter = new SimpleDateFormat("MM").format(date);
						
						switch(quarter) {
						case "01" : quarter = " I   - ";
							break;
						case "04" : quarter = " II  - ";
							break;
						case "07" : quarter = " III - ";
							break;
						case "10" : quarter = " IV - ";
							break; 
						}
						String year = new SimpleDateFormat("yyyy").format(date);
						v.add(quarter + year);
					
					}else {
						v.add(rs.getString(i));
					}
				}
				if(!v.isEmpty()) dtm.addRow(v);
			}
			setModel(dtm);
			setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Query Database", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
