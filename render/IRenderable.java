package render;

import java.awt.Graphics2D;

public interface IRenderable {
	public boolean isVisible();
	public int getZ();
	public void render(Graphics2D g2);
}
