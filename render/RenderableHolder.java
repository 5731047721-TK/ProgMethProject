package render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RenderableHolder {
	private static final RenderableHolder instance = new RenderableHolder();
	private List<IRenderable> entities;
	
	public RenderableHolder(){
		entities = new ArrayList<IRenderable>();
	}
	
	public static RenderableHolder getInstance(){
		return instance;
	}
	
	public void add(IRenderable ir){
		entities.add(ir);
		Collections.sort(entities, new Comparator<IRenderable>(){
			@Override
			public int compare(IRenderable r1, IRenderable r2){
				if(r1.getZ() > r2.getZ()) 
					return 1;
				return -1;
			}
		});
	}
	
	public List<IRenderable> getRenderableList(){
		return entities;
	}
}