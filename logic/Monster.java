package logic;

import java.awt.Graphics2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import render.IRenderable;

public class Monster extends Character implements IRenderable {
	private int hp;
	private int no;
	private BufferedImage mon;

	public Monster(int status, int speed, int hp, int no) {
		super(status, speed);
		this.no = no;
		this.hp = Data.hpMon[this.no];
		String source = "src/monster/" + this.no + ".png";
		try {
			ClassLoader loader = Player.class.getClassLoader();
			mon = ImageIO.read(loader.getResource(source));
		} catch (Exception e) {
			e.printStackTrace();
			mon = null;
		}
	}

	@Override
	public void walk(boolean way) {
		// TODO Auto-generated method stub

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
		return false;
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
		g2.drawImage(mon,
				op, x - (mon.getWidth() / 2), y - (mon.getHeight() / 2));
//				break;
	}

	@Override
	public void fall() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub

	}

}
