package babroval.storage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import babroval.storage.dao.resources.ConnectionPool;
import babroval.storage.dao.resources.InitDB;
import babroval.storage.view.LoginView;
import babroval.storage.view.StartView;

public class StartController {

	private StartView view;

	public StartController(StartView view) {
		this.view = view;
	}

	public void initController() {
		view.getCreate().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					InitDB.createDB(view.getTfUrl().getText(), view.getTfLogin().getText(),
							view.getTfPassword().getText());

					JOptionPane.showMessageDialog(view.getPanel(), "The database has been successfully created",
							"Message", JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(view.getPanel(), "the database has not been successfully created", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		view.getDelete().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					InitDB.deleteDB(view.getTfUrl().getText(), view.getTfLogin().getText(),
							view.getTfPassword().getText());

					JOptionPane.showMessageDialog(view.getPanel(), "The database has been successfully deleted",
							"Message", JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(view.getPanel(), "the database has not been successfully deleted", "",
							JOptionPane.ERROR_MESSAGE);

				}
			}
		});

		view.getConnect().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				try (Connection cn = ConnectionPool.getPool().getConnection(view.getTfUrl().getText(),
						view.getTfLogin().getText(), view.getTfPassword().getText());
						Statement st = cn.createStatement()) {

					st.executeUpdate("USE " + ConnectionPool.NAME_DB);

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new LoginView();
						}
					});

					view.dispose();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(view.getPanel(), "no database connection", "",
							JOptionPane.ERROR_MESSAGE);

				}
			}
		});
	}

}
