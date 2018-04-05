package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import babroval.storage.dao.ElectroStorageDao;
import babroval.storage.dao.OrdersStorageDao;
import babroval.storage.dao.UsersStorageDao;
import babroval.storage.entity.Orders;
import babroval.storage.entity.Users;
import babroval.storage.mysql.ConnectionPool;

public class ElectroFrameStorage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNumber, labelD, labelIndicationLastPaid, labelIndication, labelInf;
	private JComboBox<String> comboNum;
	private JComboBox<Object> comboOrder;
	private JTextField fieldDate, tfName, tfIndication, tfIndicationLast, tfInf;
	private JButton enter;
	private String[] en = { "select", "rent", "admin" };

	public ElectroFrameStorage() {
		setSize(350, 300);
		setTitle("Electric power payment form");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
		setResizable(false);
	}

	private void initComponents() {

		panel = new JPanel();

		labelNumber = new JLabel("Number of storage");
		comboNum = new JComboBox<String>();
		labelD = new JLabel("Date ");

		java.util.Date dNow = new java.util.Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		fieldDate = new JTextField(ft.format(dNow));

		tfName = new JTextField(20);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM users")) {

			comboNum.addItem("");
			while (rs.next()) {
				comboNum.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
		}

		labelIndication = new JLabel("Enter electric power indication");
		tfIndication = new JTextField(20);
		tfIndication.setText("");

		labelIndicationLastPaid = new JLabel("Electric power indication last paid");
		tfIndicationLast = new JTextField(20);
		
		labelInf = new JLabel("Number of receipt order");
		tfInf = new JTextField(20);
		tfInf.setText("");
		enter = new JButton("Enter");
		comboOrder = new JComboBox<Object>(en);

		panel.add(labelNumber);
		panel.add(comboNum);
		panel.add(labelD);
		panel.add(fieldDate);
		panel.add(tfName);
		panel.add(labelIndication);
		panel.add(tfIndication);
		panel.add(labelIndicationLastPaid);
		panel.add(tfIndicationLast);
		panel.add(labelInf);
		panel.add(tfInf);
		panel.add(enter);
		panel.add(comboOrder);

		add(panel);
	}

	private void action() {
		comboNum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				numUpdateElectroFrame();
			}
		});

		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					if (tfInf.getText().equals("")) {
						tfInf.setText("");
					}
					if (comboNum.getSelectedIndex() == 0 || comboNum.getSelectedItem().equals("")) {
						throw new NumberFormatException("e");
					}
					
					JOptionPane.showMessageDialog(panel, "Payment has been included", "Message",
							JOptionPane.INFORMATION_MESSAGE);

					comboNum.setSelectedIndex(0);
					tfName.setText("");
					tfIndication.setText("");
					tfIndicationLast.setText("");
					tfInf.setText("");

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "Select storage and electric power indication",
							"error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		comboOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (comboOrder.getSelectedIndex() == 1) {
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new OrderFrameStorage();
						}
					});
					dispose();
				}
				if (comboOrder.getSelectedIndex() == 2) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new LoginFrameStorage();
						}
					});
					dispose();
				}
			}
		});
	}

	private void numUpdateElectroFrame() {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT users.number_storage, users.name, electro.last_num, electro.date FROM users, electro"
						+ " WHERE electro.storage_id=users.storage_id")) {

			Date temp = Date.valueOf("2000-11-01");
			
			while (rs.next()) {
				String i = rs.getString(1);
				String j = (String) comboNum.getSelectedItem();
				
				if (i.equals(j)) {
				
					Date d = rs.getDate(4);

					if (d.compareTo(temp)>0) {
						tfName.setText(rs.getString(2));
						tfIndicationLast.setText(rs.getString(3));
						break;
					}else {
						temp = d;
					}
				}
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
		}
		panel.updateUI();
	}

}