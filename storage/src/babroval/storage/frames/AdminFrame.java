package babroval.storage.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import babroval.storage.dao.RentDao;
import babroval.storage.dao.UserDao;
import babroval.storage.entity.User;
import babroval.storage.mysql.ConnectionPool;
import babroval.storage.mysql.InitDB;

public class AdminFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JComboBox<String> comboRead, comboEdit, comboPayment, comboNum, comboYear;
	private JLabel labelComboNum, labelQuarts;
	private JScrollPane scroll;
	private JButton add, delete, edit, editPrihodOrder, sortFamily, debtors;
	private JCheckBox quart1, quart2, quart3, quart4;
	private ButtonGroup group;
	private TableStorage tableUsers;
	private JMenuBar menuBar;
	private JMenuItem itemWrite, itemAbout, itemExit;
	private JMenu file, about;
	private JFileChooser chooser;
	private String[] selectRead = { "select:", "RENT PAYMENT", "ELECTRYCITY PAYMENT", "TENANTS" };
	private String[] selectEdit = { "edit:", "RENT PAYMENT", "ELECTRYCITY PAYMENT", "TENANTS" };
	private String[] selectPayment = { "select payment:", "RENT", "ELECTRYCITY" };
	String columnNames[] = { "Storage number", "Owner", "Private info" };

	public AdminFrame() {
		setSize(995, 550);
		setTitle("AdminFrame");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
		setResizable(false);
	}

	private void initComponents() {

		panel = new JPanel(null);

		chooser = new JFileChooser();
		menuBar = new JMenuBar();
		file = new JMenu("File");
		about = new JMenu("About");
		itemExit = new JMenuItem("Exit");
		itemWrite = new JMenuItem("Save as *.xls");
		itemAbout = new JMenuItem("About");

		file.add(itemWrite);
		file.add(itemExit);
		about.add(itemAbout);
		menuBar.add(file);
		menuBar.add(about);
		setJMenuBar(menuBar);

		comboRead = new JComboBox<String>(selectRead);
		comboEdit = new JComboBox<String>(selectEdit);
		comboPayment = new JComboBox<String>(selectPayment);

		labelComboNum = new JLabel("Rent payment for storage:");
		comboNum = new JComboBox<String>();

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage.storage_number" + " FROM storage"
						+ " WHERE storage.storage_number!=0" + " ORDER BY storage.storage_number ASC")) {

			comboNum.addItem("");
			while (rs.next()) {
				comboNum.addItem(rs.getString(1));
			}
			tableUsers = new TableStorage(rs);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
		}

		scroll = new JScrollPane(tableUsers);
		add = new JButton("Add");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		editPrihodOrder = new JButton("Save receipt order");
		add.setEnabled(false);
		edit.setEnabled(false);
		delete.setEnabled(false);
		editPrihodOrder.setEnabled(false);
		itemWrite.setEnabled(false);

		sortFamily = new JButton("Sort by last name");

		labelQuarts = new JLabel("Quarters of the year");
		quart1 = new JCheckBox("I");
		quart2 = new JCheckBox("II");
		quart3 = new JCheckBox("III");
		quart4 = new JCheckBox("IV");
		group = new ButtonGroup();

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int i = Integer.parseInt(sdf.format(today));
		String[] year = { String.valueOf(i - 1), String.valueOf(i), String.valueOf(i + 1) };
		comboYear = new JComboBox<String>(year);
		comboYear.setSelectedIndex(1);

		debtors = new JButton("Debtors");

		sortFamily.setVisible(false);

		comboRead.setBounds(30, 10, 160, 20);
		labelComboNum.setBounds(200, 10, 150, 20);
		comboNum.setBounds(352, 10, 70, 20);
		sortFamily.setBounds(430, 10, 140, 20);
		quart1.setBounds(602, 10, 30, 20);
		quart2.setBounds(630, 10, 32, 20);
		quart3.setBounds(660, 10, 35, 20);
		quart4.setBounds(692, 10, 40, 20);
		comboYear.setBounds(732, 10, 60, 20);
		debtors.setBounds(800, 10, 95, 20);
		scroll.setBounds(20, 40, 950, 390);
		comboEdit.setBounds(30, 450, 160, 20);
		add.setBounds(205, 450, 110, 20);
		edit.setBounds(325, 450, 110, 20);
		delete.setBounds(445, 450, 110, 20);
		editPrihodOrder.setBounds(565, 450, 110, 20);
		comboPayment.setBounds(695, 450, 130, 20);

		group.add(quart1);
		group.add(quart2);
		group.add(quart3);
		group.add(quart4);

		panel.add(comboRead);
		panel.add(labelComboNum);
		panel.add(comboNum);
		panel.add(sortFamily);
		panel.add(labelQuarts);
		panel.add(quart1);
		panel.add(quart2);
		panel.add(quart3);
		panel.add(quart4);
		panel.add(comboYear);
		panel.add(debtors);
		panel.add(comboEdit);
		panel.add(add);
		panel.add(edit);
		panel.add(delete);
		panel.add(editPrihodOrder);
		panel.add(comboPayment);

		add(panel);
	}

	private void action() {

		itemWrite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int x = chooser.showSaveDialog(panel);
				if (x == 0) {
					String allRow = "";
					for (int j = 0; j < tableUsers.getColumnCount(); j++) {
						allRow = allRow + tableUsers.getColumnName(j) + "\t";
					}
					allRow = allRow + "\n";
					for (int i = 0; i < tableUsers.getRowCount(); i++) {
						for (int j = 0; j < tableUsers.getColumnCount(); j++) {
							allRow = allRow + tableUsers.getValueAt(i, j) + "\t";
						}
						allRow = allRow + "\n";
					}

					try (FileWriter fw = new FileWriter(chooser.getSelectedFile())) {

						fw.write(allRow);
						JOptionPane.showMessageDialog(panel, "Saved");
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(panel, "save error");
					}
				}
			}
		});

		itemAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panel, "Storage");
			}
		});

		itemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		comboRead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				itemWrite.setEnabled(true);
				add.setEnabled(false);
				edit.setEnabled(false);
				delete.setEnabled(false);
				editPrihodOrder.setEnabled(false);

				if (comboRead.getSelectedIndex() == 0) {
					sortFamily.setVisible(false);
					editPrihodOrder.setEnabled(false);
					panel.remove(scroll);
					panel.updateUI();
				}
				if (comboRead.getSelectedIndex() == 1) {

					sortFamily.setVisible(false);
					editPrihodOrder.setEnabled(true);
					showTable(
							"SELECT rent.date, storage.storage_number, rent.quarter_paid, rent.sum, rent.info"
									+ " FROM storage, rent" + " WHERE storage.storage_id=rent.storage_id "
									+ " AND rent.date!=0 ORDER BY rent.rent_id ASC");
				}
				if (comboRead.getSelectedIndex() == 2) {
					sortFamily.setVisible(false);
					editPrihodOrder.setEnabled(false);
					showTable(
							"SELECT electric.date, storage.storage_number, electric.tariff, electric.meter_paid, electric.sum, electric.info"
									+ " FROM storage, electric" + " WHERE storage.storage_id=electric.storage_id"
									+ " AND electric.date!=0 ORDER BY electric.electric_id ASC");
				}
				if (comboRead.getSelectedIndex() == 3) {

					sortFamily.setVisible(true);
					editPrihodOrder.setEnabled(false);

					showTable("SELECT storage.storage_number, user.name, user.info" + " FROM storage, user"
							+ " WHERE storage.storage_id=user.storage_id"
							+ " AND storage.storage_number!=0 ORDER BY storage.storage_number ASC");
				}
			}
		});

		comboNum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				sortFamily.setVisible(false);

				itemWrite.setEnabled(true);
				add.setEnabled(false);
				edit.setEnabled(false);
				delete.setEnabled(false);
				editPrihodOrder.setEnabled(false);
				panel.remove(scroll);

				try (Connection cn = ConnectionPool.getPool().getConnection();
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery(
								"SELECT rent.date, rent.quarter_paid, rent.sum, rent.info"
								+ " FROM storage, rent" 
								+ " WHERE storage.storage_id=rent.storage_id"
								+ " AND storage.storage_number='" + comboNum.getSelectedItem() + "'"
								+ " AND rent.date!=0"
								+ " ORDER BY rent.date ASC")) {

					tableUsers = new TableStorage(rs);
					scroll = new JScrollPane(tableUsers);
					scroll.setBounds(20, 40, 950, 390);
					panel.add(scroll);
					panel.updateUI();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		sortFamily.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				itemWrite.setEnabled(true);
				showTable("SELECT storage.storage_number, user.name, user.info" + " FROM storage, user"
						+ " WHERE storage.storage_id=user.storage_id" + " AND storage.storage_number!=0"
						+ " ORDER BY user.name ASC");
			}
		});

		debtors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				editPrihodOrder.setEnabled(false);
				panel.remove(scroll);

				try {
					String quarter = ""; // first month of year quarter
					if (quart1.isSelected()) quarter = "01";
					else if (quart2.isSelected()) quarter = "04";
					else if (quart3.isSelected()) quarter = "07";
					else if (quart4.isSelected()) quarter = "10";
					else throw new NumberFormatException();

					showTable(
						"SELECT MAX(rent.quarter_paid), storage.storage_number, user.name, user.info"
						+ " FROM storage, user, rent"
						+ " WHERE rent.quarter_paid<'"+ comboYear.getSelectedItem() +"-"+ quarter +"-01"+"'"
						+ " AND rent.storage_id=storage.storage_id"
						+ " AND user.storage_id=storage.storage_id"
    					+ " AND rent.date!=0"
    					+ " GROUP BY storage.storage_id"
    					+ " ORDER BY rent.quarter_paid ASC");

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "specify quarter", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		comboEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				sortFamily.setVisible(false);
    			itemWrite.setEnabled(false);
				panel.remove(scroll);

				if (comboEdit.getSelectedIndex() == 0) {
					add.setEnabled(false);
					edit.setEnabled(false);
					delete.setEnabled(false);
					editPrihodOrder.setEnabled(false);

					panel.remove(scroll);
					panel.updateUI();
				}
				if (comboEdit.getSelectedIndex() == 1) {
					add.setEnabled(false);
					edit.setEnabled(false);
					delete.setEnabled(true);
					editPrihodOrder.setEnabled(false);
					showTable("SELECT rent.rent_id, storage.storage_number, rent.date,"
							+ " rent.quarter_paid, rent.sum, rent.info"
							+ " FROM storage, rent"
							+ " WHERE storage.storage_id=rent.storage_id"
							+ " ORDER BY rent.rent_id ASC");
				}
				if (comboEdit.getSelectedIndex() == 2) {
					add.setEnabled(false);
					edit.setEnabled(false);
					delete.setEnabled(true);
					editPrihodOrder.setEnabled(false);
					showTable("SELECT electric.electric_id, storage.storage_number, electric.date,"
							+ " electric.tariff, electric.meter_paid, electric.sum, electric.info"
							+ " FROM electric, storage"
							+ " WHERE electric.storage_id=storage.storage_id"
							+ " ORDER BY electric.electric_id ASC");
				}
				if (comboEdit.getSelectedIndex() == 3) {
					add.setEnabled(true);
					edit.setEnabled(true);
					delete.setEnabled(false);
					editPrihodOrder.setEnabled(false);
					showTable("SELECT storage.storage_number, user.name, user.info"
							+ " FROM user, storage"
							+ " WHERE user.storage_id=storage.storage_id"
							+ " ORDER BY storage.storage_number ASC");

				}

			}
		});

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					tableUsers.selectAll();
					int result = JOptionPane.showConfirmDialog(panel, "Add storage?", "Delete",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {

						// UserDao daoUser = new UserDao();
						// daoUser.insert(new User(
						// Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(),
						// 0).toString()), "",
						// "", "", "", "", "", "", ""));
						JOptionPane.showMessageDialog(panel, "Storage has been added");
				//		refreshTableUserEdit();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					String quarter1 = "";
					String quarter2 = "";
					String quarter3 = "";
					String quarter4 = "";
					String temp = "";
					int result = JOptionPane.showConfirmDialog(panel, "Delete rent payment?", "Delete",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						String buff = "";

						if (comboEdit.getSelectedIndex() == 1) {
							if (tableUsers.getValueAt(tableUsers.getSelectedRow(), 3).toString().equals("0")) {
								throw new ArrayIndexOutOfBoundsException("ex");
							}

							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery("SELECT MAX(orders.order_id)"
											+ " FROM orders WHERE orders.summ!=0 AND orders.order_id!="
											+ Integer.valueOf(
													tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString())
											+ " AND orders.storage_id="
											+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 4).toString())) {

								while (rs.next()) {
									temp = rs.getString(1);
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
							}

							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery(
											"SELECT orders.quarter1 FROM orders WHERE orders.order_id=" + temp)) {
								while (rs.next()) {
									String j = rs.getString(1);
									if (j == null) {
										j = "";
									}
									quarter1 = j;
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
							}

							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery(
											"SELECT orders.quarter2" + " FROM orders WHERE orders.order_id=" + temp)) {

								while (rs.next()) {
									String j = rs.getString(1);
									if (j == null) {
										j = "";
									}
									quarter2 = j;
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
							}

							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery(
											"SELECT orders.quarter3 FROM orders WHERE orders.order_id=" + temp)) {

								while (rs.next()) {
									String j = rs.getString(1);
									if (j == null) {
										j = "";
									}

									quarter3 = j;
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
							}

							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery(
											"SELECT orders.quarter4 FROM orders WHERE orders.order_id=" + temp)) {
								while (rs.next()) {
									String j = rs.getString(1);
									if (j == null) {
										j = "";
									}
									quarter4 = j;
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
							}

							// UserDao daoUser = new UserDao();
							// daoUser.deleteQuarters(new User(
							// Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(),
							// 1).toString()),
							// quarter1, quarter2, quarter3, quarter4));

							buff = (tableUsers.getValueAt(tableUsers.getSelectedRow(), 2).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 3).toString() + "//"
									+ Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 4).toString())
									+ "//" + tableUsers.getValueAt(tableUsers.getSelectedRow(), 5).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 6).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 7).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 8).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 9).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 10).toString());

							RentDao daoOrder = new RentDao();
							// daoOrder.update(new Orders(
							// Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(),
							// 0).toString()),
							// Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(),
							// 1).toString()),
							// "0", 0, "", "", "", "", buff));

							JOptionPane.showMessageDialog(panel, "Rent payment has been deleted");
					//		refreshTableRent();
						}
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(panel,
							"Rent payment has not been selected or payment has been already deleted", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex2) {
					throw new RuntimeException(ex2);
				}
			}
		});

		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					

					 UserDao daoUser = new UserDao();
					 daoUser.update(
						 new User(Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(),0).toString()),
								  Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(),0).toString()),
								  tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString(),
								  tableUsers.getValueAt(tableUsers.getSelectedRow(), 2).toString()));
		 showTable("SELECT storage.storage_number, user.name, user.info"
								+ " FROM user, storage"
								+ " WHERE user.storage_id=storage.storage_id"
								+ " ORDER BY storage.storage_number ASC");
					
		 JOptionPane.showMessageDialog(panel, "Owner has been saved");
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(panel, "select storage", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		editPrihodOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// updating info
					// RentDao daoOrder = new RentDao();
					// daoOrder.updateInfo(new Rent(
					// Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(),
					// 0).toString()),
					// tableUsers.getValueAt(tableUsers.getSelectedRow(), 9).toString()));
					showTable(
							"SELECT orders.order_id, users.number_storage, orders.date, orders.summ, orders.quarter1, orders.quarter2, orders.quarter3, orders.quarter4, orders.year, orders.info"
									+ " FROM users, orders WHERE orders.storage_id=users.storage_id AND orders.date!=0 ORDER BY orders.date ASC");
					JOptionPane.showMessageDialog(panel, "Receipt order has been saved");

				} catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(panel, "select receipt order", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		comboPayment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				comboRead.setSelectedIndex(0);
				comboEdit.setSelectedIndex(0);

				if (comboPayment.getSelectedIndex() == 1) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new RentFrame();
						}
					});
					dispose();
				}

				if (comboPayment.getSelectedIndex() == 2) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new ElectricFrame();
						}
					});
					dispose();
				}
			}
		});

	}

	protected void showTable(String query) {

		panel.remove(scroll);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(query)) {

			tableUsers = new TableStorage(rs);

			scroll = new JScrollPane(tableUsers);
			scroll.setBounds(20, 40, 950, 390);
			panel.add(scroll);
			panel.updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
		}
	}

}
