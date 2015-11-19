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
		new Background(player1);
		new Foreground(player1);
		GameScreen gameScreen = new GameScreen();

		f.add(gameScreen);

		f.setVisible(true);
		f.pack();
		gameScreen.requestFocus();
		player1.play();
		//Debug
//		int i =0;
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameScreen.repaint();
			
//			if ((player1.getStatus() == 3 || player1.getStatus() == 2) && InputUtility.getKeytriggered(KeyEvent.VK_Z)) {
//				System.out.println("JumpHit!");
//				player1.jumpHit();
//			}	
			if(InputUtility.getKeytriggered(KeyEvent.VK_Z)){
				if(player1.isOnGround())
					player1.hit();
				else
					player1.jumpHit();
			}
			if (InputUtility.getKeypressed(KeyEvent.VK_RIGHT)) {
//				 System.out.println("Right!");
				player1.walk(true);
			}
			else if (InputUtility.getKeypressed(KeyEvent.VK_LEFT)) {
				// System.out.println("L!");
				player1.walk(false);
			}
			else if(player1.getStatus() != 2 && player1.getStatus() != 3){
				player1.stand();
			}
			if (InputUtility.getKeytriggered(KeyEvent.VK_SPACE) &&player1.getStatus() != 3) {
//				 System.out.println("Jump!");
				player1.jump();
			}
//			else if( InputUtility.getKeypressed(KeyEvent.VK_SPACE) == false && !player1.isOnGround()){
//				player1.fall();
//			}
			
//			System.out.println(player1.getStatus());
//			i++;
//			if(i%20==0)
//				System.out.println();
			player1.updateAnimation();
			player1.updatePosition();
			InputUtility.postUpdate();
		}
	}

}
