package babroval.storage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import babroval.storage.model.Electric;
import babroval.storage.model.Rent;
import babroval.storage.model.Storage;
import babroval.storage.model.User;
import babroval.storage.service.ElectricServiceImpl;
import babroval.storage.service.RentServiceImpl;
import babroval.storage.service.Service;
import babroval.storage.service.StorageServiceImpl;
import babroval.storage.service.UserServiceImpl;
import babroval.storage.util.ConnectionPool;
import babroval.storage.util.TableStorage;
import babroval.storage.view.AdminView;
import babroval.storage.view.ElectricView;
import babroval.storage.view.RentView;

public class AdminController {

	private AdminView view = new AdminView();
	
	private Service<Electric> electricService = new ElectricServiceImpl<>();
	private Service<Storage> storageService = new StorageServiceImpl<>();
	private Service<User> userService = new UserServiceImpl<>();
	private Service<Rent> rentService = new RentServiceImpl<>();
	
	private TableStorage table;
	
	public AdminController() {
		initView();
	}

	private void initView() {
		try {
			List<String> allStoragesNumbers = new ArrayList<String>();
    		allStoragesNumbers = storageService.getAllNumbers();

    		for (String storageNum : allStoragesNumbers) {
				view.getComboNum().addItem(storageNum);
				view.getComboNumEdit().addItem(storageNum);
			}

    		
			List<String> allUsersNames = new ArrayList<String>();
			allUsersNames = userService.getAllNames();
			
			for (String name : allUsersNames) {
				view.getComboUserEdit().addItem(name);
			}
		} catch (Exception e) {
			view.getComboNum().setSelectedIndex(0);
			JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void initController() {
		
		view.getItemWrite().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int x = view.getChooser().showSaveDialog(view.getPanel());
				if (x == 0) {
					String allRow = "";
					for (int j = 0; j < table.getColumnCount(); j++) {
						allRow = allRow + table.getColumnName(j) + "\t";
					}
					allRow = allRow + "\n";
					for (int i = 0; i < table.getRowCount(); i++) {
						for (int j = 0; j < table.getColumnCount(); j++) {
							allRow = allRow + table.getValueAt(i, j) + "\t";
						}
						allRow = allRow + "\n";
					}

					try (FileWriter fw = new FileWriter(view.getChooser().getSelectedFile())) {

						fw.write(allRow);
						JOptionPane.showMessageDialog(view.getPanel(), "Saved");
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(view.getPanel(), "save error");
					}
				}
			}
		});

