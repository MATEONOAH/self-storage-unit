package babroval.storage.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelLogin;
	private JLabel labelPass;
	private JTextField tfLogin;
	private JPasswordField tfPass;
	private JButton enter;
	private JComboBox<Object> comboOrder;
	private String[] en = { "select payment", "Rent", "Electricity" };

	public LoginFrame() {
		setSize(260, 175);
		setTitle("Login");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
		setResizable(false);
	}

	private void initComponents() {

		panel = new JPanel();
		labelLogin = new JLabel("Login");
		labelPass = new JLabel("Password");
		tfLogin = new JTextField(20);
		tfPass = new JPasswordField(20);
		enter = new JButton("Main view");
		comboOrder = new JComboBox<Object>(en);

		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPass);
		panel.add(tfPass);
		panel.add(enter);
		panel.add(comboOrder);

		add(panel);
	}

	private void action() {

		comboOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!tfLogin.getText().equals(" ")) {
					if (comboOrder.getSelectedIndex() == 1) {

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								new RentFrame();
							}
						});
						dispose();
					}
					if (comboOrder.getSelectedIndex() == 2) {
						
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								new ElectricFrame();
							}
						});
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(panel, "incorrect login", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				if (("").equals(tfLogin.getText())) {
					char[] pass = tfPass.getPassword();
					String res = "";
					for (int i = 0; i < pass.length; i++) {
						res += pass[i];
					}
					if (res.equals("")) {

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								new AdminFrame();
							}
						});

						dispose();
					} else {
						JOptionPane.showMessageDialog(panel, "incorrect password", "", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(panel, "incorrect login", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}
}
