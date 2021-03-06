package exception;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class InvalidValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidValueException(int type) {
		super();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				switch (type) {
				case 0:
					JOptionPane.showMessageDialog(null, "No data of the specified monster", "Warning",
							JOptionPane.ERROR_MESSAGE);
					break;
				case 1:
					JOptionPane.showMessageDialog(null, "Speed is invalid", "Warning", JOptionPane.ERROR_MESSAGE);
					break;
				case 3:
					JOptionPane.showMessageDialog(null, "Level is invalid", "Warning", JOptionPane.ERROR_MESSAGE);
					break;
				}
				System.exit(0);
			}
		});
	}
}
