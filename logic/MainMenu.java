package logic;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import exception.InvalidValueException;
import input.InputUtility;
import render.Background;
import render.Foreground;
import render.GameScreen;
import render.RenderableHolder;
import render.Title;

public class MainMenu implements Runnable {
	private Background bg;
	private Foreground eye;
	private Foreground select;
	private Thread prevThread;
	private int option;
	private boolean start;
	public static boolean cont;
	private boolean update;
	private AudioClip mainMusic;
	private AudioClip storyMusic;
	private AudioClip creditMusic;
	private AudioClip titleSelect;
	private AudioClip titleSpace;
	private AudioClip titleUpDown;
	
	public MainMenu(Thread prevThread) {
		this.prevThread = prevThread;
		start = false;
		update = false;
		option = 0;
	}

	public void space() {// press space bar
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().getRenderableList().remove(bg);
		}
		bg = new Background(null, 9999, 0);
		select = new Foreground(null, 9999 + option, false, 0);
	}

	public void start() {
		// TODO Auto-generated method stub
		synchronized (RenderableHolder.getInstance()) {
			// level 1
			RenderableHolder.getInstance().getRenderableList().remove(bg);
			RenderableHolder.getInstance().getRenderableList().remove(select);
			select = null;
			RenderableHolder.getInstance().getRenderableList().remove(eye);
		}
		Title title[] = new Title[7];
		Thread titleT[] = new Thread[7];
		for (int i = 0; i < title.length; i++) {
			if (i == 0)
				title[i] = new Title(i + 3, null);
			else
				title[i] = new Title(i + 3, titleT[i - 1]);
			titleT[i] = new Thread(title[i]);
			titleT[i].start();
		}
		Map m;
		try {
			m = new Map(1,titleT[title.length-1]);
			Thread map = new Thread(m);
			map.start();
		} catch (InvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start = true;
	}

	public void cont() {
		synchronized (RenderableHolder.getInstance()) {
			// level 1
			RenderableHolder.getInstance().getRenderableList().remove(bg);
			RenderableHolder.getInstance().getRenderableList().remove(select);
			select = null;
			RenderableHolder.getInstance().getRenderableList().remove(eye);
		}
		Map m;
		try {
			m = new Map(Data.level, null);
			Thread map = new Thread(m);
			map.start();
		} catch (InvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start = true;
	}
	
	public void credit(){
		synchronized (RenderableHolder.getInstance()) {
			// level 1
			RenderableHolder.getInstance().getRenderableList().remove(bg);
			RenderableHolder.getInstance().getRenderableList().remove(select);
			select = null;
			RenderableHolder.getInstance().getRenderableList().remove(eye);
		}
		if(storyMusic != null)
			storyMusic.stop();
		while(creditMusic==null){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		creditMusic.loop();
		Title credit[] = new Title[3];
		Thread creditT[] = new Thread[3];
		for(int i = 0;i<3;i++){
			if(i==0)
				credit[i] = new Title(i+10, null);
			else
				credit[i] = new Title(i+10, creditT[i-1]);
			creditT[i] = new Thread(credit[i]);
			creditT[i].start();
		}
		start = true;
//		cont = true;
//		creditMusic.stop();
		new Thread(new MainMenu(creditT[2])).start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ClassLoader loader = MainMenu.class.getClassLoader();
			mainMusic = Applet.newAudioClip(loader.getResource("src/sfx/Music/gametitle.wav").toURI().toURL());
			storyMusic = Applet.newAudioClip(loader.getResource("src/sfx/Music/story.wav").toURI().toURL());
			creditMusic = Applet.newAudioClip(loader.getResource("src/sfx/Music/credit.wav").toURI().toURL());
			titleSelect =Applet.newAudioClip(loader.getResource("src/sfx/Sound/title_select.wav").toURI().toURL());
			titleSpace =Applet.newAudioClip(loader.getResource("src/sfx/Sound/title_space.wav").toURI().toURL());
			titleUpDown =Applet.newAudioClip(loader.getResource("src/sfx/Sound/title_updown.wav").toURI().toURL());
			 
		} catch (MalformedURLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mainMusic = null;
			storyMusic = null;
			titleSelect = null;
			titleSpace = null;
			titleUpDown = null;
		}
		if (prevThread != null) {
			try {
				prevThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bg = new Background(null, 9998, 0);
		eye = new Foreground(null, 9998, true, 0);
		InputUtility instance = InputUtility.getInstance();
		mainMusic.loop();
		creditMusic.stop();
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (instance) {
				if (instance.getKeytriggered(KeyEvent.VK_SPACE)) {
					instance.setKeytriggered(KeyEvent.VK_SPACE, false);
					titleSpace.play();
					space();
					break;
				}
				instance.postUpdate();
			}
			GameScreen.getGamescreen().repaint();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update = false;

			synchronized (instance) {
				if (instance.getKeytriggered(KeyEvent.VK_DOWN)) {
					update = true;
					titleUpDown.play();
					if (option < 3)
						option++;
					else
						option = 0;
					instance.setKeytriggered(KeyEvent.VK_DOWN, false);
				} else if (instance.getKeytriggered(KeyEvent.VK_UP)) {
					update = true;
					titleUpDown.play();
					if (option > 0)
						option--;
					else
						option = 3;
					instance.setKeytriggered(KeyEvent.VK_UP, false);
				} else if (instance.getKeytriggered(KeyEvent.VK_SPACE)) {
					// update = true;
					instance.setKeytriggered(KeyEvent.VK_SPACE, false);
					// RenderableHolder.getInstance().getRenderableList().remove(select);
					titleSelect.play();
					switch (option) {
					case 0:
						start();
						break;
					case 1:
						cont();
						break;
					case 2:
						credit();
						break;
					case 3:
						System.exit(0);
					}
				}
				instance.postUpdate();
			}
			synchronized (RenderableHolder.getInstance()) {
				if (update) {
					RenderableHolder.getInstance().getRenderableList().remove(select);
					select = new Foreground(null, 9999 + option, false, 0);
				}
			}
			GameScreen.getGamescreen().repaint();
			if (start)
				break;
		}
		mainMusic.stop();
		mainMusic = null;
		storyMusic.loop();
		while(!cont){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		creditMusic.stop();
		storyMusic.stop();
		storyMusic = null;
		cont = false;
	}

}
