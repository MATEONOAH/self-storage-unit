package babroval.storage.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import babroval.storage.util.DateUtil;
import babroval.storage.util.TableStorage;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JComboBox<String> comboRead, comboEdit, comboPayment, comboNum, comboYear, comboNumEdit, comboUserEdit;
	private JLabel labelComboNum, labelQuarts, labelNumber, labelName, labelNewUserName, labelNewUserInfo,
			labelNewStorageNum, labelNewStorageInfo;
	private JTextField tfUserName, tfUserInfo, tfStorageNum, tfStorageInfo;
	private JScrollPane scroll;
	private JButton cancel, delete, save, sortFamily, rentDebtors;
	private JCheckBox quart1, quart2, quart3, quart4, editStorage, addStorage, deleteStorage, editUser, addUser;
	private ButtonGroup groupQuarter, groupStorage;
	private TableStorage table;
	private JMenuBar menuBar;
	private JMenuItem itemWrite, itemAbout, itemExit;
	private JMenu file, about;
	private JFileChooser chooser;
	private String[] selectRead;
	private String[] selectEdit;
	private String[] selectPayment ;

	{
		selectRead = new String[] {"select:", "RENT PAYMENT", "ELECTRYCITY PAYMENT", "TENANTS" };
		selectEdit = new String[] { "edit:", "RENT PAYMENT", "ELECTRYCITY PAYMENT", "TENANTS" };
		selectPayment = new String[] { "select payment:", "RENT", "ELECTRYCITY" };
		
		panel = new JPanel(null);

		chooser = new JFileChooser();
		menuBar = new JMenuBar();
		file = new JMenu("File");
		about = new JMenu("About");
		itemExit = new JMenuItem("Exit");
		itemWrite = new JMenuItem("Save as *.xls");
		itemAbout = new JMenuItem("About");

		comboRead = new JComboBox<String>(selectRead);
		comboEdit = new JComboBox<String>(selectEdit);
		comboPayment = new JComboBox<String>(selectPayment);

		sortFamily = new JButton("Sort by last name");
		table = new TableStorage();
		scroll = new JScrollPane(table);
		cancel = new JButton("Cancel");
		save = new JButton("Save");
		delete = new JButton("Delete");

		labelQuarts = new JLabel("Quarters of the year");
		quart1 = new JCheckBox("I");
		quart2 = new JCheckBox("II");
		quart3 = new JCheckBox("III");
		quart4 = new JCheckBox("IV");
		groupQuarter = new ButtonGroup();
		rentDebtors = new JButton("Rent debtors");

		labelComboNum = new JLabel("Rent payment for storage:");
		comboNum = new JComboBox<String>();
		comboNum.addItem("");
		labelNumber = new JLabel("Select number of storage:");
		comboNumEdit = new JComboBox<String>();
		comboNumEdit.addItem("");
		labelName = new JLabel("Select tenant:");
		comboUserEdit = new JComboBox<String>();
		comboUserEdit.addItem("");

		editStorage = new JCheckBox("Edit storage");
		addStorage = new JCheckBox("Add storage");
		deleteStorage = new JCheckBox("Delete storage");
		editUser = new JCheckBox("Edit tenant");
		addUser = new JCheckBox("Add tenant");
		groupStorage = new ButtonGroup();
		labelNewStorageNum = new JLabel("Number of storage:");
		tfStorageNum = new JTextField(150);
		labelNewStorageInfo = new JLabel("Additional information of storage:");
		tfStorageInfo = new JTextField(300);
		labelNewUserName = new JLabel("Name of tenant:");
		tfUserName = new JTextField(150);
		labelNewUserInfo = new JLabel("Personal information of tenant:");
		tfUserInfo = new JTextField(300);

		int i = DateUtil.getTodayYear();
		String[] year = { String.valueOf(i - 1), String.valueOf(i), String.valueOf(i + 1) };
		comboYear = new JComboBox<String>(year);
		comboYear.setSelectedIndex(1);

		sortFamily.setVisible(false);
		cancel.setEnabled(false);
		save.setEnabled(false);
		delete.setEnabled(false);
		itemWrite.setEnabled(false);
		labelNumber.setVisible(false);
		comboNumEdit.setVisible(false);
		labelName.setVisible(false);
		comboUserEdit.setVisible(false);
		editStorage.setVisible(false);
		addStorage.setVisible(false);
		deleteStorage.setVisible(false);
		editUser.setVisible(false);
		addUser.setVisible(false);
		labelNewStorageNum.setVisible(false);
		tfStorageNum.setVisible(false);
		labelNewStorageInfo.setVisible(false);
		tfStorageInfo.setVisible(false);
		tfUserName.setVisible(false);
		labelNewUserName.setVisible(false);
		tfUserInfo.setVisible(false);
		labelNewUserInfo.setVisible(false);

		comboRead.setBounds(30, 10, 160, 20);
		labelComboNum.setBounds(200, 10, 150, 20);
		comboNum.setBounds(352, 10, 70, 20);
		sortFamily.setBounds(430, 10, 140, 20);
		quart1.setBounds(602, 10, 30, 20);
		quart2.setBounds(630, 10, 32, 20);
		quart3.setBounds(660, 10, 35, 20);
		quart4.setBounds(692, 10, 40, 20);
		comboYear.setBounds(732, 10, 60, 20);
		rentDebtors.setBounds(800, 10, 110, 20);
		scroll.setBounds(20, 40, 950, 390);
		comboEdit.setBounds(30, 450, 160, 20);
		save.setBounds(205, 450, 110, 20);
		cancel.setBounds(325, 450, 110, 20);
		delete.setBounds(445, 450, 110, 20);
		comboPayment.setBounds(695, 450, 130, 20);
		labelNumber.setBounds(30, 80, 160, 20);
		comboNumEdit.setBounds(30, 100, 200, 20);
		editStorage.setBounds(30, 130, 100, 20);
		addStorage.setBounds(30, 160, 100, 20);
		labelNewStorageNum.setBounds(30, 190, 200, 20);
		tfStorageNum.setBounds(30, 210, 200, 20);
		labelNewStorageInfo.setBounds(30, 250, 200, 20);
		tfStorageInfo.setBounds(30, 270, 200, 20);
		deleteStorage.setBounds(30, 310, 120, 20);
		labelName.setBounds(300, 80, 160, 20);
		comboUserEdit.setBounds(300, 100, 400, 20);
		editUser.setBounds(300, 130, 100, 20);
		addUser.setBounds(300, 160, 100, 20);
		labelNewUserName.setBounds(300, 190, 200, 20);
		tfUserName.setBounds(300, 210, 300, 20);
		labelNewUserInfo.setBounds(300, 250, 200, 20);
		tfUserInfo.setBounds(300, 270, 400, 20);

		file.add(itemWrite);
		file.add(itemExit);
		about.add(itemAbout);
		menuBar.add(file);
		menuBar.add(about);
		setJMenuBar(menuBar);

		groupQuarter.add(quart1);
		groupQuarter.add(quart2);
		groupQuarter.add(quart3);
		groupQuarter.add(quart4);

		groupStorage.add(editStorage);
		groupStorage.add(addStorage);
		groupStorage.add(deleteStorage);
		groupStorage.add(editUser);
		groupStorage.add(addUser);

		panel.add(comboRead);
		panel.add(labelComboNum);
		panel.add(comboNum);
		panel.add(sortFamily);
		panel.add(labelQuarts);
		panel.add(quart1);
		panel.add(quart2);
		panel.add(quart3);
		panel.add(quart4);
		panel.add(comboYear);
		panel.add(rentDebtors);
		panel.add(comboEdit);
		panel.add(cancel);
		panel.add(save);
		panel.add(delete);
		panel.add(comboPayment);
		panel.add(labelNumber);
		panel.add(comboNumEdit);
		panel.add(labelName);
		panel.add(comboUserEdit);
		panel.add(editStorage);
		panel.add(addStorage);
		panel.add(deleteStorage);
		panel.add(editUser);
		panel.add(addUser);
		panel.add(labelNewStorageNum);
		panel.add(tfStorageNum);
		panel.add(labelNewStorageInfo);
		panel.add(tfStorageInfo);
		panel.add(labelNewUserName);
		panel.add(tfUserName);
		panel.add(labelNewUserInfo);
		panel.add(tfUserInfo);

		add(panel);
	}

	public AdminView() {
		setSize(995, 550);
		setTitle("AdminFrame");
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

	public JComboBox<String> getComboRead() {
		return comboRead;
	}

	public void setComboRead(JComboBox<String> comboRead) {
		this.comboRead = comboRead;
	}

	public JComboBox<String> getComboEdit() {
		return comboEdit;
	}

	public void setComboEdit(JComboBox<String> comboEdit) {
		this.comboEdit = comboEdit;
	}

	public JComboBox<String> getComboPayment() {
		return comboPayment;
	}

	public void setComboPayment(JComboBox<String> comboPayment) {
		this.comboPayment = comboPayment;
	}

	public JComboBox<String> getComboNum() {
		return comboNum;
	}

	public void setComboNum(JComboBox<String> comboNum) {
		this.comboNum = comboNum;
	}

	public JComboBox<String> getComboYear() {
		return comboYear;
	}

	public void setComboYear(JComboBox<String> comboYear) {
		this.comboYear = comboYear;
	}

	public JComboBox<String> getComboNumEdit() {
		return comboNumEdit;
	}

	public void setComboNumEdit(JComboBox<String> comboNumEdit) {
		this.comboNumEdit = comboNumEdit;
	}

	public JComboBox<String> getComboUserEdit() {
		return comboUserEdit;
	}

	public void setComboUserEdit(JComboBox<String> comboUserEdit) {
		this.comboUserEdit = comboUserEdit;
	}

	public JLabel getLabelComboNum() {
		return labelComboNum;
	}

	public void setLabelComboNum(JLabel labelComboNum) {
		this.labelComboNum = labelComboNum;
	}

	public JLabel getLabelQuarts() {
		return labelQuarts;
	}

	public void setLabelQuarts(JLabel labelQuarts) {
		this.labelQuarts = labelQuarts;
	}

	public JLabel getLabelNumber() {
		return labelNumber;
	}

	public void setLabelNumber(JLabel labelNumber) {
		this.labelNumber = labelNumber;
	}

	public JLabel getLabelName() {
		return labelName;
	}

	public void setLabelName(JLabel labelName) {
		this.labelName = labelName;
	}

	public JLabel getLabelNewUserName() {
		return labelNewUserName;
	}

	public void setLabelNewUserName(JLabel labelNewUserName) {
		this.labelNewUserName = labelNewUserName;
	}

	public JLabel getLabelNewUserInfo() {
		return labelNewUserInfo;
	}

	public void setLabelNewUserInfo(JLabel labelNewUserInfo) {
		this.labelNewUserInfo = labelNewUserInfo;
	}

	public JLabel getLabelNewStorageNum() {
		return labelNewStorageNum;
	}

	public void setLabelNewStorageNum(JLabel labelNewStorageNum) {
		this.labelNewStorageNum = labelNewStorageNum;
	}

	public JLabel getLabelNewStorageInfo() {
		return labelNewStorageInfo;
	}

	public void setLabelNewStorageInfo(JLabel labelNewStorageInfo) {
		this.labelNewStorageInfo = labelNewStorageInfo;
	}

	public JTextField getTfUserName() {
		return tfUserName;
	}

	public void setTfUserName(JTextField tfUserName) {
		this.tfUserName = tfUserName;
	}

	public JTextField getTfUserInfo() {
		return tfUserInfo;
	}

	public void setTfUserInfo(JTextField tfUserInfo) {
		this.tfUserInfo = tfUserInfo;
	}

	public JTextField getTfStorageNum() {
		return tfStorageNum;
	}

	public void setTfStorageNum(JTextField tfStorageNum) {
		this.tfStorageNum = tfStorageNum;
	}

	public JTextField getTfStorageInfo() {
		return tfStorageInfo;
	}

	public void setTfStorageInfo(JTextField tfStorageInfo) {
		this.tfStorageInfo = tfStorageInfo;
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}

	public JButton getCancel() {
		return cancel;
	}

	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}

	public JButton getDelete() {
		return delete;
	}

	public void setDelete(JButton delete) {
		this.delete = delete;
	}

	public JButton getSave() {
		return save;
	}

	public void setSave(JButton save) {
		this.save = save;
	}

	public JButton getSortFamily() {
		return sortFamily;
	}

	public void setSortFamily(JButton sortFamily) {
		this.sortFamily = sortFamily;
	}

	public JButton getRentDebtors() {
		return rentDebtors;
	}

	public void setRentDebtors(JButton rentDebtors) {
		this.rentDebtors = rentDebtors;
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

	public JCheckBox getEditStorage() {
		return editStorage;
	}

	public void setEditStorage(JCheckBox editStorage) {
		this.editStorage = editStorage;
	}

	public JCheckBox getAddStorage() {
		return addStorage;
	}

	public void setAddStorage(JCheckBox addStorage) {
		this.addStorage = addStorage;
	}

	public JCheckBox getDeleteStorage() {
		return deleteStorage;
	}

	public void setDeleteStorage(JCheckBox deleteStorage) {
		this.deleteStorage = deleteStorage;
	}

	public JCheckBox getEditUser() {
		return editUser;
	}

	public void setEditUser(JCheckBox editUser) {
		this.editUser = editUser;
	}

	public JCheckBox getAddUser() {
		return addUser;
	}

	public void setAddUser(JCheckBox addUser) {
		this.addUser = addUser;
	}

	public ButtonGroup getGroupQuarter() {
		return groupQuarter;
	}

	public void setGroupQuarter(ButtonGroup groupQuarter) {
		this.groupQuarter = groupQuarter;
	}

	public ButtonGroup getGroupStorage() {
		return groupStorage;
	}

	public void setGroupStorage(ButtonGroup groupStorage) {
		this.groupStorage = groupStorage;
	}

	public TableStorage getTable() {
		return table;
	}

	public void setTable(TableStorage table) {
		this.table = table;
	}

	public JMenuBar getMenu() {
		return menuBar;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenuItem getItemWrite() {
		return itemWrite;
	}

	public void setItemWrite(JMenuItem itemWrite) {
		this.itemWrite = itemWrite;
	}

	public JMenuItem getItemAbout() {
		return itemAbout;
	}

	public void setItemAbout(JMenuItem itemAbout) {
		this.itemAbout = itemAbout;
	}

	public JMenuItem getItemExit() {
		return itemExit;
	}

	public void setItemExit(JMenuItem itemExit) {
		this.itemExit = itemExit;
	}

	public JMenu getFile() {
		return file;
	}

	public void setFile(JMenu file) {
		this.file = file;
	}

	public JMenu getAbout() {
		return about;
	}

	public void setAbout(JMenu about) {
		this.about = about;
	}

	public JFileChooser getChooser() {
		return chooser;
	}

	public void setChooser(JFileChooser chooser) {
		this.chooser = chooser;
	}

	public String[] getSelectRead() {
		return selectRead;
	}

	public void setSelectRead(String[] selectRead) {
		this.selectRead = selectRead;
	}

	public String[] getSelectEdit() {
		return selectEdit;
	}

	public void setSelectEdit(String[] selectEdit) {
		this.selectEdit = selectEdit;
	}

	public String[] getSelectPayment() {
		return selectPayment;
	}

	public void setSelectPayment(String[] selectPayment) {
		this.selectPayment = selectPayment;
	}

}