package logic;

public class Data {
	public static boolean pause;
	public static final int MAX_HP = 3;
	public static final int GRAVITY = -15;
	public static final int screenWidth = 1024;
	public static final int screenHeight = 480;
	public static final int foregroundWidth = 2400;
	public static final int levelExtent = 4*foregroundWidth;
	public static final int hpInit[] = {176,430,438,449};
	public static final int furyInit[] = {176,459,438,467};
	public static final int boss[] = {662,455,875,461};
	public static final int skill[] = {457,406,524,475};
	public static final int totalMon = 8;
	public static final int[] hpMon = {2,2,3,3,4,4,5,5,6,6};
	public static final int[] speedMon = {1,2,1,1,2,2,1,1,2,2};
	public static final int[] offsetMon = {0,0,20,0,0,0,0,0,0,0};
	public static final int[] sizeMon = {80,200,100,180,180,250,220,230,150,350};
	public static final int[] hitDelayMon = {50,40,20,40,30,20,40,60,30,30,30};
	//will be changed when actual sprite done
	public static final int frameCountMaleWalk = 4;
	public static final int frameCountMaleHit = 4;
	public static final int frameCountMaleJump = 4;
	
	public static final int frameCountFemaleWalk = 4;
	public static final int frameCountFemaleHit = 4;
	public static final int frameCountFemaleJump = 4;
	
	public static final int frameCountMonWalk = 4;
	public static final int frameCountMonHit = 4;
	public static final int frameCountMonJump = 4;
}
