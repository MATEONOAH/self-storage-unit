package babroval.storage.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelLogin, labelPassword;
	private JTextField tfLogin;
	private JPasswordField tfPassword;
	private JButton mainView;
	private JComboBox<String> comboOrder;
	private String[] select = { "select payment:", "RENT", "ELECTRICITY" };

	{
		panel = new JPanel();
		labelLogin = new JLabel("Login");
		labelPassword = new JLabel("Password");
		tfLogin = new JTextField(20);
		tfPassword = new JPasswordField(20);
		mainView = new JButton("MAIN VIEW");
		comboOrder = new JComboBox<String>(select);

		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPassword);
		panel.add(tfPassword);
		panel.add(mainView);
		panel.add(comboOrder);

		add(panel);
	}

	public LoginView() {
		setSize(260, 175);
		setTitle("Login");
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

	public JTextField getTfLogin() {
		return tfLogin;
	}

	public void setTfLogin(JTextField tfLogin) {
		this.tfLogin = tfLogin;
	}

	public JPasswordField getTfPassword() {
		return tfPassword;
	}

	public void setTfPassword(JPasswordField tfPassword) {
		this.tfPassword = tfPassword;
	}

	public JButton getMainView() {
		return mainView;
	}

	public void setMainView(JButton mainView) {
		this.mainView = mainView;
	}

	public JComboBox<String> getComboOrder() {
		return comboOrder;
	}

	public void setComboOrder(JComboBox<String> comboOrder) {
		this.comboOrder = comboOrder;
	}

	public String[] getSelect() {
		return select;
	}

	public void setSelect(String[] select) {
		this.select = select;
	}

}
