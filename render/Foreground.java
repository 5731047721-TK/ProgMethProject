package render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import logic.Player;

public class Foreground implements IRenderable{
	private BufferedImage fg = null;
	private boolean visible;
	private Player player;
	
	public Foreground(Player player) {
		super();
		visible = true;
		this.player = player;
		try{
			ClassLoader loader = Player.class.getClassLoader();
			fg = ImageIO.read(loader.getResource("src/background/lv1_fg.png"));
		} catch (Exception e) {
			e.printStackTrace();
			fg = null;
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
		return -7777;
	}

	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawImage(fg, null, -player.getX()*2/4, 0);
	}

}
