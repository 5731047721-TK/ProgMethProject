package logic;

import java.awt.Graphics2D;

import exception.InvalidValueException;
import render.Background;
import render.Foreground;
import render.IRenderable;
import render.RenderableHolder;
import render.Title;

public class Map implements Runnable{
	private int map[][];
	private Player p1;
	private Monster m[];
	private int no;
	private Background bg[];
	private Foreground fg[];
	private Foreground intf;
	private int screenX;
	private int DelaySpawnMon;
	private int DelaySpawnCount;
	private Thread prevThread;
	public Map(int level, Thread prevThread) throws InvalidValueException{
		if(level <= 0)
			throw new InvalidValueException(3);
		this.prevThread = prevThread;
		
		DelaySpawnMon = 200/level;
		DelaySpawnCount = DelaySpawnMon;
		no = 1;
		
		
	}

	public void mapManagement(){
		for(int i=0; i< bg.length-1;i++){
			bg[i].setFade((i+1)*Data.foregroundWidth/400f - (float)p1.getX()/400f);	
		}
		if(p1.getX() > Data.levelExtent - Data.screenWidth + Data.screenWidth/3){
			p1.lowerBoundX = Data.levelExtent - Data.screenWidth;
			for(int i=0;i<fg.length;i++){
				fg[i].lock = true;
				bg[i].lock = true;	
			}
			p1.lock = true;
			for(int i=0;i<m.length;i++){
				m[i].die();
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (prevThread != null) {
			try {
				prevThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Title t = new Title(101, prevThread);
		Thread tT = new Thread(t);
		tT.start();
		try {
			p1 = new Player(5);
		} catch (InvalidValueException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Thread player = new Thread(p1);
		bg = new Background[4];
		for(int i=0;i<bg.length;i++){
			bg[i] = new Background(p1, 1, i+1);
		}
		intf = new Foreground(null, 7777, false, 0);
		fg = new Foreground[4];
		for(int i =0;i<fg.length;i++){
			fg[i] = new Foreground(p1, 1, false,i);
		}
		m = new Monster[25];
		Thread mt[] = new Thread[25];
		for(int i =0;i<25;i++){
			int ran = (int) (Math.random() * 100 % 8 + 1);
			try {
				m[i] = new Monster(ran, p1);
			} catch (InvalidValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mt[i] = new Thread(m[i]);
			mt[i].start();
		}
		player.start();
		while(true){
			mapManagement();
		}
	}
}
