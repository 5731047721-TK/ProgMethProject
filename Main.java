
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import input.InputUtility;
import logic.*;
import render.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("Dragon Heart");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Thread mainMenu = new Thread(new MainMenu());
		mainMenu.start();
		GameScreen gameScreen = GameScreen.getGamescreen();
		f.add(gameScreen);
		f.setVisible(true);
		f.pack();
		gameScreen.requestFocus();

	}

}
