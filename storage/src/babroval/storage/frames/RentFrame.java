package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import babroval.storage.mysql.ConnectionPool;

class RentFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNumber, labelD, labelQuarts, labelSumm, labelInf;
	private JComboBox<String> comboNum;
	private JComboBox<Integer> comboYear;
	private JComboBox<Object> comboOrder;
	private JTextField fieldDate, tfName, tfSumm, tfInf;
	private JCheckBox quart1, quart2, quart3, quart4;
	private JButton enter;
	private String[] en = { "select", "electricity", "admin" };
	boolean quartSelect = false;

	public RentFrame() {
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

		Date dNow = new Date(System.currentTimeMillis());
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		fieldDate = new JTextField(ft.format(dNow));

		tfName = new JTextField(20);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage.number FROM storage")) {

			comboNum.addItem("");
			while (rs.next()) {
				comboNum.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
		}

		labelSumm = new JLabel("Enter amount");
		tfSumm = new JTextField(20);
		tfSumm.setText("");

		labelQuarts = new JLabel("Quarter");
		quart1 = new JCheckBox("I");
		quart2 = new JCheckBox("II");
		quart3 = new JCheckBox("III");
		quart4 = new JCheckBox("IV");

		comboYear = new JComboBox<Integer>();

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
				comboYear.removeAllItems();
				numUpdateOrderFrame();
			}
		});

		comboYear.addActionListener(new ActionListener() {

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

					// RentDao daoOrder = new RentDao();
					// daoOrder.insert(new Rent(comboNum.getSelectedIndex(),
					// InitDB.stringToDate(fieldDate.getText()),
					// Integer.valueOf(tfSumm.getText()), quarter1, quarter2, quarter3, quarter4,
					// (String) comboYear.getSelectedItem(), tfInf.getText()));

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

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new ElectricFrame();
						}
					});
					dispose();
				}

				if (comboOrder.getSelectedIndex() == 2) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new LoginFrame();
						}
					});
					dispose();
				}
			}
		});
	}

	private void numUpdateOrderFrame() {

		if (comboYear.getSelectedIndex() == 0) {
			quart1.setEnabled(false);
			quart1.setSelected(true);
			quart2.setEnabled(false);
			quart2.setSelected(true);
			quart3.setEnabled(false);
			quart3.setSelected(true);
			quart4.setEnabled(false);
			quart4.setSelected(true);
		} else if (comboYear.getSelectedIndex() == 2) {
			quart1.setEnabled(true);
			quart1.setSelected(false);
			quart2.setEnabled(true);
			quart2.setSelected(false);
			quart3.setEnabled(true);
			quart3.setSelected(false);
			quart4.setEnabled(true);
			quart4.setSelected(false);
		} else {
			tfName.setText("");
			quart1.setEnabled(true);
			quart1.setSelected(false);
			quart2.setEnabled(true);
			quart2.setSelected(false);
			quart3.setEnabled(true);
			quart3.setSelected(false);
			quart4.setEnabled(true);
			quart4.setSelected(false);

			try (Connection cn = ConnectionPool.getPool().getConnection();
					Statement st = cn.createStatement();
					ResultSet rs = st.executeQuery("SELECT user.name, MAX(rent.quarter_paid) FROM rent, storage, user"
							+ " WHERE storage.number='" + comboNum.getSelectedItem()
							+ "' AND rent.storage_id=storage.storage_id" + " AND user.storage_id=storage.storage_id")) {

				while (rs.next()) {

					tfName.setText(rs.getString(1));

					String str = rs.getString(2);
					int year_paid = Integer.valueOf(str.substring(0, 4));

					comboYear.removeAllItems();
					comboYear.addItem(year_paid - 1);
					comboYear.addItem(year_paid);
					comboYear.addItem(year_paid + 1);

					comboYear.setSelectedIndex(1);

					int first_month_quarter_paid = Integer.valueOf(str.substring(5, 7));
					switch (first_month_quarter_paid) {
					case 10:
						quart4.setEnabled(false);
						quart4.setSelected(true);
					case 7:
						quart3.setEnabled(false);
						quart3.setSelected(true);
					case 4:
						quart2.setEnabled(false);
						quart2.setSelected(true);
					case 1:
						quart1.setEnabled(false);
						quart1.setSelected(true);
					}
				}

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
			}
		}
		panel.updateUI();
	}

}
