package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import babroval.storage.dao.OrdersStorageDao;
import babroval.storage.dao.UsersStorageDao;
import babroval.storage.entity.Orders;
import babroval.storage.entity.Users;
import babroval.storage.mysql.ConnectionPool;

public class AdminFrameStorage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JComboBox<Object> combo;
	private String[] es = { "edit", "rent payment", "electricity", "owners" };
	private JComboBox<Object> comboRead;
	private String[] read = { "select", "rent payment", "electricity", "owners" };
	private JLabel labelComboNum;
	private JComboBox<String> comboNum;
	private JScrollPane scroll;
	private JButton add;
	private JButton delete;
	private JButton edit;
	private JButton editPrihodOrder;
	private JComboBox<Object> comboOrder;
	private String[] en = { "enter", "rent payment", "electricity" };
	private JButton sortFamily;
	private JLabel labelQuarts;
	private JCheckBox quart1;
	private JCheckBox quart2;
	private JCheckBox quart3;
	private JCheckBox quart4;
	private ButtonGroup group;
	private JComboBox<String> comboYear;
	private JButton notPaid;
	private TableStorage tableUsers;
	private JMenuBar menuBar;
	private JMenuItem itemWrite, itemAbout, itemExit;
	private JMenu file, about;
	private JFileChooser chooser;
	String columnNames[] = { "Storage number", "Owner", "Private info" };

	public AdminFrameStorage() {
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

		combo = new JComboBox<Object>(es);
		comboRead = new JComboBox<Object>(read);
		comboOrder = new JComboBox<Object>(en);
		labelComboNum = new JLabel("Rent payment of storage number :");
		comboNum = new JComboBox<String>();

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM users")) {

			comboNum.addItem("");

			while (rs.next()) {
				comboNum.addItem(rs.getString(2));
			}
			tableUsers = new TableStorage(rs);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "database error", "Error", JOptionPane.ERROR_MESSAGE);
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

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

		ft = new SimpleDateFormat("yyyy");
		int i = Integer.parseInt(ft.format(dNow));
		String[] year = { String.valueOf(i - 1), String.valueOf(i), String.valueOf(i + 1) };
		comboYear = new JComboBox<String>(year);
		comboYear.setSelectedIndex(1);

		notPaid = new JButton("debtors");

		sortFamily.setVisible(false);

		comboRead.setBounds(30, 10, 120, 20);
		labelComboNum.setBounds(200, 10, 130, 20);
		comboNum.setBounds(317, 10, 70, 20);
		sortFamily.setBounds(430, 10, 140, 20);
		quart1.setBounds(602, 10, 30, 20);
		quart2.setBounds(630, 10, 32, 20);
		quart3.setBounds(660, 10, 35, 20);
		quart4.setBounds(692, 10, 40, 20);
		comboYear.setBounds(732, 10, 60, 20);
		notPaid.setBounds(800, 10, 95, 20);
		scroll.setBounds(20, 40, 950, 390);
		combo.setBounds(30, 450, 120, 20);
		add.setBounds(160, 450, 110, 20);
		edit.setBounds(280, 450, 110, 20);
		delete.setBounds(400, 450, 110, 20);
		editPrihodOrder.setBounds(520, 450, 110, 20);
		comboOrder.setBounds(640, 450, 120, 20);

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
		panel.add(notPaid);
		panel.add(combo);
		panel.add(add);
		panel.add(edit);
		panel.add(delete);
		panel.add(editPrihodOrder);
		panel.add(comboOrder);
		// panel.add(scroll);

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
					RefreshTableOrdersNotEdit(
							"SELECT orders.order_id, users.number_storage, orders.date, orders.summ, orders.quarter1, orders.quarter2, orders.quarter3, orders.quarter4, orders.year, orders.info"
									+ " FROM users, orders WHERE orders.storage_id=users.storage_id AND orders.date!=0 ORDER BY orders.date ASC");
				}
				if (comboRead.getSelectedIndex() == 2) {
					sortFamily.setVisible(false);
					editPrihodOrder.setEnabled(false);
					RefreshTableElectroNotEdit(
							"SELECT users.number_storage, electro.date, electro.last_num, electro.new_num, electro.kw_h, electro.tariff, electro.coef, electro.summ, electro.info"
									+ " FROM users, electro WHERE electro.storage_id=users.storage_id AND electro.date!=0 ORDER BY electro.date ASC");
				}
				if (comboRead.getSelectedIndex() == 3) {
					sortFamily.setVisible(false);
					editPrihodOrder.setEnabled(false);
					panel.remove(scroll);
					panel.updateUI();
				}
				if (comboRead.getSelectedIndex() == 4) {

					sortFamily.setVisible(true);
					editPrihodOrder.setEnabled(false);

					RefreshTableUsersNotEdit(
							"SELECT number_storage, name, person_info FROM users WHERE number_storage!=0 ORDER BY storage_id ASC");
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

				int temp = 0;

				try (Connection cn = ConnectionPool.getPool().getConnection();
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM users")) {

					comboNum.addItem("");
					while (rs.next()) {
						String i = rs.getString(2);
						String j = (String) comboNum.getSelectedItem();
						if (i.equals(j)) {
							temp = rs.getInt(1);
							break;
						}
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(panel, "database error", "Error", JOptionPane.ERROR_MESSAGE);
				}

				try (Connection cn = ConnectionPool.getPool().getConnection();
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery(
								"SELECT orders.date, orders.summ, orders.quarter1, orders.quarter2, orders.quarter3, orders.quarter4, orders.year, orders.info"
										+ " FROM orders WHERE orders.storage_id='" + temp
										+ "' AND orders.date!=0 ORDER BY orders.date ASC")) {

					tableUsers = new TableStorage(rs);
					scroll = new JScrollPane(tableUsers);
					scroll.setBounds(20, 40, 950, 390);
					panel.add(scroll);
					panel.updateUI();

				} catch (SQLException e) {
					JOptionPane.showMessageDialog(panel, "database error", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		sortFamily.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				itemWrite.setEnabled(true);
				RefreshTableUsersNotEdit(
						"SELECT number_storage, name, person_info FROM users WHERE number_storage!=0 ORDER BY name ASC");
			}
		});

		notPaid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				editPrihodOrder.setEnabled(false);

				panel.remove(scroll);

				String quarter = "";
				if (quart1.isSelected()) {
					quarter = "quarter1";
				}
				if (quart2.isSelected()) {
					quarter = "quarter2";
				}
				if (quart3.isSelected()) {
					quarter = "quarter3";
				}
				if (quart4.isSelected()) {
					quarter = "quarter4";
				}
				if (quarter.equals("")) {
					throw new NumberFormatException();
				}

				try (Connection cn = ConnectionPool.getPool().getConnection();
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery(
								"SELECT users.number_storage, name, person_info FROM users WHERE  users.year="
										+ comboYear.getSelectedItem() + " AND users." + quarter + "=''")) {

					tableUsers = new TableStorage(rs);
					scroll = new JScrollPane(tableUsers);
					scroll.setBounds(20, 40, 950, 390);
					panel.add(scroll);
					panel.updateUI();

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(panel, "specify quarter", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex2) {
					JOptionPane.showMessageDialog(panel, "database Query", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				sortFamily.setVisible(false);

				itemWrite.setEnabled(false);
				panel.remove(scroll);

				if (combo.getSelectedIndex() == 0) {
					add.setEnabled(false);
					edit.setEnabled(false);
					delete.setEnabled(false);
					editPrihodOrder.setEnabled(false);

					panel.remove(scroll);
					panel.updateUI();
				}
				if (combo.getSelectedIndex() == 1) {
					add.setEnabled(false);
					edit.setEnabled(false);
					delete.setEnabled(true);
					editPrihodOrder.setEnabled(false);
					RefreshTableOrders();
				}
				if (combo.getSelectedIndex() == 2) {
					add.setEnabled(false);
					edit.setEnabled(false);
					delete.setEnabled(true);
					editPrihodOrder.setEnabled(false);

					panel.remove(scroll);
					panel.updateUI();
				}
				if (combo.getSelectedIndex() == 3) {
					add.setEnabled(false);
					edit.setEnabled(false);
					delete.setEnabled(true);
					editPrihodOrder.setEnabled(false);

					panel.remove(scroll);
					panel.updateUI();
				}
				if (combo.getSelectedIndex() == 4) {
					add.setEnabled(true);
					edit.setEnabled(true);
					delete.setEnabled(false);
					editPrihodOrder.setEnabled(false);
					RefreshTableUsers();
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

						UsersStorageDao daoUser = new UsersStorageDao();
						daoUser.insert(new Users(
								Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString()), "",
								"", "", "", "", "", "", ""));
						JOptionPane.showMessageDialog(panel, "Storage has been added");
						RefreshTableUsers();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database error", "Error", JOptionPane.ERROR_MESSAGE);
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

						if (combo.getSelectedIndex() == 1) {
							if (tableUsers.getValueAt(tableUsers.getSelectedRow(), 3).toString().equals("0")) {
								throw new ArrayIndexOutOfBoundsException("ex");
							}

							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery("SELECT MAX(orders.order_id)"
											+ " FROM orders WHERE orders.summ!=0 AND orders.order_id!="
											+ Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString())
											+ " AND orders.storage_id=" + tableUsers.getValueAt(tableUsers.getSelectedRow(), 4).toString())) {
								
								while (rs.next()) {
									temp = rs.getString(1);
								}
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(panel, "database error", "Error", JOptionPane.ERROR_MESSAGE);
							}

							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery("SELECT orders.quarter1 FROM orders WHERE orders.order_id=" + temp)) {
								while (rs.next()) {
									String j = rs.getString(1);
									if (j == null) {
										j = "";
									}
									quarter1 = j;
								}
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(panel, "database error", "Error", JOptionPane.ERROR_MESSAGE);
							}
							
							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery("SELECT orders.quarter2"
											+ " FROM orders WHERE orders.order_id=" + temp)) {
								
								while (rs.next()) {
									String j = rs.getString(1);
									if (j == null) {
										j = "";
									}
									quarter2 = j;
								}
					    	} catch (SQLException e) {
					    		JOptionPane.showMessageDialog(panel, "database Error", "Error", JOptionPane.ERROR_MESSAGE);
						}

							try (Connection cn = ConnectionPool.getPool().getConnection();
									Statement st = cn.createStatement();
									ResultSet rs = st.executeQuery("SELECT orders.quarter3 FROM orders WHERE orders.order_id=" + temp)) {
								
								while (rs.next()) {
									String j = rs.getString(1);
									if (j == null) {
										j = "";
									}

									quarter3 = j;
								}
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(panel, "database Error", "Error", JOptionPane.ERROR_MESSAGE);
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
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(panel, "database Error", "Error", JOptionPane.ERROR_MESSAGE);
					}

							UsersStorageDao daoUser = new UsersStorageDao();
							daoUser.deleteQuarters(new Users(
									Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString()),
									quarter1, quarter2, quarter3, quarter4));

							buff = (tableUsers.getValueAt(tableUsers.getSelectedRow(), 2).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 3).toString() + "//"
									+ Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 4).toString())
									+ "//" + tableUsers.getValueAt(tableUsers.getSelectedRow(), 5).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 6).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 7).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 8).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 9).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 10).toString());

							OrdersStorageDao daoOrder = new OrdersStorageDao();
							daoOrder.update(new Orders(
									Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString()),
									Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString()),
									"0", 0, "", "", "", "", buff));

							JOptionPane.showMessageDialog(panel, "Rent payment has been deleted");
							RefreshTableOrders();
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

					Date dNow = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy");
					String t = ft.format(dNow);
					UsersStorageDao daoUser = new UsersStorageDao();
					daoUser.update(
							new Users(Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString()),
									tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString(),
									tableUsers.getValueAt(tableUsers.getSelectedRow(), 2).toString(),
									tableUsers.getValueAt(tableUsers.getSelectedRow(), 3).toString(),
									tableUsers.getValueAt(tableUsers.getSelectedRow(), 4).toString(),
									tableUsers.getValueAt(tableUsers.getSelectedRow(), 5).toString(),
									tableUsers.getValueAt(tableUsers.getSelectedRow(), 6).toString(),
									tableUsers.getValueAt(tableUsers.getSelectedRow(), 7).toString(), t));

					// create new empty order
					OrdersStorageDao daoOrder = new OrdersStorageDao();
					daoOrder.insert(new Orders(
							Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString()), "", 0,
							"", "", "", "", t, "new"));

					RefreshTableUsers();
					JOptionPane.showMessageDialog(panel, "Owner has been saved");
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(panel, "select storage", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database", "Error", JOptionPane.ERROR_MESSAGE);
						throw new RuntimeException(e);
				}
			}

		});
		
		editPrihodOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// updating info
					OrdersStorageDao daoOrder = new OrdersStorageDao();
					daoOrder.updateInfo(new Orders(
							Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString()),
							tableUsers.getValueAt(tableUsers.getSelectedRow(), 9).toString()));
					RefreshTableOrdersNotEdit(
							"SELECT orders.order_id, users.number_storage, orders.date, orders.summ, orders.quarter1, orders.quarter2, orders.quarter3, orders.quarter4, orders.year, orders.info"
									+ " FROM users, orders WHERE orders.storage_id=users.storage_id AND orders.date!=0 ORDER BY orders.date ASC");
					JOptionPane.showMessageDialog(panel, "Receipt order has been saved");
				
				} catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(panel, "select receipt order", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		
		comboOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				comboRead.setSelectedIndex(0);
				combo.setSelectedIndex(0);

				if (comboOrder.getSelectedIndex() == 1) {
					new OrderFrameStorage();
					dispose();
				}
				if (comboOrder.getSelectedIndex() == 2) {

				}
				if (comboOrder.getSelectedIndex() == 3) {

				}
			}
		});
	}

	private void RefreshTableOrders() {

		panel.remove(scroll);
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT orders.order_id, orders.storage_id, users.number_storage, orders.date, orders.summ,"
								+ " orders.quarter1, orders.quarter2, orders.quarter3, orders.quarter4,"
								+ " orders.year, orders.info FROM users, orders"
								+ " WHERE orders.storage_id=users.storage_id ORDER BY orders.order_id ASC")) {
			
			tableUsers = new TableStorage(rs);

			// for hiding key column;
			tableUsers.getColumn("order_id").setMaxWidth(0);
			tableUsers.getColumn("order_id").setMinWidth(0);
			tableUsers.getColumn("order_id").setPreferredWidth(0);
			// for hiding key column;
			tableUsers.getColumn("storage_id").setMaxWidth(0);
			tableUsers.getColumn("storage_id").setMinWidth(0);
			tableUsers.getColumn("storage_id").setPreferredWidth(0);

			scroll = new JScrollPane(tableUsers);
			scroll.setBounds(20, 40, 950, 390);
			panel.add(scroll);
			panel.updateUI();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "update database error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void RefreshTableUsers() {

		panel.remove(scroll);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT storage_id, number_storage, name, person_info,"
						+ " users.quarter1, users.quarter2, users.quarter3, users.quarter4,"
						+ " users.year FROM users ORDER BY storage_id ASC")) {

			tableUsers = new TableStorage(rs);

			// hide key column ¹0;
			tableUsers.getColumn("storage_id").setMaxWidth(0);
			tableUsers.getColumn("storage_id").setMinWidth(0);
			tableUsers.getColumn("storage_id").setPreferredWidth(0);

			// hide key column ¹4;
			tableUsers.getColumn("quarter I").setMaxWidth(0);
			tableUsers.getColumn("quarter I").setMinWidth(0);
			tableUsers.getColumn("quarter I").setPreferredWidth(0);

			// hide key column ¹5;
			tableUsers.getColumn("quarter II").setMaxWidth(0);
			tableUsers.getColumn("quarter II").setMinWidth(0);
			tableUsers.getColumn("quarter II").setPreferredWidth(0);

			// hide key column ¹6;
			tableUsers.getColumn("quarter III").setMaxWidth(0);
			tableUsers.getColumn("quarter III").setMinWidth(0);
			tableUsers.getColumn("quarter III").setPreferredWidth(0);

			// hide key column ¹7;
			tableUsers.getColumn("quarter IV").setMaxWidth(0);
			tableUsers.getColumn("quarter IV").setMinWidth(0);
			tableUsers.getColumn("quarter IV").setPreferredWidth(0);

			// hide key column ¹8;
			tableUsers.getColumn("year").setMaxWidth(0);
			tableUsers.getColumn("year").setMinWidth(0);
			tableUsers.getColumn("year").setPreferredWidth(0);

			scroll = new JScrollPane(tableUsers);
			scroll.setBounds(20, 40, 950, 390);
			panel.add(scroll);
			panel.updateUI();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "update database error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void RefreshTableOrdersNotEdit(String sort) {

		panel.remove(scroll);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(sort)) {

			tableUsers = new TableStorage(rs);

			// hide key column;
			tableUsers.getColumn("order_id").setMaxWidth(0);
			tableUsers.getColumn("order_id").setMinWidth(0);
			tableUsers.getColumn("order_id").setPreferredWidth(0);

			scroll = new JScrollPane(tableUsers);
			scroll.setBounds(20, 40, 950, 390);
			panel.add(scroll);
			panel.updateUI();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "update database error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void RefreshTableUsersNotEdit(String sort) {

		panel.remove(scroll);
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(sort)) {

			tableUsers = new TableStorage(rs);
			scroll = new JScrollPane(tableUsers);
			scroll.setBounds(20, 40, 950, 390);
			panel.add(scroll);
			panel.updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "update database error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void RefreshTableElectroNotEdit(String sort) {

		panel.remove(scroll);

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(sort)) {

			tableUsers = new TableStorage(rs);

			scroll = new JScrollPane(tableUsers);
			scroll.setBounds(20, 40, 950, 390);
			panel.add(scroll);
			panel.updateUI();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "update database error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
