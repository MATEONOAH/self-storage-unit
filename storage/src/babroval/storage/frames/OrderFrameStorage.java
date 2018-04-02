package babroval.storage.frames;

import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import babroval.storage.dao.*;
import babroval.storage.entity.*;
import babroval.storage.mysql.*;

class OrderFrameStorage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNumber, labelD, labelQuarts, labelSumm, labelInf;
	private JComboBox<String> comboNum, comboYear;
	private JComboBox<Object> comboOrder;
	private JTextField fieldDate, tfName, tfSumm, tfInf;
	private JCheckBox quart1, quart2, quart3, quart4;
	private JButton enter;
	private String[] en = { "select", "electricity", "admin" };
	boolean quartSelect = false;

	public OrderFrameStorage() {
		setSize(295, 300);
		setTitle("OrderFrame");
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

		Date dNow = new Date();
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
			JOptionPane.showMessageDialog(panel, "database Error", "Error", JOptionPane.ERROR_MESSAGE);
		}

		labelSumm = new JLabel("Enter amount");
		tfSumm = new JTextField(20);
		tfSumm.setText("");

		labelQuarts = new JLabel("Quarter");
		quart1 = new JCheckBox("I");
		quart2 = new JCheckBox("II");
		quart3 = new JCheckBox("III");
		quart4 = new JCheckBox("IV");

		ft = new SimpleDateFormat("yyyy");
		int i = Integer.parseInt(ft.format(dNow));
		String[] year = { String.valueOf(i - 1), String.valueOf(i), String.valueOf(i + 1) };
		comboYear = new JComboBox<String>(year);
		comboYear.setSelectedIndex(1);

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
		panel.add(labelSumm);
		panel.add(tfSumm);
		panel.add(labelQuarts);
		panel.add(quart1);
		panel.add(quart2);
		panel.add(quart3);
		panel.add(quart4);
		panel.add(comboYear);
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

				numUpdateOrderFrame();
			}
		});

		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					if (tfInf.getText().equals("")) {
						tfInf.setText("");
					}
					String quarter1 = "";
					String quarter2 = "";
					String quarter3 = "";
					String quarter4 = "";
					if (quart1.isSelected()) {
						quarter1 = "I";
						quartSelect = true;
					}
					if (quart2.isSelected()) {
						if (!quart1.isSelected()) {
							quarter1 = "+";
						}
						quarter2 = "II";
						quartSelect = true;
					}
					if (quart3.isSelected()) {
						if (!quart2.isSelected()) {
							quarter1 = "+";
							quarter2 = "+";
						}
						quarter3 = "III";
						quartSelect = true;
					}
					if (quart4.isSelected()) {
						if (!quart3.isSelected()) {
							quarter1 = "+";
							quarter2 = "+";
							quarter3 = "+";
						}
						quarter4 = "IV";
						quartSelect = true;
					}

					if (!quartSelect || comboNum.getSelectedIndex() == 0 || comboNum.getSelectedItem().equals("")) {
						throw new NumberFormatException("e");
					}

					OrdersStorageDao daoOrder = new OrdersStorageDao();
					daoOrder.insert(new Orders(comboNum.getSelectedIndex(), fieldDate.getText(),
							Integer.valueOf(tfSumm.getText()), quarter1, quarter2, quarter3, quarter4,
							(String) comboYear.getSelectedItem(), tfInf.getText()));

					UsersStorageDao daoUser = new UsersStorageDao();
					daoUser.updateQuarters(new Users(comboNum.getSelectedIndex(), quarter1, quarter2, quarter3,
							quarter4, (String) comboYear.getSelectedItem()));
					JOptionPane.showMessageDialog(panel, "Payment has been included", "Message",
							JOptionPane.INFORMATION_MESSAGE);

					comboNum.setSelectedIndex(0);
					tfName.setText("");
					tfSumm.setText("");
					tfInf.setText("");
					quart1.setSelected(false);
					quart2.setSelected(false);
					quart3.setSelected(false);
					quart4.setSelected(false);
					quartSelect = false;

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "Select storage, quarters and enter the right payment",
							"Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database error", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		comboOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (comboOrder.getSelectedIndex() == 1) {

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

	private void numUpdateOrderFrame() {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM users")) {

			while (rs.next()) {
				String i = rs.getString(2);
				String j = (String) comboNum.getSelectedItem();

				if (i.equals(j)) {
					tfName.setText(rs.getString(3));
					break;
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "database error", "Error", JOptionPane.ERROR_MESSAGE);
		}
		panel.updateUI();
	}

}
