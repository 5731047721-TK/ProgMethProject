package logic;

public abstract class Character {
	protected int status;
	protected int x;
	protected int y;
	protected int speed;
	protected int frameWidth,frameHeight;
	public Character(int status,int speed){
		this.status = status;
		this.speed = speed;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
		if(y >=0 && y<= Data.screenHeight)	
			this.y = y;
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

	public abstract void walk();
	public abstract void stand();
	public abstract void hit();
	public abstract void hurt();
	public abstract void die();
	public abstract void jump();
}
