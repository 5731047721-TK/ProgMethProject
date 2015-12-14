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
	private int offset;
	private float fade;
	public boolean lock;
	public Foreground(Player player,int map,boolean fadable,int offset) {
		super();
		visible = true;
		this.fadable = fadable;
		this.player = player;
		this.offset = offset;
		this.fade = 1f;
		try{
			ClassLoader loader = Foreground.class.getClassLoader();
			fg = ImageIO.read(loader.getResource("src/background/lv"+map+"_fg.png"));
		} catch (Exception e) {
			e.printStackTrace();
			fg = null;
		}
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().add(this);
		}
		
	}

	
	
	public float getFade() {
		return fade;
	}



	public void setFade(float fade) {
		if (fade > 1)
			this.fade = 1;
		else if (fade < 0)
			this.fade = 0;
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
		if(player == null) return 7777;
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
		    scrollX = -(levelExtentX - Data.screenWidth);
		}
		if(lock)
			scrollX = -(Data.levelExtent - Data.screenWidth);
		g2.drawImage(fg, null, scrollX + Data.foregroundWidth*offset, 0);
		
	}

}
