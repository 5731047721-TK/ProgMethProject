package logic;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import exception.InvalidValueException;
import input.InputUtility;
import render.Background;
import render.Foreground;
import render.IRenderable;
import render.RenderableHolder;
import render.Title;

public class Map implements Runnable {
	private int map[][];
	private Player p1;
	private Monster m[];
	private Monster boss;
	private boolean clear;
	private boolean bossSpawn;
	private int no;
	private Background bg[];
	private Foreground fg[];
	private Foreground intf;
	private int screenX;
	private int DelaySpawnMon;
	private int DelaySpawnCount;
	private Thread prevThread;
	private int level;
	public Map(int level, Thread prevThread) throws InvalidValueException {
		if (level <= 0)
			throw new InvalidValueException(3);
		this.level = level;
		this.prevThread = prevThread;
		DelaySpawnMon = 200 / level;
		DelaySpawnCount = DelaySpawnMon;
		no = 1;

	}

	public void mapManagement() {
		for (int i = 0; i < bg.length - 1; i++) {
			bg[i].setFade((i + 1) * Data.foregroundWidth / 400f - (float) p1.getX() / 400f);
		}
		if (p1.getX() > Data.levelExtent - Data.screenWidth + Data.screenWidth / 3) {
			p1.lowerBoundX = Data.levelExtent - Data.screenWidth;
			for (int i = 0; i < fg.length; i++) {
				fg[i].lock = true;
				bg[i].lock = true;
			}
			p1.lock = true;
			for (int i = 0; i < m.length; i++) {
				m[i].lock = true;
				m[i].die();
			}
			if (!bossSpawn) {
				try {
					intf = new Foreground(null, 7775 + 2*level + 1, false, 0);
					boss = new Monster(10, p1);
					boss.lock = true;
					Thread bossThread = new Thread(boss);
					bossThread.start();
					bossSpawn = true;
				} catch (InvalidValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if(!boss.isVisible() && !clear){
					clear = true;
					Title stageClear = new Title(-2,null);
					Thread clearThread = new Thread(stageClear);
					clearThread.start();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					RenderableHolder.getInstance().getRenderableList().clear();
					Map m;
					try {
						if(level < 3){
							m = new Map(level+1, null);
							Thread map = new Thread(m);
							map.start();
						}
					} catch (InvalidValueException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		synchronized (InputUtility.getInstance()) {
			if (Data.pause) {
				Title pause = new Title(0, null);
				Thread pauseThread = new Thread(pause);
				pauseThread.start();
				try {
					InputUtility.getInstance().wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (RenderableHolder.getInstance()) {
					RenderableHolder.getInstance().getRenderableList().remove(pause);
				}
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
		Title t = new Title(100 + level, prevThread);
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
		for (int i = 0; i < bg.length; i++) {
			bg[i] = new Background(p1, level, i + 1);
		}
		intf = new Foreground(null, 7775 + 2*level, false, 0);
		fg = new Foreground[4];
		for (int i = 0; i < fg.length; i++) {
			fg[i] = new Foreground(p1, level, false, i);
		}
		m = new Monster[25];
		Thread mt[] = new Thread[25];
		for (int i = 0; i < 25; i++) {
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
		while (true) {
			mapManagement();
		}
	}
}
