package babroval.storage.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import babroval.storage.dao.Dao;
import babroval.storage.dao.ElectricDaoImpl;
import babroval.storage.dao.StorageDaoImpl;
import babroval.storage.dao.UserDaoImpl;
import babroval.storage.entity.Electric;
import babroval.storage.entity.Storage;
import babroval.storage.entity.User;
import babroval.storage.mysql.InitDB;

public class ElectricFrame extends JFrame {
 
	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNum, labelDate, labelName, labelIndicationLastPaid,
				   labelTariff, labelIndication, labelSum,	labelInf;
	private JComboBox<String> comboNum, comboSelect;
	private JTextField tfDate, tfName, tfIndication, tfIndicationLastPaid, tfTariff, tfSum, tfInf;
	private JButton calculate, enter, cancel;
	private String[] select = { "select:", "RENT PAYMENT", "MAIN VIEW" };
	private Integer indication = 0 ;
	private BigDecimal tariff = new BigDecimal("0");
	private BigDecimal sum = new BigDecimal("0");

	{
		panel = new JPanel();

		labelDate = new JLabel("Date of payment:");
		Date dateNow = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		tfDate = new JTextField(sdf.format(dateNow));

		labelNum = new JLabel("Select Number of storage:");
		comboNum = new JComboBox<String>();
		comboNum.setPreferredSize(new Dimension(50, 20));
		comboNum.addItem("");

		labelName = new JLabel("Name of tenant:");
		tfName = new JTextField(20);
		tfName.setEnabled(false);

		List<String> allStoragesNumbers = new ArrayList<String>();
		
		Dao<Storage> daoStorage = new StorageDaoImpl();
		allStoragesNumbers = daoStorage.loadAllNumbers();

		for (String storageNum : allStoragesNumbers) {
			comboNum.addItem(storageNum);
		}

		labelIndicationLastPaid = new JLabel("Electric power indication last paid:");
		tfIndicationLastPaid = new JTextField(20);
		tfIndicationLastPaid.setEnabled(false);

		labelTariff = new JLabel("Price per kilowatt-hour:");
		tfTariff = new JTextField(20);

		labelIndication = new JLabel(
			"<html>Enter electric power indication and "
		  + "<br>press button \"Calculate\":</html>");
		tfIndication = new JTextField(20);

		calculate = new JButton("Calculate");

		labelSum = new JLabel("Total amount:");
		tfSum = new JTextField(20);
		tfSum.setEnabled(false);

		labelInf = new JLabel("Enter number of receipt order:");
		tfInf = new JTextField(20);

		enter = new JButton("Enter");
		cancel = new JButton("Cancel");

		comboSelect = new JComboBox<String>(select);
		comboSelect.setPreferredSize(new Dimension(115, 20));

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
		panel.add(cancel);
		panel.add(comboSelect);

		add(panel);
	}
	
	public ElectricFrame() {
		setSize(270, 460);
		setTitle("Electricity payment");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		resetFrame();
		action();
		setVisible(true);
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
		cancel.setEnabled(false);
	}
	
	private void action() {

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
					indication = new Integer(tfIndication.getText());
					tariff = new BigDecimal(tfTariff.getText());
					BigDecimal kWh = new BigDecimal(String.valueOf(indication - indicationLastPaid));

					if (tariff.compareTo(new BigDecimal("0")) <= 0 || kWh.compareTo(new BigDecimal("0")) <= 0) {
						throw new NumberFormatException("e");
					}

					sum = kWh.multiply(tariff) ;
					sum = sum.setScale(2, RoundingMode.HALF_UP);

					tfSum.setText(sum.toString());
					tfInf.setEnabled(true);
					enter.setEnabled(true);
					tfTariff.setEnabled(false);
					tfTariff.setEnabled(false);
					tfIndication.setEnabled(false);
					calculate.setEnabled(false);
					cancel.setEnabled(true);

					panel.updateUI();

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel,
							"enter the right price per kilowatt-hour and the right indication", "",
							JOptionPane.ERROR_MESSAGE);
					updateFrame();

				} catch (Exception e) {
					comboNum.setSelectedIndex(0);
					resetFrame();
					JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				
				try {
					
					if (comboNum.getSelectedIndex() == 0) {
						throw new NumberFormatException("e");
					}
					
					Dao<Electric> daoElectric = new ElectricDaoImpl();
					Dao<Storage> daoStorage = new StorageDaoImpl();
					
					daoElectric.insert(new Electric(
							daoStorage.loadIdByStorageNumber(String.valueOf(comboNum.getSelectedItem())),
							InitDB.stringToDate(tfDate.getText(), "dd-MM-yyyy"),
							tariff,
							indication,
							sum,
							tfInf.getText()));
					
					comboNum.setSelectedIndex(0);
					resetFrame();
					JOptionPane.showMessageDialog(panel, "The payment has been successfully included", "Message",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "Select storage and electric power indication", "",
							JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {
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

	}

	private void updateFrame() {

		if (comboNum.getSelectedIndex() == 0) {
			resetFrame();
		} else {
			resetFrame();
			try {
				Dao<User> daoUser = new UserDaoImpl();
				String userName = daoUser
						.loadNameByStorageNumber(String.valueOf(comboNum.getSelectedItem()));
				tfName.setText(userName);
				
				Dao<Electric> daoElectric = new ElectricDaoImpl();
				Electric electric = daoElectric
						.loadLastPaidByStorageNumber(String.valueOf(comboNum.getSelectedItem()));
				tfIndicationLastPaid.setText(String.valueOf(electric.getMeter_paid()));
				tfTariff.setText(electric.getTariff().toString());

				tfTariff.setEnabled(true);
				tfIndication.setEnabled(true);
				calculate.setEnabled(true);
			} catch (Exception e) {
				comboNum.setSelectedIndex(0);
				resetFrame();
				JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
			}
		}
		panel.updateUI();
	}

}