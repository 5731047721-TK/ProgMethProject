package render;

import java.awt.AlphaComposite;
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
	private float opacity;
	private int direction;
	public GameScreen(){
		super();
		
		setDoubleBuffered(true);
//		this.logic = logic;
		this.setPreferredSize(new Dimension(Data.screenWidth,Data.screenHeight));
		opacity = 0;
		direction = 1;
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
				if(e.getKeyCode() >= 0 && e.getKeyCode() <= 255  && !InputUtility.getKeypressed(e.getKeyCode())){ 
					InputUtility.setKeypressed(e.getKeyCode(),true);
					InputUtility.setKeytriggered(e.getKeyCode(), true);
				}
			}
		});
		
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		g2d.setBackground(Color.white);
		g2d.clearRect(0, 0, Data.screenWidth, Data.screenHeight);
		opacity+= 0.01*direction;
		if(opacity >= 0.999 || opacity <= 0.001)
			direction*=-1;
//		System.out.println(opacity);
		synchronized (RenderableHolder.getInstance()) {
			for(IRenderable entity : RenderableHolder.getInstance().getRenderableList()){
				if(entity instanceof Foreground && ((Foreground)entity).fadable)
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
				else g2d.setComposite(AlphaComposite.SrcOver.derive(1f));
				if(entity.isVisible())
					entity.render(g2d);
			}
		}
	}
}
