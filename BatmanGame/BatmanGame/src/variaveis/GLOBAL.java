package variaveis;

import com.badlogic.gdx.math.Vector2;

public class GLOBAL {

	public static final float SCREEN_WIDTH		=	800;//480;
	public static final float SCREEN_HEIGHT		=	480;//800;
	
	public static final float WORLD_SCALE		=	1/16;
	public static final Vector2 WORLD_GRAVITY	=	new Vector2(0f, -10f);
	
	public static final float MAX_VELOCITY 		= 	5f;
	
	public static final float FRAME_BASE		=	10.0f;
	public static final float GROUND_Y			=	96.0f;
	public static final float SKY_Y				=	SCREEN_HEIGHT - 64.0f;
	
	
	public static boolean DEBUG					=	true;
	public static boolean BOX2D_DEBUG			=	false;
	public static boolean COLLISION				=	true;
	public static boolean ADDACTOR				=	false; 
	
}