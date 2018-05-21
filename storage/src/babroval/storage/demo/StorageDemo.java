package babroval.storage.demo;

import javax.swing.SwingUtilities;

import babroval.storage.controller.StartController;

public class StorageDemo {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				StartController controller = new StartController();
				controller.initController();
			}
		});
	}
}
