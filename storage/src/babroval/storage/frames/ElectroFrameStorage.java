package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import babroval.storage.mysql.ConnectionPool;

public class ElectroFrameStorage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JScrollPane scroll;
	private JLabel labelTermRent, imageLabel;
	private JTextField tfTermRent;
	private JButton select;
	private TableStorage tableCars;
	private int user_id;

	public ElectroFrameStorage(int user_id) {
		this.user_id = user_id;
		setSize(500, 450);
		setTitle("OrderFrame");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
		setResizable(false);

	}

	private void initComponents() {

		panel = new JPanel(null);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st
						.executeQuery("SELECT car_id, name_car, color, price_per_day"
										+ " FROM cars WHERE availability=1")) {

			tableCars = new TableStorage(rs);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "database", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		scroll = new JScrollPane(tableCars);
		labelTermRent = new JLabel("Select car and term of rent in days");
		tfTermRent = new JTextField("1");
		select = new JButton("Select");
		imageLabel = new JLabel();
		scroll.setBounds(20, 20, 450, 100);
		labelTermRent.setBounds(40, 150, 200, 20);
		tfTermRent.setBounds(240, 150, 50, 20);
		select.setBounds(365, 150, 100, 20);

		panel.add(scroll);
		panel.add(labelTermRent);
		panel.add(tfTermRent);
		panel.add(select);
		add(panel);
	}

	private void action() {

		tableCars.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				panel.remove(imageLabel);
				ImageIcon image = new ImageIcon("D:\\images\\"
						+ Integer.valueOf(tableCars.getValueAt(tableCars.getSelectedRow(), 0).toString()) + ".jpg");
				imageLabel.setIcon(image);
				imageLabel.setBounds(20, 200, 300, 200);
				panel.add(imageLabel);
				panel.updateUI();
			}
		});

		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try (Connection cn = ConnectionPool.getPool().getConnection();
						Statement st = cn.createStatement();
						ResultSet rs = st
								.executeQuery("SELECT reject_description,  damage_description " 
										+ "FROM orders WHERE user_id=" 
										+ user_id 
										+ " AND reject_description<>'no reject'")) {

					if (rs.next()) {
						JOptionPane.showMessageDialog(panel, "Your order is rejected. The reason is " + rs.getString(2),
								"Not Accepted", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						int totalCoast = Integer.valueOf(tfTermRent.getText())
								* Integer.valueOf(tableCars.getValueAt(tableCars.getSelectedRow(), 3).toString());

						JOptionPane.showMessageDialog(panel, "Total coast is " + totalCoast, "Accepted",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(panel, "Select error", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(panel, "Please,select the car", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "Database", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

}
