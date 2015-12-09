package render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import logic.Player;

public class Background implements IRenderable{
	private BufferedImage bg = null;
	private boolean visible;
	private Player player;
	
	public Background(Player player, int map) {
		super();
		visible = true;
		this.player = player;
		try{
			ClassLoader loader = Background.class.getClassLoader();
			bg = ImageIO.read(loader.getResource("src/background/lv"+map+"_bg.png"));
		} catch (Exception e) {
			e.printStackTrace();
			bg = null;
		}
		RenderableHolder.getInstance().add(this);
	}
	
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return Integer.MIN_VALUE;
	}

	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		int x = (player==null)?0:-player.getX()/10;
		g2.drawImage(bg, null, x, 0);
	}

}
