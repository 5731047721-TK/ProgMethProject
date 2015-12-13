package logic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import exception.InvalidValueException;
import input.InputUtility;
import render.IRenderable;
import render.RenderableHolder;

public class Monster extends Character implements IRenderable, Runnable {
	private int hp;
	private int no;
	
	public boolean lock;
	private boolean visible;
	private boolean facing;
	private boolean hitting;
	// private boolean hittingAnimate;
	private boolean hurting;
	private boolean died;
	private Player player;
	private int actionDelay;
	private int actionDelayCount;
	private int hitDelayCount;
	private boolean chasing;
	private Image mStand[] = new Image[2];
	private Image mWalk[] = new Image[2];
	private Image mHurt[] = new Image[2];
	private Image mHit[] = new Image[2];
	private Image mDie;

	public Monster(int no, Player player) throws InvalidValueException {
		super();
		if (no <= 0 || no > Data.totalMon)
			throw new InvalidValueException(0);
		if (speed < 0) {
			throw new InvalidValueException(1);
		} else {
			this.speed = Data.speedMon[no - 1];
		}
		this.hitDelayCount = Data.hitDelayMon[no - 1];
		this.no = no;
		this.hp = Data.hpMon[this.no - 1];
		actionDelay = 100;
		actionDelayCount = actionDelay;
		this.x = (int) (Math.random() * 1000) + 1555 * (no - 1);
		this.x%= Data.levelExtent;
		this.x+= 300;
		// System.out.println(x);
		this.y = 300 + Data.offsetMon[no - 1];
		this.visible = true;
		this.player = player;
		this.facing = false;
		this.chasing = false;
		this.speedX = 0;
		this.speedY = 0;
		this.ground = 310;
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
			mHit[0] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_5.GIF")).getImage();
			mHit[1] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_6.GIF")).getImage();
			mHurt[0] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_7.GIF")).getImage();
			mHurt[1] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_8.GIF")).getImage();
			mDie = new ImageIcon(loader.getResource("src/monster/" + this.no + "_9.GIF")).getImage();
		} catch (Exception e) {
			e.printStackTrace();
			mStand[0] = null;
			mStand[1] = null;
			mWalk[0] = null;
			mWalk[1] = null;
			mHit[0] = null;
			mHit[1] = null;
			mHurt[0] = null;
			mHurt[1] = null;
			mDie = null;
		}
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().add(this);
		}
	}

	public int getNo() {
		return no;
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
		if (!hurting) {
			speedX = 0;
			hitting = true;
			if (hitDelayCount > 0) {
				hitDelayCount--;
				return;
			}
			hitDelayCount = Data.hitDelayMon[no - 1];
			// hitting = true;
			player.hurt(facing);
		}
	}

	@Override
	public void hurt(boolean facing) {
		// TODO Auto-generated method stub

		if (!hurting) {
			// System.out.println("hurt " + hp);
			if (facing)
				speedX = -1;
			else
				speedX = 1;
			hurting = true;
			hp--;
			if (hp <= 0) {
				try {
					Thread.sleep(750);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				die();
			}
		}
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		died = true;
		speedX = 0;
		hitting = false;
		hurting = false;
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
		if(lock)
			scrollX = -(Data.levelExtent - Data.screenWidth);
		int f = (facing) ? 1 : 0;
		// System.out.println(speedX);
		if (died)
			g2.drawImage(mDie, scrollX + x - (mDie.getHeight(null) / 2), y - (mDie.getHeight(null) / 2), null);
		else if (hurting)
			g2.drawImage(mHurt[f], scrollX + x - (mHurt[f].getHeight(null) / 2), y - (mHurt[f].getHeight(null) / 2),
					null);
		else if (hitting)
			g2.drawImage(mHit[f], scrollX + x - (mHit[f].getHeight(null) / 2), y - (mHit[f].getHeight(null) / 2), null);
		else if (speedX != 0)
			g2.drawImage(mWalk[f], scrollX + x - (mWalk[f].getHeight(null) / 2), y - (mWalk[f].getHeight(null) / 2),
					null);
		else
			g2.drawImage(mStand[f], scrollX + x - (mStand[f].getHeight(null) / 2), y - (mStand[f].getHeight(null) / 2),
					null);

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

			if (!chasing && !hitting && !hurting) {
				if (actionDelayCount > 0)
					actionDelayCount--;
				else {
					actionDelayCount = actionDelay;
					int act = (int) (Math.random() * 100);
					// System.out.println(act);
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
			// check range of player
			/*
			 * if(Math.abs(x - player.getX()) < Data.sizeMon[no-1]/2){ if(x <
			 * player.getX()){ System.out.println("Right"); player.lowerBoundX =
			 * x+Data.sizeMon[no-1]/2; }else{ System.out.println("Left");
			 * player.upperBoundX = x-Data.sizeMon[no-1]/2; } }else{
			 * player.upperBoundX = Data.levelExtent; player.lowerBoundX = 0; }
			 */
			if (Math.abs(x - player.getX()) <= Data.sizeMon[no-1]/2 && Math.abs(y - player.getY()) <= Data.sizeMon[no-1]/2) {
				hit();
			} else if (!hurting && Math.abs(x - player.getX()) < 200) {
				if (hitting) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					hitting = false;

				}
				hitDelayCount = Data.hitDelayMon[no - 1];
				if (x > player.getX()) {
					speedX = -speed;
					facing = false;
				} else {
					speedX = speed;
					facing = true;
				}
				chasing = true;
			} else {
				// hittingAnimate = false;
				hitDelayCount = Data.hitDelayMon[no - 1];
				chasing = false;
			}

			if (player.isDamaging()) {
//				System.out.println((Math.abs(x - player.getX() - 40)) + " " + Data.sizeMon[no-1]/2);
				if (player.isFacing()
						&& (Math.abs(x - player.getX() - 80))  <= Data.sizeMon[no-1]/2
						&& Math.abs(player.getY() - y) < Data.sizeMon[no - 1]/2)
					hurt(true);
				else if (!player.isFacing()
						&& (Math.abs(x - player.getX() + 80))  <= Data.sizeMon[no-1]/2
						&& Math.abs(y - player.getY()) < Data.sizeMon[no - 1]/2)
					hurt(true);
			} else {
				hurting = false;
			}
			if (died) {
				break;
			}
			updatePosition();
			synchronized (InputUtility.getInstance()) {
				if(Data.pause)
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		while (fade > 0.01) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fade -= 0.01;
		}
		this.visible = false;
		synchronized (RenderableHolder.getInstance()) {
			RenderableHolder.getInstance().getRenderableList().remove(this);
		}
	}

}
