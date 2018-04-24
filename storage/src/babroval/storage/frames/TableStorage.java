package babroval.storage.frames;

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

import babroval.storage.mysql.InitDB;

class TableStorage extends JTable {

	private static final long serialVersionUID = 1L;
	private Component panel;

	public TableStorage(ResultSet rs) {
		
		DefaultTableModel dtm = new DefaultTableModel();
		try {
			int dateColumnNumber = 0;
			int quarterColumnNumber = 0;
			
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	
				dtm.addColumn(rsmd.getColumnName(i));
				
				if (rsmd.getColumnName(i).equals("date")) {
					dateColumnNumber = i;
				}
				if (rsmd.getColumnName(i).equals("quarter_paid")) {
					quarterColumnNumber = i;
				}
			}
			while (rs.next()) {
				Vector<String> v = new Vector<String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					
					if(i == dateColumnNumber) {
						
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
				dtm.addRow(v);
			}
			setModel(dtm);
			setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Query Database", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
