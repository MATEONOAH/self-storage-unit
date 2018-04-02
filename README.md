![Alt text](usage.png)

Self-Storage Unit
=================
A small Java desktop project for finance managing of the self-storage unit. 
 
[![Build Status](https://travis-ci.org/babroval/self-storage-unit.svg?branch=master)](https://travis-ci.org/babroval/self-storage-unit)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/babroval/self-storage-unit/blob/master/LICENSE)
```
		try (Connection cn = ConnectionPool.getPool().getConnection();
			 	Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(
					"SELECT users.number_storage,"
					+ " name, person_info FROM users WHERE users.year="
					+ comboYear.getSelectedItem() + " AND users."
					+ quarter + "=''")) {
			tableUsers = new TableStorage(rs);
			scroll = new JScrollPane(tableUsers);
			scroll.setBounds(20, 40, 950, 390);
			panel.add(scroll);
			panel.updateUI();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(panel, "specify quarter", "Error",
				JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
					JOptionPane.showMessageDialog(panel, "database Query", "Error",
						JOptionPane.ERROR_MESSAGE);
		}
```

Table of Contents
-----------------
  * [Requirements](#requirements)
  * [Usage](#usage)
  * [Contributing](#contributing)
  * [License](#license)  


Requirements
------------
Self-Storage Unit requires the following to run:
  * [JRE][jre] 8
  * [MySQL Community Server][mysql]  


Usage
-----
Self-Storage Unit is easiest to use with [Eclipse IDE][eclipse]:  
File -> Import -> Git -> Projects From Git > URI

#### Error handling
All exceptions are converted into unchecked type to
keep code clean as possible.
<br/>
<br/>

Contributing
------------
To contribute to Self-Storage Unit, clone this repo locally and  
commit your code on a separate branch.
<br/>
<br/>

License
-------
Self-Storage Unit is licensed under the [MIT][mit] license.  

[jre]: http://www.oracle.com/technetwork/java/javase/downloads/
[mysql]: https://dev.mysql.com/downloads/mysql/
[eclipse]: https://www.eclipse.org/downloads/
[mit]: https://github.com/babroval/self-storage-unit/blob/master/LICENSE/
