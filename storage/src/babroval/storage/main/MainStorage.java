package babroval.storage.main;

import javax.swing.SwingUtilities;

import babroval.storage.frames.StartFrameStorage;


public class MainStorage {

	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new StartFrameStorage();
			}
		});
	}
	
}
