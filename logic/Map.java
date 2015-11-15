package logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import render.IRenderable;

public class Map implements IRenderable{
	private int map[][];
	private boolean visible;
	private BufferedImage bg = null;
	
	public Map(int level){
		
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return Integer.MIN_VALUE;
	}

	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}
}
