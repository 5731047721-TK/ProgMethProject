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
		Player player1 = new Player(0, 5, 0);
//		new Background(player1,1);
//		new Foreground(player1,1);
		Thread mainMenu = new Thread(new MainMenu());
		mainMenu.start();
		GameScreen gameScreen = new GameScreen();

		f.add(gameScreen);

		f.setVisible(true);
		f.pack();
		gameScreen.requestFocus();
		player1.play();
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(InputUtility.getKeytriggered(KeyEvent.VK_Z)){
				if(player1.isOnGround())
					player1.hit();
				else
					player1.jumpHit();
			}
			if (InputUtility.getKeypressed(KeyEvent.VK_RIGHT)) {
				player1.walk(true);
			}
			else if (InputUtility.getKeypressed(KeyEvent.VK_LEFT)) {
				player1.walk(false);
			}
			else if(player1.getStatus() != 2 && player1.getStatus() != 3){
				player1.stand();
			}
			if (InputUtility.getKeytriggered(KeyEvent.VK_SPACE) &&player1.getStatus() != 3) {
				player1.jump();
			}
			player1.updateAnimation();
			player1.updatePosition();
			InputUtility.postUpdate();
			gameScreen.repaint();
			
		}
	}

}
