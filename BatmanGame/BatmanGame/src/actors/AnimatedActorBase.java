package actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class AnimatedActorBase extends Actor{
	protected Animation animation;
	protected TextureRegion currentFrame;
	protected float stateTime = 0;
	protected int height = 0;
	protected int width = 0;
	
	public AnimatedActorBase(){}
	
	public AnimatedActorBase(float frameDuration, Texture texture, int cols, int rows){
		this(frameDuration, texture, cols, rows, cols * rows);
	}
	public AnimatedActorBase(float frameDuration, Texture texture, int cols, int rows, int frameQtd){
		if(texture != null){
			TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth()/cols, texture.getHeight()/rows);
			TextureRegion t[] = new TextureRegion [tmp.length * tmp[0].length];
			int index = 0;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if(index >= frameQtd)
						continue;
					t[index++] = tmp[i][j];
				}
			}
			animation = new Animation(frameDuration, t);
			height = t[0].getRegionHeight();
			width = t[0].getRegionWidth();
		}
	}
	
	public AnimatedActorBase(float frameDuration, TextureRegion [][] textures) {
		if(textures != null && textures.length > 0){
			TextureRegion t[] = new TextureRegion[textures.length * textures[0].length];
			int index = 0;
			for(int i = 0; i < textures.length; i++){
				for( int j = 0; j < textures[i].length; j++){
					t[index++] = textures[i][j];
				}
			}
			animation = new Animation(frameDuration, t);
//			animation.setPlayMode(Animation.LOOP);
			height = t[0].getRegionHeight();
			width = t[0].getRegionWidth();
		}
	}
	
	public AnimatedActorBase(float frameDuration, Array <? extends TextureRegion> keyFrames) {
		animation = new Animation(frameDuration, keyFrames);
		if(keyFrames.size != 0){
			height = keyFrames.first().getRegionHeight();
			width = keyFrames.first().getRegionWidth();
		}
	}
	
	public AnimatedActorBase(float frameDuration, TextureRegion... frames) {
		animation = new Animation(frameDuration, frames);
		if(frames[0] != null){
			height = frames[0].getRegionHeight();
			width = frames[0].getRegionWidth();
		}
	}	
	
	public AnimatedActorBase(float frameDuration, Array <? extends TextureRegion> keyFrames, int playType) {
		animation = new Animation(frameDuration, keyFrames, playType);
		if(keyFrames.size != 0){
			height = keyFrames.first().getRegionHeight();
			width = keyFrames.first().getRegionWidth();
		}
	}
	
	public void setPlayMode(int playMode){
		animation.setPlayMode(playMode);
	}
	
	public int getPlayMode(){
		return animation.getPlayMode();
	}
	
	public float getAnimationDuration(){
		return animation.animationDuration;
	}
	
	public float getFrameDuration(){
		return animation.frameDuration;
	}
	
	public boolean isAnimationFinished(){
		return animation.isAnimationFinished(stateTime);
	}
	
	public TextureRegion getFrameByIndex(int index){
		return animation.getKeyFrame(animation.frameDuration * index);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
		currentFrame = animation.getKeyFrame(stateTime);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(currentFrame, this.getX(), this.getY(), 0, 0, parentAlpha, parentAlpha, 
				height*this.getScaleX(), width*this.getScaleY(), this.getRotation(), false);
	}
	
	
}
