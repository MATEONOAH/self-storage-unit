package babroval.storage.demo;

import javax.swing.SwingUtilities;

import babroval.storage.controller.StartController;
import babroval.storage.view.StartView;

public class StorageDemo {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StartView view = new StartView();
				StartController controller = new StartController(view);
				controller.initController();
			}
		});
	}
}
