package actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class AnimatedActorHero4Directions extends Actor{
	
	protected Animation animation;
	protected TextureRegion currentFrame;
	protected float stateTimeRight = 0;
	protected float stateTimeLeft = 0;
	protected float stateTimeDown = 0;
	protected float stateTimeUp = 0;
	
	protected int height = 0;
	protected int width = 0;
	
	private TextureRegion textures[][];
	
	private boolean standBy = false;

	private int frameIndexRight = 0;
	private int frameIndexLeft = 0;
	private int frameIndexUp = 0;
	private int frameIndexDown = 0;
	
	private int rowRight = 0;
	private int rowLeft = 1;
	private int rowDown = 2;
	private int rowUp = 3;
	
	private int standByIndexRight = 0;
	private int standByIndexLeft = 0;
	private int standByIndexUp = 0;
	private int standByIndexDown = 0;
	
	private boolean useFlippedTextureFromRight = false;
	private boolean useFlippedTextureFromLeft = false;
	
	public float frameDuration;
	public float animationDuration;
	
	private int directions; //quantas direcoes tem
	
	private int direction = -1;
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	private int i = 0;
	
	public AnimatedActorHero4Directions(float frameDuration, Texture texture, int cols, int rows){
		this.frameDuration = frameDuration;
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth()/cols, texture.getHeight()/rows);
		fillTextures(tmp, cols, rows);
	}
	
	public AnimatedActorHero4Directions(float frameDuration, Texture texture, int cols, int rows, int framesQtd, int directions){
		this.frameDuration = frameDuration;
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth()/cols, texture.getHeight()/rows);
		fillTextures(tmp, cols, rows, framesQtd, directions);
	}
	
	public AnimatedActorHero4Directions(float frameDuration, TextureRegion [][] textures) {
		this.frameDuration = frameDuration;
		fillTextures(textures, textures[0].length, textures.length);
	}
	
	public AnimatedActorHero4Directions(float frameDuration, Array <? extends TextureRegion> keyFrames) {
		this.frameDuration = frameDuration;
		textures = new TextureRegion[1][keyFrames.size];
		int i = 0;
		for(TextureRegion tr : keyFrames){
			textures[0][i++] = tr;
		}
	}
	
	public AnimatedActorHero4Directions(float frameDuration, TextureRegion... frames) {
		this.frameDuration = frameDuration;
		textures = new TextureRegion[1][frames.length];
		for(int i = 0; i < frames.length; i++){
			textures[0][i] = frames[i];
		}
	}
	
	private void fillTextures(TextureRegion from[][], int cols, int rows){
		textures = new TextureRegion[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				textures[i][j] = from[i][j];
			}
		}
	}
	
	private void fillTextures(TextureRegion from[][], int cols, int rows, int framesQtd, int directions){
		if((cols*rows) == framesQtd){
			fillTextures(from, cols, rows);
		}else{
			textures = new TextureRegion[directions][];
			int ci, ri;
			ci = ri = 0;
			int framesPerDirection = framesQtd/directions;
			for(int i = 0; i < rows; i++, ri++){
				if(ri  >= framesPerDirection){
					ri = 0;
					ci++;
				}
				for(int j = 0; j < cols; j++){
					textures[ci][ri] = from[i][j];
				}
			}
		}
	}
	
	public int getFrameIndexRight() {
		return frameIndexRight;
	}

	public void setFrameIndexRight(int frameIndexRight) {
		this.frameIndexRight = frameIndexRight;
		if(useFlippedTextureFromRight)
			this.frameIndexLeft = this.frameIndexRight;
	}

	public int getFrameIndexLeft() {
		return frameIndexLeft;
	}

	public void setFrameIndexLeft(int frameIndexLeft) {
		this.frameIndexLeft = frameIndexLeft;
		if(useFlippedTextureFromLeft)
			this.frameIndexRight = this.frameIndexLeft;
	}

	public int getFrameIndexUp() {
		return frameIndexUp;
	}

	public void setFrameIndexUp(int frameIndexUp) {
		this.frameIndexUp = frameIndexUp;
	}

	public int getFrameIndexDown() {
		return frameIndexDown;
	}

	public void setFrameIndexDown(int frameIndexDown) {
		this.frameIndexDown = frameIndexDown;
	}

	public void setDirection(int direction){
		this.direction = direction;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public boolean isUseFlippedTextureFromRight() {
		return useFlippedTextureFromRight;
	}

	public void setUseFlippedTextureFromRight(boolean useFlippedTextureFromRight) {
		this.useFlippedTextureFromRight = useFlippedTextureFromRight;
	}
	
	public boolean isUseFlippedTextureFromLeft() {
		return useFlippedTextureFromLeft;
	}

	public void setUseFlippedTextureFromLeft(boolean useFlippedTextureFromLeft) {
		this.useFlippedTextureFromLeft = useFlippedTextureFromLeft;
	}
	

	public void setStandBy(boolean standing){
		standBy = standing;
	}

	public boolean isStandBy(){
		return standBy;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(standBy){
			switch(direction){
				case AnimatedActorHero4Directions.RIGHT:
					this.currentFrame = textures[rowRight][standByIndexRight];
					break;
				case AnimatedActorHero4Directions.LEFT:
					this.currentFrame = textures[rowLeft][standByIndexLeft];
					break;
				case AnimatedActorHero4Directions.UP:
					this.currentFrame = textures[rowUp][standByIndexUp];
					break;
				case AnimatedActorHero4Directions.DOWN:
					this.currentFrame = textures[rowDown][standByIndexDown];
					break;
				default:
					this.currentFrame = textures[0][0];
					break;
			}
			stateTimeDown = 0;
			stateTimeLeft = 0;
			stateTimeRight = 0;
			stateTimeUp = 0;
		}else{
			int index = 0;
			switch(direction){
				case AnimatedActorHero4Directions.RIGHT:
					stateTimeLeft = 0;
					stateTimeRight += delta;
					stateTimeDown = 0;
					stateTimeUp = 0;
					index = (int) ((stateTimeRight % (textures[rowLeft].length * animation.frameDuration)) / animation.frameDuration);
					this.currentFrame = textures[rowRight][index];
					if(useFlippedTextureFromLeft){
						if(!this.currentFrame.isFlipX()){
							this.currentFrame.flip(true, false);
						}
					}
					if(useFlippedTextureFromRight){
						if(this.currentFrame.isFlipX()){
							this.currentFrame.flip(true, false);
						}
					}
					break;
				case AnimatedActorHero4Directions.LEFT:
					stateTimeLeft += delta;
					stateTimeDown = 0;
					stateTimeRight = 0;
					stateTimeUp = 0;
					index = (int) ((stateTimeLeft % (textures[rowLeft].length * animation.frameDuration)) / animation.frameDuration);
					this.currentFrame = textures[rowLeft][index];
					if(useFlippedTextureFromLeft){
						if(this.currentFrame.isFlipX()){
							this.currentFrame.flip(true, false);
						}
					}
					if(useFlippedTextureFromRight){
						if(!this.currentFrame.isFlipX()){
							this.currentFrame.flip(true, false);
						}
					}
					break;
				case AnimatedActorHero4Directions.UP:
					stateTimeLeft = 0;
					stateTimeDown = 0;
					stateTimeRight = 0;
					stateTimeUp += delta;
					index = (int) ((stateTimeUp % (textures[rowLeft].length * animation.frameDuration)) / animation.frameDuration);
					this.currentFrame = textures[rowLeft][index];
					break;
				case AnimatedActorHero4Directions.DOWN:
					stateTimeLeft = 0;
					stateTimeDown += delta;
					stateTimeRight = 0;
					stateTimeUp = 0;
					index = (int) ((stateTimeDown % (textures[rowLeft].length * animation.frameDuration)) / animation.frameDuration);
					this.currentFrame = textures[rowLeft][index];
					break;
				default:
					stateTimeLeft = 0;
					stateTimeDown = 0;
					stateTimeRight = 0;
					stateTimeUp = 0;
					index = 0;
					this.currentFrame = textures[0][0];
					System.out.println("Default????");
					break;
			}
		}
	}
}
