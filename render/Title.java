package render;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import input.InputUtility;
import logic.MainMenu;
import logic.Monster;
import logic.Player;

public class Title implements IRenderable, Runnable {

	private BufferedImage title = null;
	private boolean visible;
	private float fade;
	private Thread prevThread;
	private int no;
	private boolean destroyed;

	public Title(int no, Thread prevThread) {
		super();
		this.no = no;
		visible = true;
		this.fade = 0f;
		this.prevThread = prevThread;
		try {
			ClassLoader loader = Player.class.getClassLoader();
			title = ImageIO.read(loader.getResource("src/background/title" + no + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
			title = null;
		}

	}

	public float getFade() {
		return fade;
	}

	public void setFade(float fade) {
		if (fade > 1)
			this.fade = 1;
		else if (fade < 0) {
			this.fade = 0;
		} else
			this.fade = fade;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void render(Graphics2D g2) {
		g2.drawImage(title, null, 0, 0);
	}

	@Override
	public void run() {
		if (prevThread != null) {
			try {
				prevThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().add(this);
		}
		while (fade < 0.99) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setFade(fade + 0.01f);
			GameScreen.getGamescreen().repaint();
		}
		int i =0;
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GameScreen.getGamescreen().repaint();
			if(no < 100)
				synchronized (InputUtility.getInstance()) {
					if (InputUtility.getInstance().getKeytriggered(KeyEvent.VK_SPACE)) {
						InputUtility.getInstance().postUpdate();
						break;
					}
				}
			else{
				i++;
				if(i >= 200)
					break;
			}
			if(destroyed){
				break;
			}
		}
		while (fade > 0.01) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			fade -= 0.01;
			GameScreen.getGamescreen().repaint();
			if(destroyed){
				break;
			}
		}
		this.visible = false;
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().getRenderableList().remove(this);
		}
		if(no == 12)
			MainMenu.cont = true;
		if(no == -1){
			synchronized(RenderableHolder.getInstance()){
				for (IRenderable entity : RenderableHolder.getInstance().getRenderableList()) {
					if(entity instanceof Player)
						((Player)entity).setDestroyed(true);
					else if(entity instanceof Monster)
						((Monster)entity).setDestroyed(true);
					if(entity instanceof Title)
						((Title)entity).setDestroyed(true);
				}
				RenderableHolder.getInstance().getRenderableList().clear();
			}
			if(Player.gameOver!=null)
				Player.gameOver.stop();
			MainMenu mainMenu = new MainMenu(null);
			new Thread(mainMenu).start();
		}
	}
}
