package logic;

import java.awt.event.KeyEvent;

import input.InputUtility;
import render.Background;
import render.Foreground;
import render.RenderableHolder;

public class MainMenu implements Runnable{
	private Background bg;
	private Foreground eye;
	private Foreground select;
	private int option;
	
	public MainMenu(){
		bg = new Background(null, 9998);
		eye = new Foreground(null, 9998,true);
		option = 0;
	}
	

	public void space(){//press space bar
		RenderableHolder.getInstance().getRenderableList().remove(bg);
		bg = new Background(null, 9999);
	}

	public void start() {
		// TODO Auto-generated method stub
		RenderableHolder.getInstance().getRenderableList().remove(bg);
		Player player1 = new Player(0, 5, 0);
		new Background(player1,1);
		new Foreground(player1,1,false);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(InputUtility.getKeytriggered(KeyEvent.VK_SPACE)){
				space();
				break;
			}
		} while(true){
			System.out.println(option);
			if(InputUtility.getKeytriggered(KeyEvent.VK_DOWN)){
				if(option < 3)
					option++;
				else
					option = 0;
			}else if(InputUtility.getKeytriggered(KeyEvent.VK_UP)){
				if(option > 0)
					option--;
				else option = 3;
			}else if(InputUtility.getKeytriggered(KeyEvent.VK_SPACE)){
				switch(option){
					case 0: start();
					case 3: System.exit(0);
				}
			}
			
		}
	}


	
	
	
}
