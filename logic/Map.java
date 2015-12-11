package logic;

import java.awt.Graphics2D;

import render.Background;
import render.Foreground;
import render.IRenderable;
import render.RenderableHolder;

public class Map implements Runnable{
	private int map[][];
	private Player p1;
	private int no;
	private Background bg;
	private Foreground fg,fg2,fg3;
	private Foreground intf;
	private int screenX;
	private int DelaySpawnMon;
	private int DelaySpawnCount;
	public Map(int level){
		p1 = new Player(0, 5, 0);
		Thread player = new Thread(p1);
		DelaySpawnMon = 200/level;
		DelaySpawnCount = DelaySpawnMon;
		no = 1;
		bg = new Background(p1, 1, no);
		intf = new Foreground(null, 7777, false, 0);
		fg = new Foreground(p1, 1, false,no-1);
		fg2 = new Foreground(p1, 1, false,no);
		
		Monster m1 = new Monster(0,1,1,1,p1);
		Thread mon1 = new Thread(m1);
		mon1.start();
		player.start();
		
	}

	public void mapManagement(){
		screenX = p1.getX() - (no-1)*Data.foregroundWidth;
//		System.out.println(screenX);
		if(screenX > Data.foregroundWidth){
			synchronized (RenderableHolder.getInstance()) {
				RenderableHolder.getInstance().getRenderableList().remove(bg);
				RenderableHolder.getInstance().getRenderableList().remove(fg);
				RenderableHolder.getInstance().getRenderableList().remove(fg2);
				RenderableHolder.getInstance().getRenderableList().remove(fg3);
				no++;
				bg = new Background(p1, 1, no);
				fg = new Foreground(p1, 1, false,(no-2));
				fg2 = new Foreground(p1, 1, false,(no-1));
				fg3 = new Foreground(p1, 1, false,(no));
				
			}	
		}else if(screenX < 0){
			synchronized (RenderableHolder.getInstance()) {
				RenderableHolder.getInstance().getRenderableList().remove(bg);
				RenderableHolder.getInstance().getRenderableList().remove(fg);
				RenderableHolder.getInstance().getRenderableList().remove(fg2);
				RenderableHolder.getInstance().getRenderableList().remove(fg3);
				no--;
				bg = new Background(p1, 1, no);
				fg2 = new Foreground(p1, 1, false,(no-1));
				fg3 = new Foreground(p1, 1, false,(no));
			}	
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
