package logic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import render.IRenderable;
import render.RenderableHolder;

public class Monster extends Character implements IRenderable {
	private int hp;
	private int no;
	private boolean visible;
	private Image mStand;
	private BufferedImage mWalk;
	private BufferedImage mHit;
	private BufferedImage mDie;
	

	public Monster(int status, int speed, int hp, int no) {
		super(status, speed);
		this.no = no;
		this.hp = Data.hpMon[this.no];
		this.x = 600;
		this.y = 300;
		this.visible = true;
//		String source = "src/monster/" + this.no + ".png";
		try {
			ClassLoader loader = Player.class.getClassLoader();
//			mStand = ImageIO.read(loader.getResource("src/monster/" + this.no + ".GIF"));
//			mWalk = ImageIO.read(loader.getResource("src/monster/" + this.no + ".GIF"));
			mStand = new ImageIcon(loader.getResource("src/monster/" + this.no + ".GIF")).getImage();
		} catch (Exception e) {
			e.printStackTrace();
			mStand = null;
		}
		RenderableHolder.getInstance().add(this);
	}

	@Override
	public void walk(boolean way) {
		// TODO Auto-generated method stub
		speedX = speed;
	}

	@Override
	public void stand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hurt() {
		// TODO Auto-generated method stub
		hp--;
		if (hp <= 0)
			die();
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		AffineTransformOp op = null;
		g2.drawImage(mStand, x - (mStand.getWidth(null) / 2), y - (mStand.getHeight(null) / 2), null);
//		g2.drawImage(mStand,
//				op, x - (mStand.getWidth(null) / 2), y - (mStand.getHeight(null) / 2));
//				break;
	}

	@Override
	public void fall() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		this.setX(x + speedX);
		this.setY(y - speedY);
	}

}
