package logic;

import java.awt.Graphics2D;

import exception.InvalidValueException;
import render.Background;
import render.Foreground;
import render.IRenderable;
import render.RenderableHolder;

public class Map implements Runnable{
	private int map[][];
	private Player p1;
	private int no;
	private Background bg[];
	private Foreground fg[];
	private Foreground intf;
	private int screenX;
	private int DelaySpawnMon;
	private int DelaySpawnCount;
	public Map(int level) throws InvalidValueException{
		p1 = new Player(5);
		Thread player = new Thread(p1);
		DelaySpawnMon = 200/level;
		DelaySpawnCount = DelaySpawnMon;
		no = 1;
		bg = new Background[4];
		for(int i=0;i<bg.length;i++){
			bg[i] = new Background(p1, 1, i+1);
		}
		intf = new Foreground(null, 7777, false, 0);
		fg = new Foreground[4];
		for(int i =0;i<fg.length;i++){
			fg[i] = new Foreground(p1, 1, false,i);
		}
		Monster m[] = new Monster[20];
		Thread mt[] = new Thread[20];
		for(int i =0;i<20;i++){
			int ran = (int) (Math.random() * 100 % 4 + 1);
			m[i] = new Monster(ran, p1);
			mt[i] = new Thread(m[i]);
			mt[i].start();
		}
		player.start();
		
	}

	public void mapManagement(){
		for(int i=0; i< bg.length-1;i++){
			bg[i].setFade((i+1)*Data.foregroundWidth/400f - (float)p1.getX()/400f);	
		}

		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			mapManagement();
		}
	}
}
