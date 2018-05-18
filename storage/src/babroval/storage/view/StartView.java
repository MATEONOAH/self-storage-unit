package babroval.storage.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartView extends JFrame {

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
	
	public StartView() {
		setSize(260, 220);
		setTitle("Storage");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	

	public JPanel getPanel() {
		return panel;
	}


	public void setPanel(JPanel panel) {
		this.panel = panel;
	}


	public JLabel getLabelUrl() {
		return labelUrl;
	}


	public void setLabelUrl(JLabel labelUrl) {
		this.labelUrl = labelUrl;
	}


	public JLabel getLabelLogin() {
		return labelLogin;
	}


	public void setLabelLogin(JLabel labelLogin) {
		this.labelLogin = labelLogin;
	}


	public JLabel getLabelPassword() {
		return labelPassword;
	}


	public void setLabelPassword(JLabel labelPassword) {
		this.labelPassword = labelPassword;
	}


	public JTextField getTfUrl() {
		return tfUrl;
	}


	public void setTfUrl(JTextField tfUrl) {
		this.tfUrl = tfUrl;
	}


	public JTextField getTfLogin() {
		return tfLogin;
	}


	public void setTfLogin(JTextField tfLogin) {
		this.tfLogin = tfLogin;
	}


	public JTextField getTfPassword() {
		return tfPassword;
	}


	public void setTfPassword(JTextField tfPassword) {
		this.tfPassword = tfPassword;
	}


	public JButton getCreate() {
		return create;
	}


	public void setCreate(JButton create) {
		this.create = create;
	}


	public JButton getDelete() {
		return delete;
	}


	public void setDelete(JButton delete) {
		this.delete = delete;
	}


	public JButton getConnect() {
		return connect;
	}


	public void setConnect(JButton connect) {
		this.connect = connect;
	}

}
