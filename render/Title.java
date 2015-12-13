package render;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import input.InputUtility;
import logic.Player;

public class Title implements IRenderable, Runnable {

	private BufferedImage title = null;
	private boolean visible;
	private float fade;
	private Thread prevThread;
	private int no;

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

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawImage(title, null, 0, 0);
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
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().add(this);
		}
		while (fade < 0.99) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setFade(fade + 0.01f);
			GameScreen.getGamescreen().repaint();
		}
		int i =0;
		while (true) {
			// System.out.println("Hello" + no);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
				if(i >= 500)
					break;
			}
		}
		while (fade > 0.01) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fade -= 0.01;
			GameScreen.getGamescreen().repaint();
		}
		this.visible = false;
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().getRenderableList().remove(this);
		}
	}
}
