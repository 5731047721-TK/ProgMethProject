package logic;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import input.InputUtility;
import render.GameScreen;
import render.IRenderable;
import render.RenderableHolder;

public class Player extends Character implements IRenderable, Runnable {
	private BufferedImage pStand = null;
	private BufferedImage pWalk = null;
	private BufferedImage pJump = null;
	private BufferedImage pHit1 = null;
	private BufferedImage pHit2 = null;
	private BufferedImage pDie = null;

	private int hp;
	// private int gender;
	private boolean visible;
	private boolean playing;
	private boolean hitting;
	private boolean facing;

	public Player(int status, int speed, int gender) {
		super(status, speed);
		this.hp = Data.MAX_HP;
		// this.gender = gender;
		this.playing = true;
		this.visible = true;
		this.jumpSpeed = 15;
		this.jumpTime = 0;
		this.onGround = true;
		this.facing = true;
		RenderableHolder.getInstance().add(this);
		start(0);
		this.frameCount = 8;
		this.frameDelay = 2;
		this.currentFrame = 0;
		this.frameDelayCount = 0;
		try {
			ClassLoader loader = Player.class.getClassLoader();
			pStand = ImageIO.read(loader.getResource("src/player/char2_1.png"));
			pWalk = ImageIO.read(loader.getResource("src/player/char2_2.png"));
			pJump = ImageIO.read(loader.getResource("src/player/char2_3.png"));
			pHit1 = ImageIO.read(loader.getResource("src/player/char2_4.png"));
			pHit2 = ImageIO.read(loader.getResource("src/player/char2_5.png"));
			pDie = ImageIO.read(loader.getResource("src/player/char2_6.png"));
			frameWidth = pWalk.getWidth() / 8;
			frameHeight = pWalk.getHeight();
//			System.out.println("The image are loaded");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("The image can't be loaded");
			pStand = null;
			pWalk = null;
			pJump = null;
			pHit1 = null;
			pHit2 = null;
			pDie = null;
		}
		// debug

	}

	public void start(int level) {
		switch (level) {
		case 1: // menu
			this.x = 0;
			this.y = 0;
			break;
		default:
			this.x = 60;
			this.y = 310;

		}
		this.ground = this.y;
	}

	public void animation(Graphics2D g2, String animate) {
		if (animate.equalsIgnoreCase("walk")) {

		} else if (animate.equalsIgnoreCase("hit")) {

		} else if (animate.equalsIgnoreCase("jump")) {

		}
	}

	@Override
	public void walk(boolean way) {
		// TODO Auto-generated method stub
		facing = way;
		// if(hitting)
		// return;
		if (onGround && !hitting) {
			if (status != 1)
				currentFrame = 0;
			status = 1;
		}
		if (facing)
			speedX = speed;
		else
			speedX = -speed;
	}

	@Override
	public void stand() {
		// TODO Auto-generated method stub
		if (!hitting) {
			speedX = 0;
			status = 0;
		}
	}

	@Override
	public void hit() {
		// TODO Auto-generated method stub
		if (hitting)
			return;
		if (status != 4) {
			currentFrame = 0;
			// speedX = 0;
		}
		hitting = true;
		status = 4;
	}

	public void jumpHit() {
		if (hitting)
			return;
		if (status != 5) {
			currentFrame = 0;
		}
		hitting = true;
		status = 5;
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
		if (jumpTime < 1 && hitting) {
			speedY = jumpSpeed;
			jumpTime++;
			onGround = false;
		} else if (jumpTime < 1 && status != 3 && status != 5) {
			status = 2;
			jumpTime++;
			speedY = jumpSpeed;
			onGround = false;
		}
	}

	@Override
	public void fall() {
		if (status != 5)
			status = 3;
	}

	@Override
	public void updatePosition() {
		if (onGround) {
			speedY = 0;
			jumpTime = 0;
			if (status == 3 || status == 2)
				status = 0;
		} else {
			speedY--;
			if (speedY < Data.GRAVITY)
				speedY = Data.GRAVITY;
		}
		this.setX(x + speedX);
		this.setY(y - speedY);
	}

	//
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

