package logic;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import exception.InvalidValueException;
import input.InputUtility;
import render.Background;
import render.Foreground;
import render.IRenderable;
import render.RenderableHolder;
import render.Title;

public class Map implements Runnable {
	private Player p1;
	private Monster m[];
	private Monster boss;
	private boolean clear;
	private boolean bossSpawn;
	private boolean destroyed;
	private boolean eggAlive;
	private AudioClip stageMusic;
	private AudioClip bossSound;
	private AudioClip pauseSound;
	private AudioClip stageClear;
	private int no;
	private Background bg[];
	private Foreground fg[];
	private Foreground intf;
	private int screenX;
	private Thread prevThread;
	private int level;
	public Map(int level, Thread prevThread) throws InvalidValueException {
		if (level <= 0)
			throw new InvalidValueException(3);
		this.level = level;
		this.prevThread = prevThread;
		no = 1;
		try {
			ClassLoader loader = Map.class.getClassLoader();
			stageMusic = Applet.newAudioClip(loader.getResource("src/sfx/Music/stage" + level + ".wav").toURI().toURL());
			stageClear = Applet.newAudioClip(loader.getResource("src/sfx/Music/clear.wav").toURI().toURL());
			bossSound = Applet.newAudioClip(loader.getResource("src/sfx/Sound/boss_"+level+".wav").toURI().toURL());
			pauseSound = Applet.newAudioClip(loader.getResource("src/sfx/Sound/title_select.wav").toURI().toURL());
		} catch (MalformedURLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			stageMusic = null;
			stageClear = null;
			bossSound = null;
			pauseSound = null;
		}
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}



	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}



	public void mapManagement() {
		for (int i = 0; i < bg.length - 1; i++) {
			bg[i].setFade((i + 1) * Data.foregroundWidth / 400f - (float) p1.getX() / 400f);
		}
		if(eggAlive && !m[0].isVisible()){
			eggAlive = false;
			p1.setUpperBoundX(Data.levelExtent);
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
					boss = new Monster(level*10, p1);
					boss.lock = true;
					Thread bossThread = new Thread(boss);
					bossThread.start();
					bossSound.play();
					bossSpawn = true;
				} catch (InvalidValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if(!boss.isVisible() && !clear){
					stageClear.play();
					clear = true;
					Title sClear = new Title(-2,null);
					new Thread(sClear).start();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					synchronized(RenderableHolder.getInstance()){
						for (IRenderable entity : RenderableHolder.getInstance().getRenderableList()) {
							if(entity instanceof Player)
								((Player)entity).setDestroyed(true);
							else if(entity instanceof Monster)
								((Monster)entity).die();
							if(entity instanceof Title)
								((Title)entity).setDestroyed(true);
						}
						RenderableHolder.getInstance().getRenderableList().clear();
					}
					Map m;
					try {
						if(level < 3){
							m = new Map(level+1, null);
							new Thread(m).start();
							destroyed = true;
						}else{
							destroyed = true;
							Title end[] = new Title[4];
							Thread endT[] = new Thread[4];
							for(int i = 0;i<4;i++){
								if(i==0)
									end[i] = new Title(i+13, null);
								else
									end[i] = new Title(i+13, endT[i-1]);
								endT[i] = new Thread(end[i]);
								endT[i].start();
							}
							MainMenu mainMenu = new MainMenu(endT[3]);
							new Thread(mainMenu).start();
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
				pauseSound.play();
				try {
					InputUtility.getInstance().wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pauseSound.play();
				pause.setDestroyed(true);
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
		MainMenu.cont = true;
		Title t = new Title(100 + level, prevThread);
		new Thread(t).start();
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
		if(level<3){
			m = new Monster[25];
			Thread mt[] = new Thread[25];
			for (int i = 0; i < 25; i++) {
				int ran = (int) (Math.random() * 100 % (8-level) + 1);
				if(level==2){
					Data.level = 2;
					ran+=10;
				}
				try {
					m[i] = new Monster(ran, p1);
				} catch (InvalidValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mt[i] = new Thread(m[i]);
				mt[i].start();
			}
		}else if(level == 3){
			Data.level = 3;
			m = new Monster[1];
			try {
				m[0] = new Monster(29, p1);
			} catch (InvalidValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Thread(m[0]).start();
			eggAlive = true;
			p1.setUpperBoundX(Data.levelExtent - 2 * Data.foregroundWidth + 800);
		}
		player.start();
		stageMusic.loop();
		while (true) {
			mapManagement();
			if(destroyed)
				break;
		}
		stageMusic.stop();
	}
}
