package input;

public class InputUtility {
	private static boolean[] keypressed = new boolean[2];
	
	public InputUtility(){
	}
	
	public static boolean getKeypressed(int key){
		if(key>= 0 && key <= 255) 
			return keypressed[key];
		return false;
	}
	
	public static void setKeypressed(int key,boolean press){
		if(key>= 0 && key <= 255) 
			keypressed[key] = press;
	}
}
