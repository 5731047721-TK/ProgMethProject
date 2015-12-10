
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
//		Player player1 = new Player(0, 5, 0);
//		new Background(player1,1);
//		new Foreground(player1,1);
		Thread mainMenu = new Thread(new MainMenu());
		mainMenu.start();
		GameScreen gameScreen = GameScreen.getGamescreen();

		f.add(gameScreen);

		f.setVisible(true);
		f.pack();
		gameScreen.requestFocus();
//		player1.play();
		InputUtility instance = InputUtility.getInstance();
	/*	while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (RenderableHolder.getInstance()) {
				if(instance.getKeypressed(KeyEvent.VK_Q))
					System.out.println(RenderableHolder.getInstance().getRenderableList().toString());
			}
			
//			System.out.println(Thread.currentThread());
			synchronized (instance) {
				instance.postUpdate();
			}
			gameScreen.repaint();
			
		}*/
	}

}
