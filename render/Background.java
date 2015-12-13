package render;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import logic.Data;
import logic.Player;

public class Background implements IRenderable {
	private BufferedImage bg = null;
	private boolean visible;
	private Player player;
	private float fade;
	private int no;
	private int map;
	public boolean lock;
	public Background(Player player, int map, int no) {
		super();
		this.no = no - 1;
		this.map = map;
		if(map == 0)
			setFade(0f);
		else
			setFade(1f);
		visible = true;
		this.player = player;
		try {
			ClassLoader loader = Background.class.getClassLoader();
			String num = (no == 0) ? "" : "" + no;
			bg = ImageIO.read(loader.getResource("src/background/lv" + map + "_bg" + num + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
			bg = null;
		}
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().add(this);
		}
	}

	public float getFade() {
		return fade;
	}

	public void setFade(float fade) {
		if (fade >= 1)
			this.fade = 1f;
		else if (fade <= 0)
			this.fade = 0f;
		else
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
		if(map == 0) return Integer.MAX_VALUE; 
		return -1000000 - no;
	}

	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		int scrollX;
		if (player == null)
			scrollX = 0;
		else {
			scrollX = (Data.screenWidth / 3 - player.getX()) / 8;
			int levelExtentX = Data.levelExtent;

			if (player.getX() < Data.screenWidth / 3)
				scrollX = 0;
			else if (player.getX() > levelExtentX - 2 * Data.screenWidth / 3)
				scrollX = -(levelExtentX - Data.screenWidth) / 8;
		}
		if(lock)
			scrollX = -(Data.levelExtent - Data.screenWidth)/8;
		// System.out.println(no);
		if (no < 0)
			g2.drawImage(bg, null, scrollX, 0);
		else
			g2.drawImage(bg, null, scrollX + (Data.foregroundWidth * no) / 10, 0);
	}

}
