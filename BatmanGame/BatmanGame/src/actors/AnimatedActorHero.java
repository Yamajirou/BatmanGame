package actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimatedActorHero extends AnimatedActorBase{
	private TextureRegion standByTextureRight;
	private TextureRegion standByTextureLeft;
	private TextureRegion standByTextureUp;
	private TextureRegion standByTextureDown;
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
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	private int i = 0;
	
	public AnimatedActorHero(float frameDuration, Texture texture, int cols, int rows){
		this(frameDuration, texture, cols, rows, cols * rows);
	}
	
	public AnimatedActorHero(float frameDuration, Texture texture, int cols, int rows, int frameQtd){
		super(frameDuration, texture, cols, rows, frameQtd);
		defaultStandBy();
		maxFrames = cols;
	}
	
	public AnimatedActorHero(float frameDuration, TextureRegion [][] textures) {
		super(frameDuration, textures);
		defaultStandBy();
		maxFrames = textures.length;
	}
	
	public AnimatedActorHero(float frameDuration, Array <? extends TextureRegion> keyFrames) {
		super(frameDuration, keyFrames);
		defaultStandBy();
	}
	
	public AnimatedActorHero(float frameDuration, TextureRegion... frames) {
		super(frameDuration, frames);
		defaultStandBy();
	}	
	
	public AnimatedActorHero(float frameDuration, Array <? extends TextureRegion> keyFrames, int playType) {
		super(frameDuration, keyFrames, playType);
		defaultStandBy();
	}
	
	private void defaultStandBy(){
		standByTextureDown = animation.getKeyFrame(0);
		standByTextureUp = animation.getKeyFrame(0);
		standByTextureLeft = animation.getKeyFrame(0);
		standByTextureRight = animation.getKeyFrame(0);
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
				case AnimatedActorHero.RIGHT:
					this.currentFrame = standByTextureRight;
					break;
				case AnimatedActorHero.LEFT:
					this.currentFrame = standByTextureLeft;
					break;
				case AnimatedActorHero.UP:
					this.currentFrame = standByTextureUp;
					break;
				case AnimatedActorHero.DOWN:
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
				case AnimatedActorHero.RIGHT:
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
				case AnimatedActorHero.LEFT:
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
				case AnimatedActorHero.UP:
					this.currentFrame = this.getFrameByIndex(frameIndex + frameIndexUp);
					break;
				case AnimatedActorHero.DOWN:
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
