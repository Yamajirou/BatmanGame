package actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class AnimatedActorHero4Directions extends Actor{
	
	protected Animation animation;
	protected TextureRegion currentFrame;
	protected float stateTime = 0;
	protected int height = 0;
	protected int width = 0;
	
	private TextureRegion standByTextureRight;
	private TextureRegion standByTextureLeft;
	private TextureRegion standByTextureUp;
	private TextureRegion standByTextureDown;
	
	private TextureRegion textures[][];
	private int textureRightIndex = 0;
	private int textureLeftIndex = 0;
	private int textureDownIndex = 0;
	private int textureUpIndex = 0;
	
	private int direction = -1;
	private boolean standBy = false;
	private int frameIndex = 0;
	private int frameIndexRight = 0;
	private int frameIndexLeft = 0;
	private int frameIndexUp = 0;
	private int frameIndexDown = 0;
	private int maxFrames = 1;
	private boolean useFlippedTextureFromRight = false;
	private boolean useFlippedTextureFromLeft = false;
	
	public float frameDuration;
	public float animationDuration;
	
	private int directions; //quantas direcoes tem
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	private int i = 0;
	
	public AnimatedActorHero4Directions(float frameDuration, Texture texture, int cols, int rows){
//		this(frameDuration, texture, cols, rows, cols * rows, 1);
		this.frameDuration = frameDuration;
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth()/cols, texture.getHeight()/rows);
		maxFrames = cols;
		defaultStandBy();
		fillTextures(tmp, cols, rows);
	}
	
	public AnimatedActorHero4Directions(float frameDuration, Texture texture, int cols, int rows, int framesQtd, int directions){
//		super(frameDuration, texture, cols, rows, frameQtd);
		this.frameDuration = frameDuration;
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth()/cols, texture.getHeight()/rows);
		fillTextures(tmp, cols, rows, framesQtd, directions);
		defaultStandBy();
		maxFrames = cols;
	}
	
	public AnimatedActorHero4Directions(float frameDuration, TextureRegion [][] textures) {
		super(frameDuration, textures);
		defaultStandBy();
		maxFrames = textures.length;
	}
	
	public AnimatedActorHero4Directions(float frameDuration, Array <? extends TextureRegion> keyFrames) {
		super(frameDuration, keyFrames);
		defaultStandBy();
	}
	
	public AnimatedActorHero4Directions(float frameDuration, TextureRegion... frames) {
		super(frameDuration, frames);
		defaultStandBy();
	}	
	
	public AnimatedActorHero4Directions(float frameDuration, Array <? extends TextureRegion> keyFrames, int playType) {
		super(frameDuration, keyFrames, playType);
		defaultStandBy();
	}
	
	private void fillTextures(TextureRegion from[][], int cols, int rows){
		textures = new TextureRegion[cols][];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				textures[i][j] = from[i][j];
			}
		}
	}
	
	private void fillTextures(TextureRegion from[][], int cols, int rows, int framesQtd, int directions){
		if((cols*rows) == framesQtd){
			textures = new TextureRegion[cols][];
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < cols; j++){
					textures[i][j] = from[i][j];
				}
			}
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
	
	private void defaultStandBy(){
		standByTextureDown = animation.getKeyFrame(0);
		standByTextureUp = animation.getKeyFrame(0);
		standByTextureLeft = animation.getKeyFrame(0);
		standByTextureRight = animation.getKeyFrame(0);
	}
	
	public int getKeyFrameIndex (float stateTime) {
		int frameNumber = (int)(stateTime / frameDuration);
	}
	
	public void setStandByTextureRight(int index){
		standByTextureRight = animation.getKeyFrame(animation.frameDuration * index);
		if(useFlippedTextureFromRight){
			if(standByTextureRight != null){
				standByTextureLeft = standByTextureRight;
				standByTextureLeft.flip(true, false);
			}
		}
	}
	
	public void setStandByTextureLeft(int index){
		standByTextureLeft = animation.getKeyFrame(animation.frameDuration * index);
		if(useFlippedTextureFromLeft){
			if(standByTextureLeft!= null){
				standByTextureRight = standByTextureLeft;
				standByTextureRight.flip(true, false);
			}
		}
	}
	
	public void setStandByTextureUp(int index){
		standByTextureUp = animation.getKeyFrame(animation.frameDuration * index);
	}
	
	public void setStandByTextureDown(int index){
		standByTextureDown = animation.getKeyFrame(animation.frameDuration * index);
	}
	
	public TextureRegion getStandByTextureRight() {
		return standByTextureRight;
	}

	public void setStandByTextureRight(TextureRegion standByTextureRight) {
		this.standByTextureRight = standByTextureRight;
		if(useFlippedTextureFromRight){
			if(standByTextureRight != null){
				this.standByTextureLeft = this.standByTextureRight;
				this.standByTextureLeft.flip(true, false);
			}
		}
	}

	public TextureRegion getStandByTextureLeft() {
		return standByTextureLeft;
	}

	public void setStandByTextureLeft(TextureRegion standByTextureLeft) {
		this.standByTextureLeft = standByTextureLeft;
		if(useFlippedTextureFromLeft){
			if(standByTextureLeft != null){
				this.standByTextureRight = this.standByTextureLeft;
				this.standByTextureRight.flip(true, false);
			}
		}
	}

	public TextureRegion getStandByTextureUp() {
		return standByTextureUp;
	}

	public void setStandByTextureUp(TextureRegion standByTextureUp) {
		this.standByTextureUp = standByTextureUp;
	}

	public TextureRegion getStandByTextureDown() {
		return standByTextureDown;
	}

	public void setStandByTextureDown(TextureRegion standByTextureDown) {
		this.standByTextureDown = standByTextureDown;
	}
	
	public int getFrameIndex() {
		return frameIndex;
	}

	public void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
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
	
	public int getMaxFrames() {
		return maxFrames;
	}

	public void setMaxFrames(int maxFrames) {
		this.maxFrames = maxFrames;
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
		if(useFlippedTextureFromRight){
			if(this.standByTextureRight != null){
				this.standByTextureLeft = this.standByTextureRight;
				this.standByTextureLeft.flip(true, false);
			}
		}
	}
	
	public boolean isUseFlippedTextureFromLeft() {
		return useFlippedTextureFromLeft;
	}

	public void setUseFlippedTextureFromLeft(boolean useFlippedTextureFromLeft) {
		this.useFlippedTextureFromLeft = useFlippedTextureFromLeft;
		if(useFlippedTextureFromLeft){
			if(this.standByTextureLeft != null){
				this.standByTextureRight = this.standByTextureLeft;
				this.standByTextureRight.flip(true, false);
			}
		}
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
//		System.out.println("name = " + getName());
//		System.out.println("i = " + i++);
		if(standBy){
			switch(direction){
				case AnimatedActorHero4Directions.RIGHT:
					this.currentFrame = standByTextureRight;
					break;
				case AnimatedActorHero4Directions.LEFT:
					this.currentFrame = standByTextureLeft;
					break;
				case AnimatedActorHero4Directions.UP:
					this.currentFrame = standByTextureUp;
					break;
				case AnimatedActorHero4Directions.DOWN:
					this.currentFrame = standByTextureDown;
					break;
				default:
					break;
			}
		}else{
//			int a = (int) ((stateTime % (maxFrames * animation.frameDuration)) / animation.frameDuration);
			frameIndex = (int) ((stateTime % (maxFrames * animation.frameDuration)) / animation.frameDuration);
//			System.out.println("delta = " + delta);
//			System.out.println("stateTime = " + stateTime);
//			System.out.println("maxFrames = " + maxFrames);
//			System.out.println("animation.frameDuration = " + animation.frameDuration);
//			System.out.println("frame = " + frameIndex);
//			System.out.println("direction = " + direction);
			
			switch(direction){
				case AnimatedActorHero4Directions.RIGHT:
					this.currentFrame = this.getFrameByIndex(frameIndex + frameIndexRight);
//					System.out.println("RIGHT: getting frame (" + (frameIndex + frameIndexRight) + ")");
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
					this.currentFrame = this.getFrameByIndex(frameIndex + frameIndexLeft);
//					System.out.println("LEFT: getting frame (" + (frameIndex + frameIndexLeft) + ")");
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
					this.currentFrame = this.getFrameByIndex(frameIndex + frameIndexUp);
					break;
				case AnimatedActorHero4Directions.DOWN:
					this.currentFrame = this.getFrameByIndex(frameIndex + frameIndexDown);
					break;
				default:
					break;
			}
//			frameIndex = (frameIndex + 1) % maxFrames;
//			this.currentFrame.flip(true, false);
			
		}
	}
	
}
