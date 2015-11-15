package logic;

import java.awt.Graphics2D;

import render.IRenderable;

public class Monster extends Character implements IRenderable{
	private int hp;
	private int no;
	
	public Monster(int status,int speed, int hp,int no){
		super(status,speed);
		this.no = no;
		this.hp = Data.hpMon[no];
		String source = "src/monster/"+no+".png";
//		try{
//		ClassLoader loader = Player.class.getClassLoader();
//		player = ImageIO.read(loader.getResource(source));
//	} catch(Exception e){
//		e.printStackTrace();
//		player = null;
//	}
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
		
	}

}
