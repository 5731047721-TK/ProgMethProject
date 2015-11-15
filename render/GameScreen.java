package render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

import input.InputUtility;
import logic.Data;

public class GameScreen extends JComponent{
	
	private static final long serialVersionUID = 1L;

	
	
	public GameScreen(){
		super();
		setDoubleBuffered(true);
//		this.logic = logic;
		this.setPreferredSize(new Dimension(Data.screenWidth,Data.screenHeight));
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() >= 0 && e.getKeyCode() <= 255){
					InputUtility.setKeypressed(e.getKeyCode(),false);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() >= 0 && e.getKeyCode() <= 255){
					InputUtility.setKeypressed(e.getKeyCode(),true);
				}
			}
		});
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(Color.white);
		g2d.clearRect(0, 0, Data.screenWidth, Data.screenHeight);
		synchronized (RenderableHolder.getInstance()) {
			for(IRenderable entity : RenderableHolder.getInstance().getRenderableList()){
				if(entity.isVisible())
					entity.render(g2d);
			}
		}
	}
}
