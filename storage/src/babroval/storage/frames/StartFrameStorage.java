package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import babroval.storage.mysql.ConnectionPool;
import babroval.storage.mysql.InitDBase;

public class StartFrameStorage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelUrl, labelLogin, labelPass;
	private JTextField tfUrl, tfLogin, tfPass;
	private JButton create, delete, connect;

	public StartFrameStorage() {
		setSize(260, 220);
		setTitle("Storage");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
		setResizable(false);
	}

	public JTextField getTfUrl() {
		return tfUrl;
	}

	public JTextField getTfLogin() {
		return tfLogin;
	}

	public JTextField getTfPass() {
		return tfPass;
	}

	private void initComponents() {
		panel = new JPanel();
		labelUrl = new JLabel("URL");
		labelLogin = new JLabel("Login");
		labelPass = new JLabel("Pass");
		tfUrl = new JTextField("jdbc:mysql://localhost", 20);
		tfLogin = new JTextField("root", 20);
		tfPass = new JTextField("root", 20);
		create = new JButton("create");
		delete = new JButton("delete");
		connect = new JButton("connect");

		panel.add(labelUrl);
		panel.add(tfUrl);
		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPass);
		panel.add(tfPass);
		panel.add(create);
		panel.add(delete);
		panel.add(connect);

		add(panel);
	}

	private void action() {
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					InitDBase.createDB(tfUrl.getText(), tfLogin.getText(), tfPass.getText());
					
					JOptionPane.showMessageDialog(panel, "Database create successful", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database create error", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					InitDBase.deleteDB(tfUrl.getText(), tfLogin.getText(), tfPass.getText());
					
					JOptionPane.showMessageDialog(panel, "Database delete successful", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				
				} catch (Exception e) {
					JOptionPane.showMessageDialog(panel, "database delete error", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});

		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				try (Connection cn = ConnectionPool.getPool().getConnection(tfUrl.getText(), tfLogin.getText(),
						tfPass.getText()); Statement st = cn.createStatement()) {
					
					st.executeUpdate("USE " + ConnectionPool.NAME_DB);
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new LoginFrameStorage();
						}
					});
					
					dispose();
					
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(panel, "database connect error", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});

	}

}