		frameDelayCount = frameDelay;
		currentFrame++;
		if (currentFrame == frameCount) {
			stop();
			if (hitting) {
				hitting = false;
			}

		}

	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
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

	public AffineTransformOp getOp() {
		AffineTransform tran = AffineTransform.getTranslateInstance(frameWidth, 0);
		AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
		tran.concatenate(flip);
		AffineTransformOp op = new AffineTransformOp(tran, AffineTransformOp.TYPE_BILINEAR);
		return op;
	}

	@Override
	public void render(Graphics2D g2) {
		AffineTransformOp op = null;
		int levelExtentX = Data.levelExtent;
		int drawX = 0;
		if (x <= Data.screenWidth/3){
			drawX = x - (frameWidth / 2);
		}else if (x > levelExtentX - 2*Data.screenWidth/3){
		    drawX = x - levelExtentX + Data.screenWidth - (frameWidth / 2);
		}else{
			drawX = Data.screenWidth/3 - frameWidth/2;
		}
		if (!facing) {
			op = getOp();
		}
		switch (status) {
		case 0: // stand
			if (onGround)
				g2.drawImage(pStand, op, drawX, y - (pStand.getHeight() / 2));
			else
				g2.drawImage(pJump.getSubimage(currentFrame * pJump.getWidth() / 8, 0, pJump.getWidth() / 8,
						pJump.getHeight()), op, drawX, y - (pJump.getHeight() / 2));

			break;
		case 1: // walk/jump
			if (onGround)
				g2.drawImage(pWalk.getSubimage(currentFrame * pWalk.getWidth() / 8, 0, pWalk.getWidth() / 8,
						pWalk.getHeight()), op, drawX, y - (pWalk.getHeight() / 2));
			else
				g2.drawImage(pJump.getSubimage(currentFrame * pJump.getWidth() / 8, 0, pJump.getWidth() / 8,
						pJump.getHeight()), op, drawX, y - (pJump.getHeight() / 2));

			break;
		case 2: // jump
			g2.drawImage(
					pJump.getSubimage(currentFrame * pJump.getWidth() / 8, 0, pJump.getWidth() / 8, pJump.getHeight()),
					op, drawX, y - (pJump.getHeight() / 2));
			break;
		case 3: // jump

			g2.drawImage(
					pJump.getSubimage(currentFrame * pJump.getWidth() / 8, 0, pJump.getWidth() / 8, pJump.getHeight()),
					op, drawX, y - (pJump.getHeight() / 2));
			break;
		case 4: // hit2

			if (facing) {
				op = getOp();
			} else
				op = null;
			g2.drawImage(
					pHit2.getSubimage(currentFrame * pHit2.getWidth() / 8, 0, pHit2.getWidth() / 8, pHit2.getHeight()),
					op,drawX, y - (pHit2.getHeight() / 2));
			break;
		case 5: // hit1
			// System.out.println("Draw " + currentFrame);
			g2.drawImage(
					pHit1.getSubimage(currentFrame * pHit1.getWidth() / 8, 0, pHit1.getWidth() / 8, pHit1.getHeight()),
					op, drawX, y - (pHit1.getHeight() / 2));
			break;
		case 100: // die
			g2.drawImage(pDie, op, drawX, y - (pDie.getHeight() / 2));
			break;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		play();
		InputUtility instance = InputUtility.getInstance();
		while (true) {
			try {
				Thread.sleep(18);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (instance) {
				if (instance.getKeytriggered(KeyEvent.VK_Z)) {
					if (this.isOnGround())
						this.hit();
					else
						this.jumpHit();
				}
				if (instance.getKeypressed(KeyEvent.VK_RIGHT)) {
					this.walk(true);
				} else if (instance.getKeypressed(KeyEvent.VK_LEFT)) {
					this.walk(false);
				} else if (this.getStatus() != 2 && this.getStatus() != 3) {
					this.stand();
				}
				if (instance.getKeytriggered(KeyEvent.VK_SPACE) && this.getStatus() != 3) {
					this.jump();
				}
			}
			instance.postUpdate();
			this.updateAnimation();
			this.updatePosition();
			GameScreen.getGamescreen().repaint();

		}
	}
}
