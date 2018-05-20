package babroval.storage.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import babroval.storage.dao.Dao;
import babroval.storage.dao.ElectricDaoImpl;
import babroval.storage.dao.StorageDaoImpl;
import babroval.storage.dao.UserDaoImpl;
import babroval.storage.model.Electric;
import babroval.storage.model.Storage;
import babroval.storage.model.User;
import babroval.storage.util.InitDB;

public class ElectricView extends JFrame {
 
	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNum, labelDate, labelName, labelIndicationLastPaid,
				   labelTariff, labelIndication, labelSum,	labelInf;
	private JComboBox<String> comboNum, comboSelect;
	private JTextField tfDate, tfName, tfIndication, tfIndicationLastPaid, tfTariff, tfSum, tfInf;
	private JButton calculate, enter, cancel;
	private String[] select = { "select:", "RENT PAYMENT", "MAIN VIEW" };

	{
		panel = new JPanel();

		labelDate = new JLabel("Date of payment:");
		Date dateNow = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		tfDate = new JTextField(sdf.format(dateNow));

		labelNum = new JLabel("Select Number of storage:");
		comboNum = new JComboBox<String>();
		comboNum.setPreferredSize(new Dimension(50, 20));
		comboNum.addItem("");

		labelName = new JLabel("Name of tenant:");
		tfName = new JTextField(20);
		tfName.setEnabled(false);

		labelIndicationLastPaid = new JLabel("Electric power indication last paid:");
		tfIndicationLastPaid = new JTextField(20);
		tfIndicationLastPaid.setEnabled(false);

		labelTariff = new JLabel("Price per kilowatt-hour:");
		tfTariff = new JTextField(20);

		labelIndication = new JLabel(
			"<html>Enter electric power indication and "
		  + "<br>press button \"Calculate\":</html>");
		tfIndication = new JTextField(20);

		calculate = new JButton("Calculate");

		labelSum = new JLabel("Total amount:");
		tfSum = new JTextField(20);
		tfSum.setEnabled(false);

		labelInf = new JLabel("Enter number of receipt order:");
		tfInf = new JTextField(20);

		enter = new JButton("Enter");
		cancel = new JButton("Cancel");

		comboSelect = new JComboBox<String>(select);
		comboSelect.setPreferredSize(new Dimension(115, 20));

		panel.add(labelDate);
		panel.add(tfDate);
		panel.add(labelNum);
		panel.add(comboNum);
		panel.add(labelName);
		panel.add(tfName);
		panel.add(labelIndicationLastPaid);
		panel.add(tfIndicationLastPaid);
		panel.add(labelTariff);
		panel.add(tfTariff);
		panel.add(labelIndication);
		panel.add(tfIndication);
		panel.add(calculate);
		panel.add(labelSum);
		panel.add(tfSum);
		panel.add(labelInf);
		panel.add(tfInf);
		panel.add(enter);
		panel.add(cancel);
		panel.add(comboSelect);

		add(panel);
	}
	
	public ElectricView() {
		setSize(270, 460);
		setTitle("Electricity payment");
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

	public JLabel getLabelNum() {
		return labelNum;
	}

	public void setLabelNum(JLabel labelNum) {
		this.labelNum = labelNum;
	}

	public JLabel getLabelDate() {
		return labelDate;
	}

	public void setLabelDate(JLabel labelDate) {
		this.labelDate = labelDate;
	}

	public JLabel getLabelName() {
		return labelName;
	}

	public void setLabelName(JLabel labelName) {
		this.labelName = labelName;
	}

	public JLabel getLabelIndicationLastPaid() {
		return labelIndicationLastPaid;
	}

	public void setLabelIndicationLastPaid(JLabel labelIndicationLastPaid) {
		this.labelIndicationLastPaid = labelIndicationLastPaid;
	}

	public JLabel getLabelTariff() {
		return labelTariff;
	}

	public void setLabelTariff(JLabel labelTariff) {
		this.labelTariff = labelTariff;
	}

	public JLabel getLabelIndication() {
		return labelIndication;
	}

	public void setLabelIndication(JLabel labelIndication) {
		this.labelIndication = labelIndication;
	}

	public JLabel getLabelSum() {
		return labelSum;
	}

	public void setLabelSum(JLabel labelSum) {
		this.labelSum = labelSum;
	}

	public JLabel getLabelInf() {
		return labelInf;
	}

	public void setLabelInf(JLabel labelInf) {
		this.labelInf = labelInf;
	}

	public JComboBox<String> getComboNum() {
		return comboNum;
	}

	public void setComboNum(JComboBox<String> comboNum) {
		this.comboNum = comboNum;
	}

	public JComboBox<String> getComboSelect() {
		return comboSelect;
	}

	public void setComboSelect(JComboBox<String> comboSelect) {
		this.comboSelect = comboSelect;
	}

	public JTextField getTfDate() {
		return tfDate;
	}

	public void setTfDate(JTextField tfDate) {
		this.tfDate = tfDate;
	}

	public JTextField getTfName() {
		return tfName;
	}

	public void setTfName(JTextField tfName) {
		this.tfName = tfName;
	}

	public JTextField getTfIndication() {
		return tfIndication;
	}

	public void setTfIndication(JTextField tfIndication) {
		this.tfIndication = tfIndication;
	}

	public JTextField getTfIndicationLastPaid() {
		return tfIndicationLastPaid;
	}

	public void setTfIndicationLastPaid(JTextField tfIndicationLastPaid) {
		this.tfIndicationLastPaid = tfIndicationLastPaid;
	}

	public JTextField getTfTariff() {
		return tfTariff;
	}

	public void setTfTariff(JTextField tfTariff) {
		this.tfTariff = tfTariff;
	}

	public JTextField getTfSum() {
		return tfSum;
	}

	public void setTfSum(JTextField tfSum) {
		this.tfSum = tfSum;
	}

	public JTextField getTfInf() {
		return tfInf;
	}

	public void setTfInf(JTextField tfInf) {
		this.tfInf = tfInf;
	}

	public JButton getCalculate() {
		return calculate;
	}

	public void setCalculate(JButton calculate) {
		this.calculate = calculate;
	}

	public JButton getEnter() {
		return enter;
	}

	public void setEnter(JButton enter) {
		this.enter = enter;
	}

	public JButton getCancel() {
		return cancel;
	}

	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}

	public String[] getSelect() {
		return select;
	}

	public void setSelect(String[] select) {
		this.select = select;
	}

}