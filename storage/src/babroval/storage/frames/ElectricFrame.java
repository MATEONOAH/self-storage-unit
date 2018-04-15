package babroval.storage.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import babroval.storage.mysql.ConnectionPool;

public class ElectricFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNum, labelDate, labelName, labelIndicationLastPaid, labelIndication, labelInf;
	private JComboBox<String> comboNum, comboSelect;
	private JTextField fieldDate, fieldName, fieldIndication, fieldIndicationLastPaid, fieldInf;
	private JButton enter;
	private String[] select = { "select:", "RENT PAYMENT", "MAIN VIEW" };

	public ElectricFrame() {
		setSize(300, 318);
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
		fieldDate = new JTextField(sdf.format(dateNow));
		
		labelNum = new JLabel("Select Number of storage:");
		comboNum = new JComboBox<String>();
		comboNum.setPreferredSize(new Dimension(50, 20));
		
		labelName = new JLabel("Name of tenant:");
		fieldName = new JTextField(20);
		fieldName.setEnabled(false);

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
		fieldIndicationLastPaid = new JTextField(20);
		fieldIndicationLastPaid.setEnabled(false);
		
		labelIndication = new JLabel("Enter electric power indication:");
		fieldIndication = new JTextField(20);

		labelInf = new JLabel("Enter number of receipt order:");
		fieldInf = new JTextField(20);

		enter = new JButton("Enter");

		comboSelect = new JComboBox<String>(select);
		resetFrame();

		panel.add(labelDate);
		panel.add(fieldDate);
		panel.add(labelNum);
		panel.add(comboNum);
		panel.add(labelName);
		panel.add(fieldName);
		panel.add(labelIndicationLastPaid);
		panel.add(fieldIndicationLastPaid);
		panel.add(labelIndication);
		panel.add(fieldIndication);
		panel.add(labelInf);
		panel.add(fieldInf);
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
					if (fieldInf.getText().equals("")) {
						fieldInf.setText("");
					}
					if (comboNum.getSelectedIndex() == 0 || comboNum.getSelectedItem().equals("")) {
						throw new NumberFormatException("e");
					}

					JOptionPane.showMessageDialog(panel, "Payment has been included", "Message",
							JOptionPane.INFORMATION_MESSAGE);

					comboNum.setSelectedIndex(0);
					fieldName.setText("");
					fieldIndication.setText("");
					fieldIndicationLastPaid.setText("");
					fieldInf.setText("");

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
			resetFrame();
			try (Connection cn = ConnectionPool.getPool().getConnection();
					Statement st = cn.createStatement();
					ResultSet rs = st.executeQuery("SELECT user.name, MAX(electric.meter_paid)"
							+ " FROM electric, storage, user"
							+ " WHERE storage.number='" + comboNum.getSelectedItem()
							+ "' AND electric.storage_id=storage.storage_id"
							+ " AND user.storage_id=storage.storage_id")) {

				while (rs.next()) {

					fieldName.setText(rs.getString(1));
					fieldIndicationLastPaid.setText(rs.getString(2));
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
			}
		}
		panel.updateUI();
	}

	private void resetFrame() {
		fieldName.setText("");
		fieldIndicationLastPaid.setText("");
		fieldIndication.setText("");
		fieldInf.setText("");
	}

}