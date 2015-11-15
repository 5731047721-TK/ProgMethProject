import javax.swing.JFrame;

import logic.*;
import render.GameScreen;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f  = new JFrame("Dragon Heart");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Player player1 = new Player(0,2,0);
		GameScreen gameScreen = new GameScreen();
		
		f.add(gameScreen);
		f.setVisible(true);
		f.pack();
		
		int test = 1000;
		int time = 0;
		while(true){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameScreen.repaint();
			if(test > 0 && !player1.isPlaying()){
				player1.play();
				test--;
			}
			player1.updateAnimation();
			if(player1.getX() >= 200 && player1.getY() <= 200){
				int a = -1;
				player1.setY(player1.getY()-(player1.getSpeed()*time/10+(time*time*a)/200));
				player1.setX(player1.getX()+player1.getSpeed());
				time++;
			} else{
				time = 0;
				player1.setY(200);
				player1.setX(player1.getX()+player1.getSpeed());
			}
		}
	}
	
}
