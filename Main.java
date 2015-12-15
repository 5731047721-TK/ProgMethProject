import javax.swing.ImageIcon;
import javax.swing.JFrame;

import logic.*;
import render.*;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame("Dragon Heart");
		try{
			ClassLoader loader = Main.class.getClassLoader();
			f.setIconImage(new ImageIcon(loader.getResource("src/popo.png")).getImage());
		}catch(Exception e){
			e.printStackTrace();
		}
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Thread mainMenu = new Thread(new MainMenu(null));
		mainMenu.start();
		GameScreen gameScreen = GameScreen.getGamescreen();
		f.add(gameScreen);
		f.setVisible(true);
		f.pack();
		gameScreen.requestFocus();

	}

}
