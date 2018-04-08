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
	private JLabel labelNumber, labelD, labelIndicationLastPaid, labelIndication, labelInf;
	private JComboBox<String> comboNum;
	private JComboBox<Object> comboOrder;
	private JTextField fieldDate, tfName, tfIndication, tfIndicationLast, tfInf;
	private JButton enter;
	private String[] en = { "select", "rent", "admin" };

	public ElectricFrame() {
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

		Date dateNow = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		fieldDate = new JTextField(sdf.format(dateNow));

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
					JOptionPane.showMessageDialog(panel, "Select storage and electric power indication", "error",
							JOptionPane.ERROR_MESSAGE);
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
							new RentFrame();
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

	private void numUpdateElectroFrame() {
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT users.number_storage, users.name, MAX(electro.new_num)"
						+ " FROM users, electro WHERE electro.storage_id=users.storage_id AND users.number_storage='"+ comboNum.getSelectedItem() + "'")) {

			    while (rs.next()) {
				
						tfName.setText(rs.getString(2));
						tfIndicationLast.setText(rs.getString(3));
			    }
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "database error", "error", JOptionPane.ERROR_MESSAGE);
		}
		panel.updateUI();
	}

}