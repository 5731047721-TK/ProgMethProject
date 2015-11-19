package logic;

public abstract class Character {
	protected int status;
	protected int x;
	protected int y;
	protected int ground;
	protected int jumpSpeed;
	protected int jumpTime;
	protected boolean onGround;
	protected int speed;
	protected int speedX;
	protected int speedY;
	protected int frameWidth,frameHeight;
	protected int currentFrame,frameDelayCount;
	protected int frameCount,frameDelay;
	
	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public int getGround() {
		return ground;
	}

	public void setGround(int ground) {
		this.ground = ground;
	}
	
	public Character(int status,int speed){
		this.status = status;
		this.speed = speed;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		// 2 = jumping
		// 3 = falling
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if(x >= 0 && x <= Data.screenWidth) 
			this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(y > ground && !onGround){
			onGround = true;
//			status = 0;
			y = ground;
		}
		if(y >=0 && y <= ground ){
			this.y = y;
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}

	public abstract void walk(boolean way);
	public abstract void stand();
	public abstract void hit();
	public abstract void hurt();
	public abstract void die();
	public abstract void jump();
	public abstract void fall();
	public abstract void updatePosition();
}
