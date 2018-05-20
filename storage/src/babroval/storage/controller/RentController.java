package babroval.storage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import babroval.storage.model.Rent;
import babroval.storage.model.Storage;
import babroval.storage.model.User;
import babroval.storage.service.RentServiceImpl;
import babroval.storage.service.Service;
import babroval.storage.service.StorageServiceImpl;
import babroval.storage.service.UserServiceImpl;
import babroval.storage.util.InitDB;
import babroval.storage.view.ElectricView;
import babroval.storage.view.LoginView;
import babroval.storage.view.RentView;

public class RentController {

	private RentView view = new RentView();

	private Service<Rent> rentService = new RentServiceImpl<>();
	private Service<Storage> storageService = new StorageServiceImpl<>();
	private Service<User> userService = new UserServiceImpl<>();

	public RentController() {
		initView();
	}

	public void initView() {
		try {
			List<String> allStoragesNumbers = new ArrayList<String>();
			allStoragesNumbers = storageService.getAllNumbers();

			for (String storageNum : allStoragesNumbers) {
				view.getComboNum().addItem(storageNum);
			}
		} catch (Exception e) {
			view.getComboNum().setSelectedIndex(0);
			resetFrame();
			JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void initController() {

		view.getComboNum().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				updateFrame();
			}
		});

		view.getEnter().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				try {
					String quarter = ""; // first month of year quarter

					if (view.getQuart1().isEnabled() && view.getQuart1().isSelected())
						quarter = "01";
					else if (view.getQuart2().isEnabled() && view.getQuart2().isSelected())
						quarter = "04";
					else if (view.getQuart3().isEnabled() && view.getQuart3().isSelected())
						quarter = "07";
					else if (view.getQuart4().isEnabled() && view.getQuart4().isSelected())
						quarter = "10";

					BigDecimal sum = new BigDecimal(view.getTfSum().getText());
					sum = sum.setScale(2, RoundingMode.FLOOR);

					if (view.getComboNum().getSelectedIndex() == 0 || quarter.equals("")) {
						throw new NumberFormatException("e");
					}

					rentService.insert(new Rent(
							storageService.getIdByStorageNumber((String) view.getComboNum().getSelectedItem()),
							InitDB.stringToDate(view.getTfDate().getText(), "dd-MM-yyyy"),
							InitDB.stringToDate("01-" + quarter + "-" + view.getLabelYear().getText(), "dd-MM-yyyy"),
							sum, view.getTfInf().getText()));

					view.getComboNum().setSelectedIndex(0);
					resetFrame();
					JOptionPane.showMessageDialog(view.getPanel(), "The payment has been successfully included",
							"Message", JOptionPane.INFORMATION_MESSAGE);

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(view.getPanel(), "select storage, quarter and enter the right amount",
							"", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {
					view.getComboNum().setSelectedIndex(0);
					resetFrame();
					JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		view.getCancel().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				updateFrame();
			}
		});

		view.getComboSelect().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (view.getComboSelect().getSelectedIndex() == 1) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new ElectricView();
						}
					});
					view.dispose();
				}

				if (view.getComboSelect().getSelectedIndex() == 2) {

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new LoginView();
						}
					});
					view.dispose();
				}
			}
		});

	}

	private void resetFrame() {
		view.getTfName().setText("");
		view.getQuart1().setEnabled(false);
		view.getQuart1().setSelected(false);
		view.getQuart2().setEnabled(false);
		view.getQuart2().setSelected(false);
		view.getQuart3().setEnabled(false);
		view.getQuart3().setSelected(false);
		view.getQuart4().setEnabled(false);
		view.getQuart4().setSelected(false);
		view.getLabelYear().setText("");
		view.getTfSum().setText("");
		view.getTfSum().setEnabled(false);
		view.getTfInf().setText("");
		view.getTfInf().setEnabled(false);
		view.getEnter().setEnabled(false);
		view.getCancel().setEnabled(false);
	}

	private void updateFrame() {

		if (view.getComboNum().getSelectedIndex() == 0) {
			resetFrame();
		} else {
			resetFrame();
			try {
				String userName = userService.getNameByStorageNumber((String) view.getComboNum().getSelectedItem());
				view.getTfName().setText(userName);

				Date quarterPaid = rentService
						.getLastQuarterPaidByStorageNumber((String) view.getComboNum().getSelectedItem());
				String temp = view.getSdf().format(quarterPaid);

				Integer quarter = Integer.valueOf(temp.substring(3, 5));
				Integer year = Integer.valueOf(temp.substring(6, 10));

				switch (quarter) {
				case 1:
					view.getQuart1().setEnabled(false);
					view.getQuart1().setSelected(true);
					view.getQuart2().setEnabled(true);
					view.getQuart2().setSelected(false);
					break;
				case 4:
					view.getQuart1().setEnabled(false);
					view.getQuart1().setSelected(true);
					view.getQuart2().setEnabled(false);
					view.getQuart2().setSelected(true);
					view.getQuart3().setEnabled(true);
					view.getQuart3().setSelected(false);
					break;
				case 7:
					view.getQuart1().setEnabled(false);
					view.getQuart1().setSelected(true);
					view.getQuart2().setEnabled(false);
					view.getQuart2().setSelected(true);
					view.getQuart3().setEnabled(false);
					view.getQuart3().setSelected(true);
					view.getQuart4().setEnabled(true);
					view.getQuart4().setSelected(false);
					break;
				case 10:
					year++;
					view.getQuart1().setEnabled(true);
					view.getQuart1().setSelected(false);
					break;
				}
				view.getLabelYear().setText(String.valueOf(year));
				view.getTfSum().setEnabled(true);
				view.getTfInf().setEnabled(true);
				view.getEnter().setEnabled(true);
				view.getCancel().setEnabled(true);

			} catch (Exception e) {
				view.getComboNum().setSelectedIndex(0);
				resetFrame();
				JOptionPane.showMessageDialog(view.getPanel(), "database fault", "", JOptionPane.ERROR_MESSAGE);
			}
		}
		view.getPanel().updateUI();
	}

}
