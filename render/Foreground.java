package render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import logic.Data;
import logic.Player;

public class Foreground implements IRenderable{
	private BufferedImage fg = null;
	private boolean visible;
	private Player player;
	public boolean fadable;
	public Foreground(Player player,int map,boolean fadable) {
		super();
		visible = true;
		this.fadable = fadable;
		this.player = player;
		try{
			ClassLoader loader = Player.class.getClassLoader();
			fg = ImageIO.read(loader.getResource("src/background/lv"+map+"_fg.png"));
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
		int scrollX;
		if(player==null)
			scrollX = 0;
		else{
		scrollX = Data.screenWidth/3 - player.getX();
		int levelExtentX = Data.levelExtent;
		 
		if (player.getX() < Data.screenWidth/3)
		    scrollX = 0;
		else if (player.getX() > levelExtentX - 2*Data.screenWidth/3)
		    scrollX = -(levelExtentX - Data.screenWidth/3);
		}
		g2.drawImage(fg, null, scrollX, 0);
		if(player!=null)
			System.out.println(player.getX() + " x:scX "+scrollX);
	}

}
