package input;

public class InputUtility {
	private static boolean[] keypressed = new boolean[256];
	private static boolean[] keytriggered = new boolean[256];
	
	public InputUtility(){
	}
	
	public static boolean getKeypressed(int key){
		if(key>= 0 && key <= 255) 
			return keypressed[key];
		return false;
	}
	
	public static boolean getKeytriggered(int key){
		if(key>= 0 && key <= 255) 
			return keytriggered[key];
		return false;
	}
	
	public static void setKeypressed(int key,boolean press){
		if(key>= 0 && key <= 255) {
			keypressed[key] = press;
		}
	}
	
	public static void setKeytriggered(int key,boolean pressed) {
		if(key >=0 && key <= 255)
			keytriggered[key] = pressed;
	}

	public static void postUpdate(){
		for(int i = 0; i < 256; i++){
			keytriggered[i] = false;
		}
		
	}
}
