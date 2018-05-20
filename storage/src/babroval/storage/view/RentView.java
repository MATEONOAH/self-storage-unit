package babroval.storage.view;

import java.awt.Dimension;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RentView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel labelNumber, labelDate, labelName, labelQuarter, labelSum, labelYear, labelInf;
	private JComboBox<String> comboNum, comboSelect;
	private JTextField tfDate, tfName, tfSum, tfInf;
	private JCheckBox quart1, quart2, quart3, quart4;
	private JButton enter, cancel;
	private String[] select = { "select:", "ELECTRICITY PAYMENT", "MAIN VIEW" };
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	{
		panel = new JPanel();

		labelDate = new JLabel("Date of payment:");

		Date today = new Date(System.currentTimeMillis());
		tfDate = new JTextField(sdf.format(today));

		labelNumber = new JLabel("Select Number of storage:");
		comboNum = new JComboBox<String>();
		comboNum.setPreferredSize(new Dimension(50, 20));
		comboNum.addItem("");

		labelName = new JLabel("Name of tenant:");
		tfName = new JTextField(20);
		tfName.setEnabled(false);

		labelQuarter = new JLabel("Select Quarter of");

		quart1 = new JCheckBox("I");
		quart2 = new JCheckBox("II");
		quart3 = new JCheckBox("III");
		quart4 = new JCheckBox("IV");

		labelYear = new JLabel("Year");

		labelSum = new JLabel("Enter rent amount:");
		tfSum = new JTextField(20);

		labelInf = new JLabel("Enter number of receipt order:");
		tfInf = new JTextField(20);

		enter = new JButton("Enter");
		cancel = new JButton("Cancel");

		comboSelect = new JComboBox<String>(select);
		comboSelect.setPreferredSize(new Dimension(180, 20));

		panel.add(labelDate);
		panel.add(tfDate);
		panel.add(labelNumber);
		panel.add(comboNum);
		panel.add(labelName);
		panel.add(tfName);
		panel.add(labelQuarter);
		panel.add(labelYear);
		panel.add(quart1);
		panel.add(quart2);
		panel.add(quart3);
		panel.add(quart4);
		panel.add(labelSum);
		panel.add(tfSum);
		panel.add(labelInf);
		panel.add(tfInf);
		panel.add(enter);
		panel.add(cancel);
		panel.add(comboSelect);

		add(panel);
	}

	public RentView() {
		setSize(300, 327);
		setTitle("Rent payment");
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

	public JLabel getLabelNumber() {
		return labelNumber;
	}

	public void setLabelNumber(JLabel labelNumber) {
		this.labelNumber = labelNumber;
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

	public JLabel getLabelQuarter() {
		return labelQuarter;
	}

	public void setLabelQuarter(JLabel labelQuarter) {
		this.labelQuarter = labelQuarter;
	}

	public JLabel getLabelSum() {
		return labelSum;
	}

	public void setLabelSum(JLabel labelSum) {
		this.labelSum = labelSum;
	}

	public JLabel getLabelYear() {
		return labelYear;
	}

	public void setLabelYear(JLabel labelYear) {
		this.labelYear = labelYear;
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

	public JCheckBox getQuart1() {
		return quart1;
	}

	public void setQuart1(JCheckBox quart1) {
		this.quart1 = quart1;
	}

	public JCheckBox getQuart2() {
		return quart2;
	}

	public void setQuart2(JCheckBox quart2) {
		this.quart2 = quart2;
	}

	public JCheckBox getQuart3() {
		return quart3;
	}

	public void setQuart3(JCheckBox quart3) {
		this.quart3 = quart3;
	}

	public JCheckBox getQuart4() {
		return quart4;
	}

	public void setQuart4(JCheckBox quart4) {
		this.quart4 = quart4;
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

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

}
