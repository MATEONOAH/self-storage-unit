package babroval.storage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import babroval.storage.view.LoginView;

public class LoginController {

	private LoginView view = new LoginView();

	public LoginController() {
	}

	public void initController() {

		view.getMainView().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				if (("").equals(view.getTfLogin().getText())) {
					char[] pass = view.getTfPassword().getPassword();
					String res = "";
					for (int i = 0; i < pass.length; i++) {
						res += pass[i];
					}
					if (res.equals("")) {

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {

								AdminController controller = new AdminController();
								controller.initController();
							}
						});

						view.dispose();
					} else {
						JOptionPane.showMessageDialog(view.getPanel(), "incorrect password", "",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(view.getPanel(), "incorrect login", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		view.getComboOrder().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (!view.getTfLogin().getText().equals(" ")) {
					if (view.getComboOrder().getSelectedIndex() == 1) {

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {

								RentController controller = new RentController();
								controller.initController();
							}
						});
						view.dispose();
					}

					if (view.getComboOrder().getSelectedIndex() == 2) {

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {

								ElectricController controller = new ElectricController();
								controller.initController();
							}
						});
						view.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(view.getPanel(), "incorrect login", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
