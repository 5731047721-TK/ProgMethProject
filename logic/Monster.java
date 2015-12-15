package logic;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
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
	private int currentFrame;
	private int frameCount;
	private int frameDelay;
	private int frameDelayCount;
	private int frameWidth;
	private boolean playing;
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
	private AudioClip aSound;
	private AudioClip dSound;
	private AudioClip hSound;
	
	private BufferedImage bHit = null;

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
		if (no < 10)
			this.x = (int) (Math.random() * 400) + 700 * (no);
		else if (no < 20)
			this.x = (int) (Math.random() * 500) + 800 * (no % 10);
		else if (no == 29)
			this.x = Data.levelExtent - 2 * Data.foregroundWidth;
		this.x %= Data.levelExtent - Data.foregroundWidth;
		this.x += 800;
		if (no % 10 == 0) {
			this.frameCount = Data.bossFrame[no / 10 - 1];
			this.frameDelay = Data.bossFrameDelay[no / 10 - 1];
			this.x = Data.levelExtent - 300;
		}
		this.y = 300 + Data.offsetMon[no - 1];
		this.visible = true;
		this.player = player;
		this.facing = false;
		this.chasing = false;
		this.speedX = 0;
		this.speedY = 0;
		this.ground = 310;
		try {
			ClassLoader loader = Monster.class.getClassLoader();
			mStand[0] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_1.GIF")).getImage();
			mDie = new ImageIcon(loader.getResource("src/monster/" + this.no + "_9.GIF")).getImage();
			String num = (no<10)?"0"+no:""+no;
			dSound = Applet.newAudioClip(loader.getResource("src/sfx/Sound/"+num+"_d.wav").toURI().toURL());
			if (no != 29) {
				mStand[1] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_2.GIF")).getImage();
				mWalk[0] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_3.GIF")).getImage();
				mWalk[1] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_4.GIF")).getImage();
				mHit[0] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_5.GIF")).getImage();
				mHit[1] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_6.GIF")).getImage();
				mHurt[0] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_7.GIF")).getImage();
				mHurt[1] = new ImageIcon(loader.getResource("src/monster/" + this.no + "_8.GIF")).getImage();
				aSound = Applet.newAudioClip(loader.getResource("src/sfx/Sound/"+num+"_a.wav").toURI().toURL());
				hSound = Applet.newAudioClip(loader.getResource("src/sfx/Sound/"+num+"_h.wav").toURI().toURL());
			}
			if (no % 10 == 0) {
				bHit = ImageIO.read(loader.getResource("src/monster/" + no + "_hit.png"));
				frameWidth = bHit.getWidth() / Data.bossFrame[no / 10 - 1];
			}
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

	public boolean isDied() {
		return died;
	}
	
	@Override
	public void walk(boolean way) {
		if (facing)
			speedX = speed;
		else
			speedX = -speed;
	}

	@Override
	public void stand() {
		speedX = 0;
	}

	@Override
	public void hit() {
		if (!hurting) {
			speedX = 0;
			if (!hitting) {
				currentFrame = 0;
			}
			hitting = true;
			if (no % 10 != 0 && no != 29) {
				if (hitDelayCount > 0) {
					if(hitDelayCount < 3)
						aSound.play();
					hitDelayCount--;
					return;
				}
				hitDelayCount = Data.hitDelayMon[no - 1];
				player.hurt(facing);
			}else{
				aSound.play();
			}
		}
	}

	@Override
	public void hurt(boolean facing) {
		if (!hurting || player.isUseFury()) {
			if (facing)
				speedX = 1;
			else
				speedX = -1;
			hurting = true;
			if(no!=29)
				hSound.play();
			if(player.isUseFury()){
				try {
					Thread.sleep(200);
					if(no%10==0){
						Thread.sleep(300);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			hp--;
			if (hp <= 0) {
				try {
					Thread.sleep(750);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				die();
			}
		}
	}

	@Override
	public void die() {
		died = true;
		speedX = 0;
		hitting = false;
		hurting = false;
		if(!lock)
			dSound.play();
		if (!lock && !player.isUseFury())
			player.increaseFury(25);
	}

	@Override
	public void jump(){
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public int getZ() {
		return 9999;
	}

	public AffineTransformOp getOp() {
		AffineTransform tran = AffineTransform.getTranslateInstance(frameWidth, 0);
		AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
		tran.concatenate(flip);
		AffineTransformOp op = new AffineTransformOp(tran, AffineTransformOp.TYPE_BILINEAR);
		return op;
	}

	@Override
	public void render(Graphics2D g2) {
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
		if (lock)
			scrollX = -(Data.levelExtent - Data.screenWidth);
		int f = (facing) ? 1 : 0;
		if (no == 29) {
			if (died)
				g2.drawImage(mDie, scrollX + x - (mDie.getHeight(null) / 2), y - (mDie.getHeight(null) / 2), null);
			else
				g2.drawImage(mStand[0], scrollX + x - (mStand[0].getHeight(null) / 2),
						y - (mStand[0].getHeight(null) / 2), null);
			return;
		} else {
			if (died)
				g2.drawImage(mDie, scrollX + x - (mDie.getHeight(null) / 2), y - (mDie.getHeight(null) / 2), null);
			else if (hurting)
				g2.drawImage(mHurt[f], scrollX + x - (mHurt[f].getHeight(null) / 2), y - (mHurt[f].getHeight(null) / 2),
						null);
			else if (hitting && no % 10 != 0)
				g2.drawImage(mHit[f], scrollX + x - (mHit[f].getHeight(null) / 2), y - (mHit[f].getHeight(null) / 2),
						null);
			else if (hitting && no % 10 == 0)
				g2.drawImage(
						bHit.getSubimage(currentFrame * bHit.getWidth() / Data.bossFrame[no / 10 - 1], 0,
								bHit.getWidth() / Data.bossFrame[no / 10 - 1], bHit.getHeight()),
						(facing) ? getOp() : null, scrollX + x - frameWidth / 2, y - (bHit.getHeight() / 2));
			else if (speedX != 0)
				g2.drawImage(mWalk[f], scrollX + x - (mWalk[f].getHeight(null) / 2), y - (mWalk[f].getHeight(null) / 2),
						null);
			else
				g2.drawImage(mStand[f], scrollX + x - (mStand[f].getHeight(null) / 2),
						y - (mStand[f].getHeight(null) / 2), null);
		}
		if (no % 10 == 0) {
			// boss hp
			g2.setColor(new Color(210,0,0));
			g2.fillRect(Data.boss[0] + Data.boss[2] - hp*Data.boss[2]/Data.hpMon[no-1], Data.boss[1], hp*Data.boss[2]/Data.hpMon[no-1], Data.boss[3]);
		}

	}

	@Override
	public void fall() {
	}

	@Override
	public void updatePosition() {
		this.setX(x + speedX);
		this.setY(y - speedY);

	}

	public void play() {
		currentFrame = 0;
		playing = true;
		visible = true;
	}

	public void stop() {
		if (hitting)
			currentFrame = frameCount - 1;
		else
			currentFrame = 0;
		playing = true;
		visible = true;
	}

	public void updateAnimation() {
		if (!playing)
			return;
		if (frameDelayCount > 0) {
			frameDelayCount--;
			return;
		}
		if ((no == 10) && currentFrame >= 5 && currentFrame <= 8 && hitting
				&& (Math.abs(x - player.getX() + ((facing)?80:-80))) <= Data.sizeMon[no - 1] / 3) {
			player.hurt(facing);	
		}else if(no == 20 && currentFrame >= 4 && currentFrame <= 7 && hitting && (Math.abs(x - player.getX() + ((facing)?80:-80))) <= Data.sizeMon[no - 1] / 3) {
			player.hurt(facing);
		}else if (no == 30 && currentFrame >= 12 
				&& hitting && (Math.abs(x - player.getX() + ((facing)?80:-80))) <= Data.sizeMon[no - 1] / 3) {
			player.hurt(facing);
		}
		frameDelayCount = frameDelay;
		currentFrame++;
		if (currentFrame == frameCount) {
			stop();
			if (hitting) {
				hitting = false;
			}
		}
	}

	@Override
	public void run() {
		if (no % 10 == 0)
			play();
		while (true) {
			try {
				if(no!=29)
					Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!chasing && !hitting && !hurting && no != 29) {
				if (actionDelayCount > 0)
					actionDelayCount--;
				else {
					actionDelayCount = actionDelay;
					int act = (int) (Math.random() * 100);
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
			if (Math.abs(x - player.getX()) <= Data.sizeMon[no - 1] / 2
					&& Math.abs(y - player.getY()) <= Data.sizeMon[no - 1] / 2 && no != 29) {
				if(x < player.getX())
					facing = true;
				else
					facing = false;
				hit();
				actionDelayCount = actionDelay;
			} else if (!hurting && Math.abs(x - player.getX()) < Data.chasingRangeMon[no - 1]) {
				if (hitting) {
						if (actionDelayCount > 0)
							actionDelayCount--;
						else {
							actionDelayCount = actionDelay;
							hitting = false;
						}
				}
				if (!hitting) {
					hitDelayCount = Data.hitDelayMon[no - 1];
					if (x > player.getX()) {
						speedX = -speed;
						facing = false;
					} else {
						speedX = speed;
						facing = true;
					}
					chasing = true;
				}
			} else {
				hitting = false;
				hitDelayCount = Data.hitDelayMon[no - 1];
				chasing = false;
			}
			if(player.isUseFury()){
				if ((Math.abs(x - player.getX())) <= Data.sizeMon[no - 1] / 2 && Math.abs(player.getY() - y) < Data.sizeMon[no - 1] / 2)
					hurt(player.isFacing());
			}
			if (player.isDamaging()) {
				if (player.isFacing() && (Math.abs(x - player.getX() - 80)) <= Data.sizeMon[no - 1] / 2
						&& Math.abs(player.getY() - y) < Data.sizeMon[no - 1] / 2)
					hurt(true);
				else if (!player.isFacing() && (Math.abs(x - player.getX() + 80)) <= Data.sizeMon[no - 1] / 2
						&& Math.abs(y - player.getY()) < Data.sizeMon[no - 1] / 2)
					hurt(true);
			} else {
				hurting = false;
			}
			if (died) {
				break;
			}
			updatePosition();
			updateAnimation();
			synchronized (InputUtility.getInstance()) {
				if (Data.pause)
					try {
						InputUtility.getInstance().wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
		while (fade > 0.01) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
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
