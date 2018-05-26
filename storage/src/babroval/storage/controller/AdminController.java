package babroval.storage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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
import babroval.storage.util.TableStorage;
import babroval.storage.view.AdminView;

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

			view.getComboNum().removeAllItems();
			view.getComboNum().addItem("");
			view.getComboNumEdit().removeAllItems();
			view.getComboNumEdit().addItem("");
			view.getComboUserEdit().removeAllItems();
			view.getComboUserEdit().addItem("");

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
				view.getCancel().setEnabled(false);
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
				view.getCancel().setEnabled(false);
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
					view.getCancel().setEnabled(false);
					view.getSave().setEnabled(false);
					view.getDelete().setEnabled(false);

					view.getPanel().remove(view.getScroll());
					view.getPanel().updateUI();
				}
				if (view.getComboEdit().getSelectedIndex() == 1) {
					view.getCancel().setEnabled(false);
					view.getSave().setEnabled(false);
					view.getDelete().setEnabled(true);

					showTable(rentService.getEditTable());
				}
				if (view.getComboEdit().getSelectedIndex() == 2) {
					view.getCancel().setEnabled(false);
					view.getSave().setEnabled(false);
					view.getDelete().setEnabled(true);

					showTable(electricService.getEditTable());
				}
				if (view.getComboEdit().getSelectedIndex() == 3) {
					view.getCancel().setEnabled(true);
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
					view.getLabelNewUserName().setVisible(true);
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
					view.getTfStorageNum().setText("");
					view.getTfStorageInfo().setText("");
				} else {
					try {
						Storage storage = storageService
								.getByStorageNumber(String.valueOf(view.getComboNumEdit().getSelectedItem()));
						User user = userService
								.getByStorageNumber(String.valueOf(view.getComboNumEdit().getSelectedItem()));

						view.getTfStorageNum().setText(storage.getStorage_number());
						view.getTfStorageInfo().setText(storage.getInfo());

						if (user.getUser_id() != 1) {
							view.getComboUserEdit().setSelectedItem(user.getName());
							view.getTfUserName().setText(user.getName());
							view.getTfUserInfo().setText(user.getInfo());
						} else {
							view.getComboUserEdit().setSelectedIndex(0);
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

		view.getCancel().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				view.getComboNumEdit().setSelectedIndex(0);
			}
		});

		view.getSave().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					if (view.getComboNumEdit().getSelectedIndex() == 0
							& view.getComboUserEdit().getSelectedIndex() == 0) {
						throw new NumberFormatException("e");
					}
					if (view.getEditUser().isSelected() & view.getComboUserEdit().getSelectedIndex() == 0) {
						throw new NumberFormatException("e");
					}
					if (!view.getEditUser().isSelected() & view.getComboNumEdit().getSelectedIndex() == 0) {
						throw new NumberFormatException("e");
					}

					Integer storageId = storageService
							.getIdByStorageNumber(String.valueOf(view.getComboNumEdit().getSelectedItem()));

					User newUser = new User();

					if (view.getComboUserEdit().getSelectedIndex() != 0) {
						newUser = new User(
								Integer.valueOf(userService
										.getIdByName(String.valueOf(view.getComboUserEdit().getSelectedItem()))),
								view.getTfUserName().getText(), view.getTfUserInfo().getText());

						if (view.getEditUser().isSelected()) {
							userService.update(newUser);
						}
					} else {
						newUser.setUser_id(1);
					}

					for(int i = 0; i<view.getComboNumEdit().getItemCount(); i++) {
						if(view.getTfStorageNum().getText().equals(view.getComboNumEdit().getItemAt(i))) {
							throw new NumberFormatException("e");
						}
					}
					
					if (view.getEditStorage().isSelected()) {
						storageService.update(new Storage(
								storageService.getIdByStorageNumber(
										String.valueOf(view.getComboNumEdit().getSelectedItem())),
								newUser.getUser_id(), view.getTfStorageNum().getText(),
								view.getTfStorageInfo().getText()));
					}

					if (storageId != 0) {
						storageService.assignTo(new Storage(storageId, 1)); // detach garage from user
					}
					if (view.getComboUserEdit().getSelectedIndex() != 0) {
						storageService.assignTo(new Storage(storageId, newUser.getUser_id())); // attach new user to
																								// garage
					}

					initView();
					view.getComboEdit().setSelectedIndex(3);

					JOptionPane.showMessageDialog(view.getPanel(), "Tenant has been successfully saved");

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(view.getPanel(), "select storage, tenant or element is already exists", "",
							JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);

					initView();
					view.getComboEdit().setSelectedIndex(3);
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

							RentController controller = new RentController();
							controller.initController();
						}
					});
					view.dispose();
				}

				if (view.getComboPayment().getSelectedIndex() == 2) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {

							ElectricController controller = new ElectricController();
							controller.initController();
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
			view.setScroll(new JScrollPane(table));
			view.getScroll().setBounds(20, 40, 950, 390);
			view.getPanel().add(view.getScroll());
			view.getPanel().updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
		}
	}

}
