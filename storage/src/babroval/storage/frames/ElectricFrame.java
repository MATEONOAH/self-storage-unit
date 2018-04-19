package babroval.storage.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import babroval.storage.dao.ElectricDao;
import babroval.storage.dao.RentDao;
import babroval.storage.entity.Rent;
import babroval.storage.mysql.ConnectionPool;
import babroval.storage.mysql.InitDB;

public class ElectricFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNum, labelDate, labelName, labelIndicationLastPaid, labelTariff, labelIndication, labelSum, labelInf;
	private JComboBox<String> comboNum, comboSelect;
	private JTextField tfDate, tfName, tfIndication, tfIndicationLastPaid, tfTariff, tfSum, tfInf;
	private JButton enter, calculate;
	private String[] select = { "select:", "RENT PAYMENT", "MAIN VIEW" };

	public ElectricFrame() {
		setSize(300, 440);
		setTitle("Electricity payment");
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
		
		labelNum = new JLabel("Select Number of storage:");
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
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
		}

		labelIndicationLastPaid = new JLabel("Electric power indication last paid:");
		tfIndicationLastPaid = new JTextField(20);
		tfIndicationLastPaid.setEnabled(false);
		
		labelTariff = new JLabel("Price per kilowatt-hour:");
		tfTariff = new JTextField(20);
		
		labelIndication = new JLabel("<html>Enter electric power indication and "
								   + "<br>press button \"Calculate\"</html>");
		tfIndication = new JTextField(20);
		
		calculate = new JButton("Calculate");

		labelSum = new JLabel("Total amount:");
		tfSum = new JTextField(20);
		tfSum.setEnabled(false);
		
		labelInf = new JLabel("Enter number of receipt order:");
		tfInf = new JTextField(20);

		enter = new JButton("Enter");

		comboSelect = new JComboBox<String>(select);
		resetFrame();

		panel.add(labelDate);
		panel.add(tfDate);
		panel.add(labelNum);
		panel.add(comboNum);
		panel.add(labelName);
		panel.add(tfName);
		panel.add(labelIndicationLastPaid);
		panel.add(tfIndicationLastPaid);
		panel.add(labelTariff);
		panel.add(tfTariff);
		panel.add(labelIndication);
		panel.add(tfIndication);
		panel.add(calculate);
		panel.add(labelSum);
		panel.add(tfSum);
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
		
		calculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					Integer indicationLastPaid = new Integer(tfIndicationLastPaid.getText());
					Integer indication = new Integer(tfIndication.getText());
					BigDecimal kW = new BigDecimal(String.valueOf(indication - indicationLastPaid));
					BigDecimal tariff = new BigDecimal(tfTariff.getText());
					BigDecimal sum = kW.multiply(tariff) ;
					sum = sum.setScale(2, RoundingMode.HALF_UP);
					
					tfSum.setText(String.valueOf(sum));	
					tfInf.setEnabled(true);
					enter.setEnabled(true);
					
					panel.updateUI();
					
				} catch (NumberFormatException e) {
					
					tfTariff.setText("");
					tfIndication.setText("");
					JOptionPane.showMessageDialog(panel,
							"enter the right price per kilowatt and the right indication",	"",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					comboNum.setSelectedIndex(0);
					resetFrame();
					JOptionPane.showMessageDialog(panel, 
							"database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {

					if (comboNum.getSelectedIndex() == 0 || comboNum.getSelectedItem().equals("")) {
						throw new NumberFormatException("e");
					}

					JOptionPane.showMessageDialog(panel, "Payment has been included", "Message",
							JOptionPane.INFORMATION_MESSAGE);

					comboNum.setSelectedIndex(0);
					tfName.setText("");
					tfIndicationLastPaid.setText("");
					tfTariff.setText("");
					tfIndication.setText("");
					tfSum.setText("");
					tfInf.setText("");

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "Select storage and electric power indication", "error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		comboSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (comboSelect.getSelectedIndex() == 1) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new RentFrame();
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
			try (Connection cn = ConnectionPool.getPool().getConnection();
					Statement st = cn.createStatement();
					ResultSet rs = st.executeQuery("SELECT user.name, MAX(electric.meter_paid), electric.tariff"
							+ " FROM electric, storage, user"
							+ " WHERE storage.number='" + comboNum.getSelectedItem()
							+ "' AND electric.storage_id=storage.storage_id"
							+ " AND user.storage_id=storage.storage_id")) {

				while (rs.next()) {

					tfName.setText(rs.getString(1));
					tfIndicationLastPaid.setText(rs.getString(2));
					tfTariff.setText(rs.getString(3));
				}
				
				tfTariff.setEnabled(true);
				tfIndication.setEnabled(true);
				calculate.setEnabled(true);
			} catch (SQLException e) {
				comboNum.setSelectedIndex(0);
				resetFrame();
				JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
			}
		}
		panel.updateUI();
	}

	private void resetFrame() {
		tfName.setText("");
		tfIndicationLastPaid.setText("");
		tfTariff.setText("");
		tfTariff.setEnabled(false);
		tfIndication.setText("");
		tfIndication.setEnabled(false);
		calculate.setEnabled(false);
		tfSum.setText("");
		tfSum.setEnabled(false);
		tfInf.setText("");
		tfInf.setEnabled(false);
		enter.setEnabled(false);
	}

}