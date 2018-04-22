package babroval.storage.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
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
	private JLabel labelNumber, labelDate, labelName, labelQuarter, labelSum, labelYear, labelInf;
	private JComboBox<String> comboNum, comboSelect;
	private JTextField tfDate, tfName, tfSum, tfInf;
	private JCheckBox quart1, quart2, quart3, quart4;
	private JButton enter, cancel;
	private String[] select = { "select:", "ELECTRICITY PAYMENT", "MAIN VIEW" };

	
	public RentFrame() {
		setSize(300, 327);
		setTitle("Rent payment");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
		setResizable(false);
	}

	private void initComponents() {

		panel = new JPanel();

		labelDate = new JLabel("Date of payment:");
		Date dateNow = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		tfDate = new JTextField(sdf.format(dateNow));

		labelNumber = new JLabel("Select Number of storage:");
		comboNum = new JComboBox<String>();
		comboNum.setPreferredSize(new Dimension(50, 20));

		labelName = new JLabel("Name of tenant:");
		tfName = new JTextField(20);
		tfName.setEnabled(false);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage.number FROM storage")) {

			comboNum.addItem("");
			while (rs.next()) {
				comboNum.addItem(rs.getString(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
		}

		labelQuarter = new JLabel("Select Quarter of");

		quart1 = new JCheckBox("I");
		quart2 = new JCheckBox("II");
		quart3 = new JCheckBox("III");
		quart4 = new JCheckBox("IV");

		labelYear = new JLabel("Year");

		labelSum = new JLabel("Enter rent amount:");
		tfSum = new JTextField(20);

		labelInf = new JLabel("Enter number of receipt order:");
		tfInf = new JTextField(20);

		enter = new JButton("Enter");
		cancel = new JButton("Cancel");

		comboSelect = new JComboBox<String>(select);
		comboSelect.setPreferredSize(new Dimension(180, 20));

		resetFrame();

		panel.add(labelDate);
		panel.add(tfDate);
		panel.add(labelNumber);
		panel.add(comboNum);
		panel.add(labelName);
		panel.add(tfName);
		panel.add(labelQuarter);
		panel.add(labelYear);
		panel.add(quart1);
		panel.add(quart2);
		panel.add(quart3);
		panel.add(quart4);
		panel.add(labelSum);
		panel.add(tfSum);
		panel.add(labelInf);
		panel.add(tfInf);
		panel.add(enter);
		panel.add(cancel);
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
					String quarter = ""; // first month of year quarter

					if (quart1.isEnabled() && quart1.isSelected())
						quarter = "01";
					else if (quart2.isEnabled() && quart2.isSelected())
						quarter = "04";
					else if (quart3.isEnabled() && quart3.isSelected())
						quarter = "07";
					else if (quart4.isEnabled() && quart4.isSelected())
						quarter = "10";

					BigDecimal sum = new BigDecimal(tfSum.getText());
					sum = sum.setScale(2, RoundingMode.FLOOR);
					
					if (comboNum.getSelectedIndex() == 0 || quarter.equals("")) {
						throw new NumberFormatException("e");
					}
					RentDao daoRent = new RentDao();
					daoRent.insert(new Rent(
							comboNum.getSelectedIndex(),
							InitDB.stringToDate(tfDate.getText()),
							InitDB.stringToDate("01-" + quarter + "-" + labelYear.getText()),
							sum,
							tfInf.getText()));

					comboNum.setSelectedIndex(0);
					resetFrame();
					JOptionPane.showMessageDialog(panel, "The payment has been successfully included", "Message",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "select storage, quarter and enter the right amount", "",
							JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {
					comboNum.setSelectedIndex(0);
					resetFrame();
					JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				updateFrame();
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

		if (comboNum.getSelectedIndex() == 0) {
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
					tfSum.setEnabled(true);
					tfInf.setEnabled(true);
					enter.setEnabled(true);
					cancel.setEnabled(true);
				}
			} catch (Exception e) {
				comboNum.setSelectedIndex(0);
				resetFrame();
				JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
			}
		}
		panel.updateUI();
	}

	private void resetFrame() {

		tfName.setText("");
		quart1.setEnabled(false);
		quart1.setSelected(false);
		quart2.setEnabled(false);
		quart2.setSelected(false);
		quart3.setEnabled(false);
		quart3.setSelected(false);
		quart4.setEnabled(false);
		quart4.setSelected(false);
		labelYear.setText("");
		tfSum.setText("");
		tfSum.setEnabled(false);
		tfInf.setText("");
		tfInf.setEnabled(false);
		enter.setEnabled(false);
		cancel.setEnabled(false);
	}

}
