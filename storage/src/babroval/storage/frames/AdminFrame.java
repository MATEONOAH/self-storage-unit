package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import babroval.storage.dao.ElectricDao;
import babroval.storage.dao.RentDao;
import babroval.storage.dao.StorageDao;
import babroval.storage.dao.UserDao;
import babroval.storage.entity.Electric;
import babroval.storage.entity.Rent;
import babroval.storage.entity.Storage;
import babroval.storage.entity.User;
import babroval.storage.mysql.ConnectionPool;

public class AdminFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JComboBox<String> comboRead, comboEdit, comboPayment, comboNum, comboYear, comboNumEdit, comboUserEdit;
	private JLabel labelComboNum, labelQuarts, labelNumber, labelName,
				labelNewUserName, labelNewUserInfo, labelNewStorageNum, labelNewStorageInfo;
	private JTextField tfUserName, tfUserInfo, tfStorageNum, tfStorageInfo;
	private JScrollPane scroll;
	private JButton add, delete, save, sortFamily, rentDebtors;
	private JCheckBox quart1, quart2, quart3, quart4, editStorage, addStorage, deleteStorage, editUser, addUser;
	private ButtonGroup groupQuarter, groupStorage;
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

    	comboRead = new JComboBox<String>(selectRead);
		comboEdit = new JComboBox<String>(selectEdit);
		comboPayment = new JComboBox<String>(selectPayment);
		
		sortFamily = new JButton("Sort by last name");
		scroll = new JScrollPane(tableUsers);
		add = new JButton("Add");
		save = new JButton("Save");
		delete = new JButton("Delete");
		
		labelQuarts = new JLabel("Quarters of the year");
		quart1 = new JCheckBox("I");
		quart2 = new JCheckBox("II");
		quart3 = new JCheckBox("III");
		quart4 = new JCheckBox("IV");
		groupQuarter = new ButtonGroup();
		rentDebtors = new JButton("Rent debtors");

		labelComboNum = new JLabel("Rent payment for storage:");
		comboNum = new JComboBox<String>();
		labelNumber = new JLabel("Select number of storage:");
		comboNumEdit = new JComboBox<String>();
		labelName = new JLabel("Select tenant:");
		comboUserEdit = new JComboBox<String>();
		
		editStorage = new JCheckBox("Edit storage");
		addStorage = new JCheckBox("Add storage");
		deleteStorage = new JCheckBox("Delete storage");
		editUser = new JCheckBox("Edit tenant");
		addUser = new JCheckBox("Add tenant");
		groupStorage = new ButtonGroup();
		labelNewStorageNum = new JLabel("Number of storage:");
		tfStorageNum = new JTextField(150);
		labelNewStorageInfo = new JLabel("Additional information of storage:");
		tfStorageInfo = new JTextField(300);
		labelNewUserName = new JLabel("Name of tenant:");
		tfUserName = new JTextField(150);
		labelNewUserInfo = new JLabel("Personal information of tenant:");
		tfUserInfo = new JTextField(300);
		
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int i = Integer.parseInt(sdf.format(today));
		String[] year = { String.valueOf(i - 1), String.valueOf(i), String.valueOf(i + 1) };
		comboYear = new JComboBox<String>(year);
		comboYear.setSelectedIndex(1);
		
		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
						"SELECT storage.storage_number"
				     + " FROM storage"
				     + " WHERE storage.storage_id!=1"
				     + " ORDER BY storage.storage_number")) {

			comboNum.addItem("");
			comboNumEdit.addItem("");
			while (rs.next()) {
				comboNum.addItem(rs.getString(1));
				comboNumEdit.addItem(rs.getString(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
		}

		try (Connection cn = ConnectionPool.getPool().getConnection();
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
						   "SELECT user.name"
						+ " FROM user"
						+ " WHERE user.user_id!=1"
						+ " ORDER BY user.name")) {

			comboUserEdit.addItem("");
			while (rs.next()) {
				comboUserEdit.addItem(rs.getString(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
		}
		
		sortFamily.setVisible(false);
		add.setEnabled(false);
		save.setEnabled(false);
		delete.setEnabled(false);
		itemWrite.setEnabled(false);
		labelNumber.setVisible(false);
		comboNumEdit.setVisible(false);
		labelName.setVisible(false);
		comboUserEdit.setVisible(false);
		editStorage.setVisible(false);
		addStorage.setVisible(false);
		deleteStorage.setVisible(false);
		editUser.setVisible(false);
		addUser.setVisible(false);
		labelNewStorageNum.setVisible(false);
		tfStorageNum.setVisible(false);
		labelNewStorageInfo.setVisible(false);
		tfStorageInfo.setVisible(false);
		tfUserName.setVisible(false);
		labelNewUserName.setVisible(false);
		tfUserInfo.setVisible(false);
		labelNewUserInfo.setVisible(false);
		
		comboRead.setBounds(30, 10, 160, 20);
		labelComboNum.setBounds(200, 10, 150, 20);
		comboNum.setBounds(352, 10, 70, 20);
		sortFamily.setBounds(430, 10, 140, 20);
		quart1.setBounds(602, 10, 30, 20);
		quart2.setBounds(630, 10, 32, 20);
		quart3.setBounds(660, 10, 35, 20);
		quart4.setBounds(692, 10, 40, 20);
		comboYear.setBounds(732, 10, 60, 20);
		rentDebtors.setBounds(800, 10, 110, 20);
		scroll.setBounds(20, 40, 950, 390);
		comboEdit.setBounds(30, 450, 160, 20);
		add.setBounds(205, 450, 110, 20);
		save.setBounds(325, 450, 110, 20);
		delete.setBounds(445, 450, 110, 20);
		comboPayment.setBounds(695, 450, 130, 20);
		labelNumber.setBounds(30, 80, 160, 20);
		comboNumEdit.setBounds(30, 100, 200, 20);
		editStorage.setBounds(30, 130, 100, 20);
		addStorage.setBounds(30, 160, 100, 20);
		labelNewStorageNum.setBounds(30, 190, 200, 20);
		tfStorageNum.setBounds(30, 210, 200, 20);
		labelNewStorageInfo.setBounds(30, 250, 200, 20);
		tfStorageInfo.setBounds(30, 270, 200, 20);
		deleteStorage.setBounds(30, 310, 120, 20);
		labelName.setBounds(300, 80, 160, 20);
		comboUserEdit.setBounds(300, 100, 400, 20);
		editUser.setBounds(300, 130, 100, 20);
		addUser.setBounds(300, 160, 100, 20);
		labelNewUserName.setBounds(300, 190, 100, 20);
		tfUserName.setBounds(300, 210, 300, 20);
		labelNewUserInfo.setBounds(300, 250, 200, 20);
		tfUserInfo.setBounds(300, 270, 400, 20);
		
		file.add(itemWrite);
		file.add(itemExit);
		about.add(itemAbout);
		menuBar.add(file);
		menuBar.add(about);
		setJMenuBar(menuBar);

		groupQuarter.add(quart1);
		groupQuarter.add(quart2);
		groupQuarter.add(quart3);
		groupQuarter.add(quart4);
		
		groupStorage.add(editStorage);
		groupStorage.add(addStorage);
		groupStorage.add(deleteStorage);
		groupStorage.add(editUser);
		groupStorage.add(addUser);
		
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
		panel.add(rentDebtors);
		panel.add(comboEdit);
		panel.add(add);
		panel.add(save);
		panel.add(delete);
		panel.add(comboPayment);
		panel.add(labelNumber);
		panel.add(comboNumEdit);
		panel.add(labelName);
		panel.add(comboUserEdit);
		panel.add(editStorage);
		panel.add(addStorage);
		panel.add(deleteStorage);
		panel.add(editUser);
		panel.add(addUser);
		panel.add(labelNewStorageNum);
		panel.add(tfStorageNum);
		panel.add(labelNewStorageInfo);
		panel.add(tfStorageInfo);
		panel.add(labelNewUserName);
		panel.add(tfUserName);
		panel.add(labelNewUserInfo);
		panel.add(tfUserInfo);

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
				save.setEnabled(false);
				delete.setEnabled(false);

				if (comboRead.getSelectedIndex() == 0) {
					sortFamily.setVisible(false);
					panel.remove(scroll);
					panel.updateUI();
				}
				if (comboRead.getSelectedIndex() == 1) {

					sortFamily.setVisible(false);
					showTable("SELECT rent.date, storage.storage_number, rent.quarter_paid, rent.sum, rent.info"
							+ " FROM storage, rent" + " WHERE storage.storage_id=rent.storage_id "
							+ " AND rent.date!=0 ORDER BY rent.rent_id DESC");
				}
				if (comboRead.getSelectedIndex() == 2) {
					sortFamily.setVisible(false);
				    showTable(
							"SELECT electric.date, storage.storage_number, electric.tariff, electric.meter_paid, electric.sum, electric.info"
									+ " FROM storage, electric" + " WHERE storage.storage_id=electric.storage_id"
									+ " AND electric.date!=0 ORDER BY electric.electric_id DESC");
				}
				if (comboRead.getSelectedIndex() == 3) {

					sortFamily.setVisible(true);
			    	showTable("SELECT storage.storage_number, user.name, user.info" + " FROM storage, user"
							+ " WHERE storage.user_id=user.user_id"
							+ " AND storage.storage_number!=0 ORDER BY storage.storage_number");
				}
			}
		});

		comboNum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				sortFamily.setVisible(false);

				itemWrite.setEnabled(true);
				add.setEnabled(false);
				save.setEnabled(false);
				delete.setEnabled(false);
			
				showTable("SELECT rent.date, rent.quarter_paid, rent.sum, rent.info" + " FROM storage, rent"
						+ " WHERE storage.storage_id=rent.storage_id" + " AND storage.storage_number='"
						+ comboNum.getSelectedItem() + "'" + " AND rent.date!=0" + " ORDER BY rent.quarter_paid DESC");
			}
		});

		sortFamily.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				itemWrite.setEnabled(true);
				showTable("SELECT storage.storage_number, user.name, user.info" + " FROM storage, user"
						+ " WHERE storage.user_id=user.user_id" + " AND storage.storage_number!=0"
						+ " ORDER BY user.name");
			}
		});

		rentDebtors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				panel.remove(scroll);

				try {
					String quarter = ""; // first month of year quarter
					if (quart1.isSelected())
						quarter = "01";
					else if (quart2.isSelected())
						quarter = "04";
					else if (quart3.isSelected())
						quarter = "07";
					else if (quart4.isSelected())
						quarter = "10";
					else
						throw new NumberFormatException();

					showTable("SELECT MAX(rent.quarter_paid), storage.storage_number, user.name, user.info"
							+ " FROM storage, user, rent" 
							+ " WHERE rent.storage_id=storage.storage_id"
							+ " AND storage.user_id=user.user_id" 
							+ " GROUP BY storage.storage_id"
							+ " HAVING MAX(rent.quarter_paid)<'" + comboYear.getSelectedItem() + "-" + quarter + "-01"
							+ "'" + " ORDER BY rent.quarter_paid ASC");

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
					save.setEnabled(false);
					delete.setEnabled(false);
			
					panel.remove(scroll);
					panel.updateUI();
				}
				if (comboEdit.getSelectedIndex() == 1) {
					add.setEnabled(false);
					save.setEnabled(false);
					delete.setEnabled(true);

					showRentTable();
				}
				if (comboEdit.getSelectedIndex() == 2) {
					add.setEnabled(false);
					save.setEnabled(false);
					delete.setEnabled(true);
		
					showElectricTable();
				}
				if (comboEdit.getSelectedIndex() == 3) {
					add.setEnabled(true);
					save.setEnabled(true);
					delete.setEnabled(false);
					panel.remove(scroll);
					labelNumber.setVisible(true);
					comboNumEdit.setVisible(true);
					labelName.setVisible(true);
					comboUserEdit.setVisible(true);
					editStorage.setVisible(true);
					addStorage.setVisible(true);
					deleteStorage.setVisible(true);
					editUser.setVisible(true);
					addUser.setVisible(true);
					labelNewStorageNum.setVisible(true);
					tfStorageNum.setVisible(true);
					labelNewStorageInfo.setVisible(true);
					tfStorageInfo.setVisible(true);
					tfUserName.setVisible(true);
					labelNewUserName.setVisible(true);
					tfUserInfo.setVisible(true);
					labelNewUserInfo.setVisible(true);
					
					panel.updateUI();
				}

			}
		});

		comboNumEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				groupStorage.clearSelection();

				if (comboNumEdit.getSelectedIndex() == 0) {
					comboUserEdit.setSelectedIndex(0);
				} else {
					try {
						UserDao daoUser = new UserDao();
						User user = daoUser.loadUserByStorageNumber(comboNumEdit.getSelectedItem().toString());
						
						if(user.getUser_id().equals("1")) {
							comboUserEdit.setSelectedIndex(0);
						}else {
							comboUserEdit.setSelectedItem(user.getName());
							tfUserName.setText(user.getName());
							tfUserInfo.setText(user.getInfo());
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
				
		comboUserEdit.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				groupStorage.clearSelection();
				try {
				
					UserDao daoUser = new UserDao();
					User user = daoUser.loadUserByName(comboUserEdit.getSelectedItem().toString());
				
					tfUserName.setText(user.getName());
					tfUserInfo.setText(user.getInfo());
	
    			} catch (NumberFormatException e) {
    				groupStorage.clearSelection();
     				JOptionPane.showMessageDialog(panel, "select tenant", "",
							JOptionPane.ERROR_MESSAGE);
  				} catch (Exception e) {
  					groupStorage.clearSelection();
					JOptionPane.showMessageDialog(panel, "database fault", "",
							JOptionPane.ERROR_MESSAGE);
				}				
			}
		});

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try (Connection cn = ConnectionPool.getPool().getConnection();
						Statement st = cn.createStatement();
						ResultSet rs = st
								.executeQuery("SELECT user.user_id" + " FROM user" + " WHERE user.storage_id=1")) {
					if (rs.next()) {
						throw new ArrayIndexOutOfBoundsException("e");
					}

					int result = JOptionPane.showConfirmDialog(panel, "Add tenant?", "Add",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {

						UserDao daoUser = new UserDao();
						daoUser.insert(new User(1, "", ""));

					//	showUserTable();

						JOptionPane.showMessageDialog(panel, "Please, enter tenant's name on the empty line");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(panel, "enter tenant's name on the empty line", "fault",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					if (comboNumEdit.getSelectedIndex() == 0 || comboUserEdit.getSelectedIndex() == 0) {
						throw new NumberFormatException("e");
					}

					Integer storageId = 0;
					Integer userId = 0;
					String userName = "";
					try (Connection cn = ConnectionPool.getPool().getConnection();
							Statement st = cn.createStatement();
							ResultSet rs = st.executeQuery(
									"SELECT storage.storage_id, user.name"
								 + " FROM storage, user"
								 + " WHERE storage.storage_number='" + comboNumEdit.getSelectedItem() + "'"
								 + " AND storage.user_id=user.user_id")) {

						while (rs.next()) {
							storageId = Integer.valueOf(rs.getString(1));
							userName = rs.getString(2);
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

					if (userName.equals(comboUserEdit.getSelectedItem().toString())) {
						throw new NumberFormatException("e");
					}

					StorageDao daoStorage = new StorageDao();
					if (storageId != 0) {
						daoStorage.assignUser(new Storage(storageId, 1));
					}

					try (Connection cn = ConnectionPool.getPool().getConnection();
							Statement st = cn.createStatement();
							ResultSet rs = st.executeQuery(
									"SELECT user.user_id"
								 + " FROM user"
								 + " WHERE user.name='" + comboUserEdit.getSelectedItem() + "'")) {

						while (rs.next()) {
							userId = Integer.valueOf(rs.getString(1));
						}

						daoStorage.assignUser(new Storage(storageId, userId));

						JOptionPane.showMessageDialog(panel, "Tenant has been successfully saved");
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel,
							"select storage and tenant or this tenant has already rented this storage", "",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {

					if (tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString().equals("DELETED")) {
						throw new ArrayIndexOutOfBoundsException("e");
					}
					int result = JOptionPane.showConfirmDialog(panel, "Delete payment?", "Delete",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {

						String deletedInfo = "";

						if (comboEdit.getSelectedIndex() == 1) {
							deletedInfo = ("DELETED:" + tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString()
									+ "//" + tableUsers.getValueAt(tableUsers.getSelectedRow(), 2).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 3).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 4).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 5).toString());

							RentDao daoRent = new RentDao();
							daoRent.update(new Rent(
									Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString()),
									1, Date.valueOf("0001-01-01"), Date.valueOf("0001-01-01"), BigDecimal.valueOf(0),
									deletedInfo));

							showRentTable();
						}

						if (comboEdit.getSelectedIndex() == 2) {
							deletedInfo = ("DELETED:" + tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString()
									+ "//" + tableUsers.getValueAt(tableUsers.getSelectedRow(), 2).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 3).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 4).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 5).toString() + "//"
									+ tableUsers.getValueAt(tableUsers.getSelectedRow(), 6).toString());

							ElectricDao daoElectric = new ElectricDao();
							daoElectric.update(new Electric(
									Integer.valueOf(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString()),
									1, Date.valueOf("0001-01-01"), BigDecimal.valueOf(0), 0, BigDecimal.valueOf(0),
									deletedInfo));

							showElectricTable();
						}
						JOptionPane.showMessageDialog(panel, "Payment has been deleted");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(panel,
							"Payment has not been selected or payment has been already deleted", "fault",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		comboPayment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

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

	protected void showRentTable() {
		showTable("SELECT rent.rent_id, storage.storage_number, rent.date," + " rent.quarter_paid, rent.sum, rent.info"
				+ " FROM storage, rent" + " WHERE rent.storage_id=storage.storage_id" + " ORDER BY rent.rent_id DESC");
	}

	protected void showElectricTable() {
		showTable("SELECT electric.electric_id, storage.storage_number, electric.date,"
				+ " electric.tariff, electric.meter_paid, electric.sum, electric.info" + " FROM electric, storage"
				+ " WHERE electric.storage_id=storage.storage_id" + " ORDER BY electric.electric_id DESC");

	}

	protected void showTable(String query) {

		panel.remove(scroll);
		labelNumber.setVisible(false);
		comboNumEdit.setVisible(false);
		labelName.setVisible(false);
		comboUserEdit.setVisible(false);
		editStorage.setVisible(false);
		addStorage.setVisible(false);
		deleteStorage.setVisible(false);
		editUser.setVisible(false);
		addUser.setVisible(false);
		labelNewStorageNum.setVisible(false);
		tfStorageNum.setVisible(false);
		labelNewStorageInfo.setVisible(false);
		tfStorageInfo.setVisible(false);
		tfUserName.setVisible(false);
		labelNewUserName.setVisible(false);
		tfUserInfo.setVisible(false);
		labelNewUserInfo.setVisible(false);

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
