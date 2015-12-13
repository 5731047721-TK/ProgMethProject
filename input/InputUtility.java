package input;

public class InputUtility {
	private static boolean[] keypressed = new boolean[256];
	private static boolean[] keytriggered = new boolean[256];
	private static final InputUtility instance = new InputUtility();
	public InputUtility(){
	}
	
	public static InputUtility getInstance(){
		return instance;
	}
	
	public boolean getKeypressed(int key){
		if(key>= 0 && key <= 255) 
			return keypressed[key];
		return false;
	}
	
	public boolean getKeytriggered(int key){
		if(key>= 0 && key <= 255) 
			return keytriggered[key];
		return false;
	}
	
	public synchronized void setKeypressed(int key,boolean press){
		if(key>= 0 && key <= 255) {
			keypressed[key] = press;
		}
	}
	
	public synchronized void setKeytriggered(int key,boolean pressed) {
		if(key >=0 && key <= 255){
			keytriggered[key] = pressed;
		}
	}

	public synchronized void postUpdate(){
		for(int i = 0; i < 256; i++){
			keytriggered[i] = false;
		}
		
	}
}
