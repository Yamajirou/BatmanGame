package actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimatedActorHero extends AnimatedActorBase{
	private TextureRegion standByRight;
	private TextureRegion standByLeft;
	private TextureRegion standByUp;
	private TextureRegion standByDown;
	private int standBy = -1;
	
	public static final int STANDBY_RIGHT = 0;
	public static final int STANDBY_LEFT = 1;
	public static final int STANDBY_UP = 2;
	public static final int STANDBY_DOWN = 3;
	
	public AnimatedActorHero(float frameDuration, Texture texture, int cols, int rows){
		this(frameDuration, texture, cols, rows, cols * rows);
	}
	
	public AnimatedActorHero(float frameDuration, Texture texture, int cols, int rows, int frameQtd){
		super(frameDuration, texture, cols, rows, frameQtd);
	}
	
	public AnimatedActorHero(float frameDuration, TextureRegion [][] textures) {
		super(frameDuration, textures);
	}
	
	public AnimatedActorHero(float frameDuration, Array <? extends TextureRegion> keyFrames) {
		super(frameDuration, keyFrames);
	}
	
	public AnimatedActorHero(float frameDuration, TextureRegion... frames) {
		super(frameDuration, frames);
	}	
	
	public AnimatedActorHero(float frameDuration, Array <? extends TextureRegion> keyFrames, int playType) {
		super(frameDuration, keyFrames, playType);
	}
	
	public void setStandByTextureRight(int index){
		standByRight = animation.getKeyFrame(animation.frameDuration * index);
	}
	
	public void setStandByTextureRight(TextureRegion standByTextureRight){
		standByRight = standByTextureRight;
	}
	
	public void setStandByTextureLeft(int index){
		standByLeft = animation.getKeyFrame(animation.frameDuration * index);
	}
	
	public void setStandByTextureLeft(TextureRegion standByTextureLeft){
		standByLeft = standByTextureLeft;
	}
	
	public void setStandByTextureUp(int index){
		standByUp = animation.getKeyFrame(animation.frameDuration * index);
	}
	
	public void setStandByTextureUp(TextureRegion standByTextureUp){
		standByUp = standByTextureUp;
	}
	
	public void setStandByTextureDown(int index){
		standByDown = animation.getKeyFrame(animation.frameDuration * index);
	}
	
	public void setStandByTextureDown(TextureRegion standByTextureDown){
		standByDown = standByTextureDown;
	}
	
	public void setStandBy(int position){
		standBy = position;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		switch(standBy){
			case AnimatedActorHero.STANDBY_RIGHT:
				this.currentFrame = standByRight;
				break;
			case AnimatedActorHero.STANDBY_LEFT:
				this.currentFrame = standByLeft;
				break;
			case AnimatedActorHero.STANDBY_UP:
				this.currentFrame = standByUp;
				break;
			case AnimatedActorHero.STANDBY_DOWN:
				this.currentFrame = standByDown;
				break;
			default:
				break;
		}
	}
	
}
