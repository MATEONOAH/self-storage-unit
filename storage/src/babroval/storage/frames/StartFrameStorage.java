package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import babroval.storage.mysql.ConnectionPool;
import babroval.storage.mysql.WorkDBase;

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
			public void actionPerformed(ActionEvent e) {
				try {
					WorkDBase.createDB(tfUrl.getText(), tfLogin.getText(), tfPass.getText());
					JOptionPane.showMessageDialog(panel, "Database create succsesfull", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(panel, "Database create error", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					WorkDBase.deleteDB(tfUrl.getText(), tfLogin.getText(), tfPass.getText());
					JOptionPane.showMessageDialog(panel, "Data Base delete succsesfull", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(panel, "Data Base delete error", "Error", JOptionPane.ERROR_MESSAGE);
				
				}
			}
		});

		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try (Connection cn = ConnectionPool.getPool().getConnection(tfUrl.getText(), tfLogin.getText(), tfPass.getText())) {
					
					new LoginFrameStorage();
					dispose();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(panel, "Data Base connect error", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		
	}
	
}
