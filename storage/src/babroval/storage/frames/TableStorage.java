package babroval.storage.frames;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class TableStorage extends JTable {

	private static final long serialVersionUID = 1L;
	private Component panel;

	public TableStorage(ResultSet rs) {
		DefaultTableModel dtm = new DefaultTableModel();
		try {
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				;
				dtm.addColumn(rsmd.getColumnName(i));
			}
			while (rs.next()) {
				Vector<String> v = new Vector<String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					v.add(rs.getString(i));
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
