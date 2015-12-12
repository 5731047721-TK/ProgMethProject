package exception;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class InvalidValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidValueException(int type){
		super();
		switch(type){
		case 0:
			JOptionPane.showMessageDialog(null,"No data of the specified monster",
                "Error", JOptionPane.ERROR_MESSAGE);
		case 1:
			JOptionPane.showMessageDialog(null,"Speed is invalid",
	                "Error", JOptionPane.ERROR_MESSAGE);
		
		}
	}
}
