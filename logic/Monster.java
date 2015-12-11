package logic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import render.IRenderable;
import render.RenderableHolder;

public class Monster extends Character implements IRenderable, Runnable {
	private int hp;
	private int no;
	private boolean visible;
	private boolean facing;
	private Player player;
	private int actionDelay;
	private int actionDelayCount;
	private boolean chasing;
	private Image mStand[] = new Image[2];
	private Image mWalk[] = new Image[2];
	private BufferedImage mHit;
	private BufferedImage mDie;

	public Monster(int status, int speed, int hp, int no, Player player) {
		super(status, speed);
		this.no = no;
		this.hp = Data.hpMon[this.no];
		actionDelay = 100;
		actionDelayCount = actionDelay;
		this.x = (int) (Math.random() * 1000) + 300 + 1555 * (no - 1);
		// System.out.println(x);
		this.y = 300;
		this.visible = true;
		this.player = player;
		this.facing = false;
		this.chasing = false;
		this.speedX = 0;
		this.speedY = 0;
		this.ground = 300;
		// String source = "src/monster/" + this.no + ".png";
		try {
			ClassLoader loader = Player.class.getClassLoader();
			// mStand = ImageIO.read(loader.getResource("src/monster/" + this.no
			// + ".GIF"));
			// mWalk = ImageIO.read(loader.getResource("src/monster/" + this.no
			// + ".GIF"));
			mStand[0] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_1.GIF")).getImage();
			mStand[1] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_2.GIF")).getImage();
			mWalk[0] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_3.GIF")).getImage();
			mWalk[1] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_4.GIF")).getImage();
		} catch (Exception e) {
			e.printStackTrace();
			mStand[0] = null;
			mStand[1] = null;
			mWalk[0] = null;
			mWalk[1] = null;
		}
		RenderableHolder.getInstance().add(this);
	}

	@Override
	public void walk(boolean way) {
		// TODO Auto-generated method stub
		if (facing)
			speedX = speed;
		else
			speedX = -speed;
	}

	@Override
	public void stand() {
		// TODO Auto-generated method stub
		speedX = 0;
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

		int scrollX;
		if (player == null)
			scrollX = 0;
		else {
			scrollX = Data.screenWidth / 3 - player.getX();
			int levelExtentX = Data.levelExtent;

			if (player.getX() < Data.screenWidth / 3)
				scrollX = 0;
			else if (player.getX() > levelExtentX - 2 * Data.screenWidth / 3)
				scrollX = -(levelExtentX - Data.screenWidth);
		}
		int f = (facing) ? 1 : 0;
		System.out.println(speedX);
		if(speedX != 0)
			g2.drawImage(mWalk[f], scrollX + x - (mWalk[f].getHeight(null) / 2), y - (mWalk[f].getHeight(null) / 2), null);
		else
			g2.drawImage(mStand[f], scrollX + x - (mStand[f].getHeight(null) / 2), y - (mStand[f].getHeight(null) / 2), null);

		// g2.drawImage(mStand,
		// op, x - (mStand.getWidth(null) / 2), y - (mStand.getHeight(null) /
		// 2));
		// break;
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!chasing) {
				if (actionDelayCount > 0)
					actionDelayCount--;
				else {
					actionDelayCount = actionDelay;
					int act = (int) (Math.random() * 100);
//					System.out.println(act);
					if (act >= 70) {
						stand();
						facing = !facing;
					} else if (act >= 50)
						walk(true);
					else {
						stand();
					}
				}
			}
			System.out.println(Math.abs(x - player.getX()));
			if (Math.abs(x - player.getX()) < 200) {
				if(x > player.getX()){
					speedX = -speed;
					facing = false;
				}else{
					speedX = speed;
					facing = true;
				}
				chasing = true;
			} else {
				chasing = false;
			}

			updatePosition();
		}
	}

}
