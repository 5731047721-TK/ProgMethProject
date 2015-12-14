package logic;

public class Data {
	public static boolean pause;
	public static final int MAX_HP = 10;
	public static final int MAX_FURY = 500;
	public static final int GRAVITY = -15;
	public static final int screenWidth = 1024;
	public static final int screenHeight = 480;
	public static final int foregroundWidth = 2400;
	public static final int levelExtent = 4*foregroundWidth;
	public static final int hpInit[] = {176,430,263,20};
	public static final int furyInit[] = {176,459,263,9};
	public static final int boss[] = {662,455,875,461};
	public static final int skill[] = {457,406,524,475};
	public static final int totalMon = 30;
	public static final int[] hpMon = {1,2,3,3,4,4,5,0,0,10,2,4,4,5,6,7,0,0,0,15,0,0,0,0,0,0,0,0,1,25};
	public static final int[] speedMon = {1,1,1,1,2,2,1,0,0,2,1,1,2,1,2,3,0,0,0,2,0,0,0,0,0,0,0,0,0,3};
	public static final int[] offsetMon = {0,0,20,0,-10,0,0,0,0,-40,-10,0,-10,-30,0,0,0,0,0,-150,0,0,0,0,0,0,0,0,-75,-100};
	public static final int[] sizeMon = {80,100,80,180,150,200,150,0,0,400,100,120,120,150,200,180,0,0,0,400,0,0,0,0,0,0,0,0,500,500};
	public static final int[] hitDelayMon = {50,60,20,40,60,60,50,0,0,120,70,50,60,60,60,40,0,0,0,20,0,0,0,0,0,0,0,0,0,60};
	public static final int[] chasingRangeMon = {200,200,200,200,300,300,300,0,0,20000,300,300,400,400,400,400,0,0,0,20000,0,0,0,0,0,0,0,0,0,20000};
	public static final int[] map = {7,6,0};
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