		view.getItemAbout().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(view.getPanel(), "Storage");
			}
		});

		view.getItemExit().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		view.getComboRead().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				view.getItemWrite().setEnabled(true);
				view.getAdd().setEnabled(false);
				view.getSave().setEnabled(false);
				view.getDelete().setEnabled(false);

				if (view.getComboRead().getSelectedIndex() == 0) {
					view.getSortFamily().setVisible(false);
					view.getPanel().remove(view.getScroll());
					view.getPanel().updateUI();
				}
				if (view.getComboRead().getSelectedIndex() == 1) {
					view.getSortFamily().setVisible(false);

					showTable(rentService.getReadOnlyTable());
				}

				if (view.getComboRead().getSelectedIndex() == 2) {
					view.getSortFamily().setVisible(false);

					showTable(electricService.getReadOnlyTable());
				}

				if (view.getComboRead().getSelectedIndex() == 3) {
					view.getSortFamily().setVisible(true);

					showTable(userService.getReadOnlyTable());
				}
			}
		});

		view.getComboNum().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				view.getSortFamily().setVisible(false);

				view.getItemWrite().setEnabled(true);
				view.getAdd().setEnabled(false);
				view.getSave().setEnabled(false);
				view.getDelete().setEnabled(false);

				showTable(rentService.getTableByStorageNumber(String.valueOf(view.getComboNum().getSelectedItem())));
			}
		});

		view.getSortFamily().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				view.getItemWrite().setEnabled(true);

				showTable(userService.getSortTable());
			}
		});

		view.getRentDebtors().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				view.getPanel().remove(view.getScroll());

				try {
					String quarter = ""; // first month of year quarter
					if (view.getQuart1().isSelected())
						quarter = "01";
					else if (view.getQuart2().isSelected())
						quarter = "04";
					else if (view.getQuart3().isSelected())
						quarter = "07";
					else if (view.getQuart4().isSelected())
						quarter = "10";
					else
						throw new NumberFormatException();

					showTable(rentService.getDebtorsByYearQuarter(String.valueOf(view.getComboYear().getSelectedItem()),
							quarter));

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(view.getPanel(), "specify quarter", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		view.getComboEdit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				view.getSortFamily().setVisible(false);
				view.getItemWrite().setEnabled(false);
				view.getPanel().remove(view.getScroll());

				if (view.getComboEdit().getSelectedIndex() == 0) {
					view.getAdd().setEnabled(false);
					view.getSave().setEnabled(false);
					view.getDelete().setEnabled(false);

					view.getPanel().remove(view.getScroll());
					view.getPanel().updateUI();
				}
				if (view.getComboEdit().getSelectedIndex() == 1) {
					view.getAdd().setEnabled(false);
					view.getSave().setEnabled(false);
					view.getDelete().setEnabled(true);

					showTable(rentService.getEditTable());
				}
				if (view.getComboEdit().getSelectedIndex() == 2) {
					view.getAdd().setEnabled(false);
					view.getSave().setEnabled(false);
					view.getDelete().setEnabled(true);

					showTable(electricService.getEditTable());
				}
				if (view.getComboEdit().getSelectedIndex() == 3) {
					view.getAdd().setEnabled(true);
					view.getSave().setEnabled(true);
					view.getDelete().setEnabled(false);
					view.getPanel().remove(view.getScroll());
					view.getLabelNumber().setVisible(true);
					view.getComboNumEdit().setVisible(true);
					view.getLabelName().setVisible(true);
					view.getComboUserEdit().setVisible(true);
					view.getEditStorage().setVisible(true);
					view.getAddStorage().setVisible(true);
					view.getDeleteStorage().setVisible(true);
					view.getEditUser().setVisible(true);
					view.getAddUser().setVisible(true);
					view.getLabelNewStorageNum().setVisible(true);
					view.getTfStorageNum().setVisible(true);
					view.getLabelNewStorageInfo().setVisible(true);
					view.getTfStorageInfo().setVisible(true);
					view.getTfUserName().setVisible(true);
					view.getTfUserInfo().setVisible(true);
					view.getTfUserInfo().setVisible(true);
					view.getLabelNewUserInfo().setVisible(true);

					view.getPanel().updateUI();
				}

			}
		});

		view.getComboNumEdit().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				view.getGroupStorage().clearSelection();

				if (view.getComboNumEdit().getSelectedIndex() == 0) {
					view.getComboUserEdit().setSelectedIndex(0);
				} else {
					try {
						User user = userService
								.getByStorageNumber(String.valueOf(view.getComboNumEdit().getSelectedItem()));

						if (user.getUser_id() == 1) {
							view.getComboUserEdit().setSelectedIndex(0);
						} else {
							view.getComboUserEdit().setSelectedItem(user.getName());
							view.getTfUserName().setText(user.getName());
							view.getTfUserInfo().setText(user.getInfo());
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		view.getComboUserEdit().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				view.getGroupStorage().clearSelection();
				try {

					User user = userService.getByName(String.valueOf(view.getComboUserEdit().getSelectedItem()));

					view.getTfUserName().setText(user.getName());
					view.getTfUserInfo().setText(user.getInfo());

				} catch (NumberFormatException e) {
					view.getGroupStorage().clearSelection();
					JOptionPane.showMessageDialog(view.getPanel(), "select tenant", "", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					view.getGroupStorage().clearSelection();
					JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		view.getAdd().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try (Connection cn = ConnectionPool.getPool().getConnection();
						Statement st = cn.createStatement();
						ResultSet rs = st
								.executeQuery("SELECT user.user_id" + " FROM user" + " WHERE user.storage_id=1")) {
					if (rs.next()) {
						throw new ArrayIndexOutOfBoundsException("e");
					}

					int result = JOptionPane.showConfirmDialog(view.getPanel(), "Add tenant?", "Add",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {

						userService.insert(new User(1, "", ""));

						// showUserTable();

						JOptionPane.showMessageDialog(view.getPanel(), "Please, enter tenant's name on the empty line");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(view.getPanel(), "enter tenant's name on the empty line", "fault",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		view.getSave().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					if (view.getComboNumEdit().getSelectedIndex() == 0 || view.getComboUserEdit().getSelectedIndex() == 0) {
						throw new NumberFormatException("e");
					}

					String userName = userService
							.getNameByStorageNumber(String.valueOf(view.getComboNumEdit().getSelectedItem()));
					
					Integer storageId = storageService
							.getIdByStorageNumber(String.valueOf(view.getComboNumEdit().getSelectedItem()));

					if (userName.equals(String.valueOf(view.getComboUserEdit().getSelectedItem()))) {
						throw new NumberFormatException("e");
					}

					if (storageId != 0) {
						storageService.assignTo(new Storage(storageId, 1));
					}

					Integer otherUserId = userService
							.getIdByName(String.valueOf(view.getComboUserEdit().getSelectedItem()));

					storageService.assignTo(new Storage(storageId, otherUserId));

					JOptionPane.showMessageDialog(view.getPanel(), "Tenant has been successfully saved");

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(view.getPanel(),
							"select storage and tenant or this tenant has already rented this storage", "",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		view.getDelete().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				try {
					if ("DELETED".equals(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)))) {
						throw new ArrayIndexOutOfBoundsException("e");
					}
					int result = JOptionPane.showConfirmDialog(view.getPanel(), "Delete payment?", "Delete",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {

						String deletedInfo = "";

						if (view.getComboEdit().getSelectedIndex() == 1) {
							deletedInfo = ("DELETED:" + String.valueOf(table.getValueAt(table.getSelectedRow(), 1))
									+ "//" + String.valueOf(table.getValueAt(table.getSelectedRow(), 2)) + "//"
									+ String.valueOf(table.getValueAt(table.getSelectedRow(), 3)) + "//"
									+ String.valueOf(table.getValueAt(table.getSelectedRow(), 4)) + "//"
									+ String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));

							rentService.update(new Rent(
									Integer.valueOf(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))), 1,
									Date.valueOf("1970-01-01"), Date.valueOf("1970-01-01"), new BigDecimal("0"),
									deletedInfo));

							showTable(rentService.getEditTable());
						}

						if (view.getComboEdit().getSelectedIndex() == 2) {
							deletedInfo = ("DELETED:" + String.valueOf(table.getValueAt(table.getSelectedRow(), 1))
									+ "//" + String.valueOf(table.getValueAt(table.getSelectedRow(), 2)) + "//"
									+ String.valueOf(table.getValueAt(table.getSelectedRow(), 3)) + "//"
									+ String.valueOf(table.getValueAt(table.getSelectedRow(), 4)) + "//"
									+ String.valueOf(table.getValueAt(table.getSelectedRow(), 5)) + "//"
									+ String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));

							electricService.update(new Electric(
									Integer.valueOf(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))), 1,
									Date.valueOf("1970-01-01"), new BigDecimal("0"), 0, new BigDecimal("0"),
									deletedInfo));

							showTable(electricService.getEditTable());
						}
						JOptionPane.showMessageDialog(view.getPanel(), "Payment has been deleted");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(view.getPanel(),
							"Payment has not been selected or payment has been already deleted", "fault",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		view.getComboPayment().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				view.getComboRead().setSelectedIndex(0);
				view.getComboEdit().setSelectedIndex(0);

				if (view.getComboPayment().getSelectedIndex() == 1) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new RentView();
						}
					});
					view.dispose();
				}

				if (view.getComboPayment().getSelectedIndex() == 2) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new ElectricView();
						}
					});
					view.dispose();
				}
			}
		});

	}
	
	private void showTable(TableStorage table) {

		view.getPanel().remove(view.getScroll());
		view.getLabelNumber().setVisible(false);
		view.getComboNumEdit().setVisible(false);
		view.getLabelName().setVisible(false);
		view.getComboUserEdit().setVisible(false);
		view.getEditStorage().setVisible(false);
		view.getAddStorage().setVisible(false);
		view.getDeleteStorage().setVisible(false);
		view.getEditUser().setVisible(false);
		view.getAddUser().setVisible(false);
		view.getLabelNewStorageNum().setVisible(false);
		view.getTfStorageNum().setVisible(false);
		view.getLabelNewStorageInfo().setVisible(false);
		view.getTfStorageInfo().setVisible(false);
		view.getTfUserName().setVisible(false);
		view.getLabelNewUserName().setVisible(false);
		view.getTfUserInfo().setVisible(false);
		view.getLabelNewUserInfo().setVisible(false);

		try {
			view.getScroll().setBounds(20, 40, 950, 390);
			view.getPanel().add(view.getScroll());
			view.getPanel().updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}

