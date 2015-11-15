package logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import render.IRenderable;
import render.RenderableHolder;

public class Player extends Character implements IRenderable{
	private static BufferedImage player = null;
	private int hp;
	private int gender;
	private boolean visible;
	private boolean playing;
	

	private int currentFrame,frameDelayCount;
	private int frameCount,frameDelay;
	
	public Player(int status,int speed,int gender){
		super(status,speed);
		this.hp = Data.MAX_HP;
		this.gender = gender;
		this.playing = true;
		this.visible = true;
		RenderableHolder.getInstance().add(this);
		start(0);
		this.frameCount = 8;
		this.frameDelay = 4;
		this.currentFrame = 0;
		this.frameDelayCount = 0;
		try{
			ClassLoader loader = Player.class.getClassLoader();
			player = ImageIO.read(loader.getResource((gender == 1)?"src/player/male.png":"src/player/female.png"));
			frameWidth = player.getWidth()/8;
			frameHeight = player.getHeight();
		} catch(Exception e){
			e.printStackTrace();
			player = null;
		}
		//debug
		
	}
	
	public void start(int level){
		switch(level){
			default : this.x = 20;
					  y = 200;
		}
	}
	
	public void animation(Graphics2D g2 ,String animate){
		if(animate.equalsIgnoreCase("walk")){
			
		}
		else if(animate.equalsIgnoreCase("hit")){
			
		}
		else if(animate.equalsIgnoreCase("jump")){
			
		}
	}

	@Override
	public void walk() {
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
//		g2.fillOval(x, y, 120, 120);
		g2.drawImage(player.getSubimage(currentFrame * player.getWidth()/8, 0, player.getWidth()/8, player.getHeight()), null, x, y);
	}
	
	public void play(){
		currentFrame = 0;
		playing = true;
		visible = true;
	}
	
	public void stop(){
		currentFrame = 0;
		playing = false;
		visible = true;
	}
	
	public void updateAnimation(){
		if(!playing) return;
		if(frameDelayCount>0){
			frameDelayCount--;
			return;
		}
		frameDelayCount = frameDelay;
		currentFrame++;
		if(currentFrame == frameCount){
			stop();
		}
	}
	
	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
}
