package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import babroval.storage.mysql.ConnectionPool;
import babroval.storage.mysql.InitDB;

public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelUrl, labelLogin, labelPassword;
	private JTextField tfUrl, tfLogin, tfPassword;
	private JButton create, delete, connect;

	{
		panel = new JPanel();
		labelUrl = new JLabel("URL");
		labelLogin = new JLabel("Login");
		labelPassword = new JLabel("Password");
		tfUrl = new JTextField("jdbc:mysql://localhost", 20);
		tfLogin = new JTextField("root", 20);
		tfPassword = new JTextField("root", 20);
		create = new JButton("create");
		delete = new JButton("delete");
		connect = new JButton("connect");

		panel.add(labelUrl);
		panel.add(tfUrl);
		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPassword);
		panel.add(tfPassword);
		panel.add(create);
		panel.add(delete);
		panel.add(connect);

		add(panel);
	}
	
	public StartFrame() {
		setSize(260, 220);
		setTitle("Storage");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		action();
		setVisible(true);
	}
	
	private void action() {
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					InitDB.createDB(tfUrl.getText(), tfLogin.getText(), tfPassword.getText());
					
					JOptionPane.showMessageDialog(panel, "The database has been successfully created", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "the database has not been successfully created", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					InitDB.deleteDB(tfUrl.getText(), tfLogin.getText(), tfPassword.getText());
					
					JOptionPane.showMessageDialog(panel, "The database has been successfully deleted", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "the database has not been successfully deleted", "", JOptionPane.ERROR_MESSAGE);

				}
			}
		});

		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				try (Connection cn = ConnectionPool.getPool().getConnection(tfUrl.getText(), tfLogin.getText(),
						tfPassword.getText()); Statement st = cn.createStatement()) {
					
					st.executeUpdate("USE " + ConnectionPool.NAME_DB);
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new LoginFrame();
						}
					});
					
					dispose();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "no database connection", "", JOptionPane.ERROR_MESSAGE);

				}
			}
		});

	}

}
