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

import babroval.storage.dao.RentDao;
import babroval.storage.entity.Rent;
import babroval.storage.mysql.ConnectionPool;
import babroval.storage.mysql.InitDB;

class RentFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNumber, labelDate, labelQuarter, labelSumm, labelYear, labelInf;
	private JComboBox<String> comboNum;
	private JComboBox<Object> comboSelect;
	private JTextField tfDate, tfName, tfSumm, tfInf;
	private JCheckBox quart1, quart2, quart3, quart4;
	private JButton enter;
	private String[] select = { "select", "electricity", "admin" };

	public RentFrame() {
		setSize(280, 260);
		setTitle("RentFrame");
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
		labelDate = new JLabel("Date ");

		Date dateNow = new Date(System.currentTimeMillis());
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		tfDate = new JTextField(ft.format(dateNow));

		tfName = new JTextField(20);
		tfName.setEnabled(false);

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

		labelQuarter = new JLabel("Quarter");
		quart1 = new JCheckBox("I");
		quart2 = new JCheckBox("II");
		quart3 = new JCheckBox("III");
		quart4 = new JCheckBox("IV");

		labelYear = new JLabel("of Year");

		labelInf = new JLabel("Number of receipt order");
		tfInf = new JTextField(20);

		enter = new JButton("Enter");
		comboSelect = new JComboBox<Object>(select);

		resetFrame();

		panel.add(labelNumber);
		panel.add(comboNum);
		panel.add(labelDate);
		panel.add(tfDate);
		panel.add(tfName);
		panel.add(labelSumm);
		panel.add(tfSumm);
		panel.add(labelQuarter);
		panel.add(quart1);
		panel.add(quart2);
		panel.add(quart3);
		panel.add(quart4);
		panel.add(labelYear);
		panel.add(labelInf);
		panel.add(tfInf);
		panel.add(enter);
		panel.add(comboSelect);

		add(panel);
	}

	private void action() {
		comboNum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				updateFrame();
			}
		});

		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					if (tfInf.getText().equals("")) {
						tfInf.setText("");
					}

					String quarter = ""; // first month of year quarter

					if (quart1.isEnabled() && quart1.isSelected())
						quarter = "01";
					else if (quart2.isEnabled() && quart2.isSelected())
						quarter = "04";
					else if (quart3.isEnabled() && quart3.isSelected())
						quarter = "07";
					else if (quart4.isEnabled() && quart4.isSelected())
						quarter = "10";

					if (quarter.equals("") || comboNum.getSelectedIndex() == 0
							|| comboNum.getSelectedItem().equals("")) {
						throw new NumberFormatException("e");
					}

					RentDao daoRent = new RentDao();
					daoRent.insert(new Rent(comboNum.getSelectedIndex(), InitDB.stringToDate(tfDate.getText()),
							InitDB.stringToDate("01-" + quarter + "-" + labelYear.getText()),
							Integer.valueOf(tfSumm.getText()), tfInf.getText()));

					JOptionPane.showMessageDialog(panel, "Payment has been included", "Message",
							JOptionPane.INFORMATION_MESSAGE);

					comboNum.setSelectedIndex(0);
					resetFrame();

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "select storage, quarters and enter the right payment",
							"error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
					comboNum.setSelectedIndex(0);
					resetFrame();
				}
			}
		});

		comboSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (comboSelect.getSelectedIndex() == 1) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new ElectricFrame();
						}
					});
					dispose();
				}

				if (comboSelect.getSelectedIndex() == 2) {

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

	private void updateFrame() {

		if (comboNum.getSelectedIndex() == 0 || comboNum.getSelectedItem().equals("")) {
			comboNum.setSelectedIndex(0);
			resetFrame();
		} else {
			resetFrame();

			try (Connection cn = ConnectionPool.getPool().getConnection();
					Statement st = cn.createStatement();
					ResultSet rs = st.executeQuery("SELECT user.name, MAX(rent.quarter_paid) FROM rent, storage, user"
							+ " WHERE storage.number='" + comboNum.getSelectedItem()
							+ "' AND rent.storage_id=storage.storage_id" + " AND user.storage_id=storage.storage_id")) {

				while (rs.next()) {

					tfName.setText(rs.getString(1));

					String str = rs.getString(2);
					Integer year = Integer.valueOf(str.substring(0, 4));
					Integer quarter = Integer.valueOf(str.substring(5, 7));

					switch (quarter) {
					case 1:
						quart1.setEnabled(false);
						quart1.setSelected(true);
						quart2.setEnabled(true);
						quart2.setSelected(false);
						break;
					case 4:
						quart1.setEnabled(false);
						quart1.setSelected(true);
						quart2.setEnabled(false);
						quart2.setSelected(true);
						quart3.setEnabled(true);
						quart3.setSelected(false);
						break;
					case 7:
						quart1.setEnabled(false);
						quart1.setSelected(true);
						quart2.setEnabled(false);
						quart2.setSelected(true);
						quart3.setEnabled(false);
						quart3.setSelected(true);
						quart4.setEnabled(true);
						quart4.setSelected(false);
						break;
					case 10:
						year++;
						quart1.setEnabled(true);
						quart1.setSelected(false);
						break;
					}
					labelYear.setText(String.valueOf(year));

				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
			}
		}
		panel.updateUI();
	}

	private void resetFrame() {

		tfName.setText("");
		tfSumm.setText("");
		quart1.setEnabled(false);
		quart1.setSelected(false);
		quart2.setEnabled(false);
		quart2.setSelected(false);
		quart3.setEnabled(false);
		quart3.setSelected(false);
		quart4.setEnabled(false);
		quart4.setSelected(false);
		labelYear.setText("of Year");
		tfInf.setText("");
	}

}
