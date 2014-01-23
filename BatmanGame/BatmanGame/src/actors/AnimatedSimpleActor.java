package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AnimatedSimpleActor extends Actor {
//	private final AnimationDrawable drawable;
	
	private int        FRAME_COLS = 0;         // #1
    private int        FRAME_ROWS = 0;         // #2
    
    Animation                       animation;          // #3
    Texture                         sheet;              // #4
    TextureRegion[]                 frames;             // #5
    Sprite [] sprite;
    Sprite currentSprite;
    
    Texture spriteTex;
    
    private float height = 0;
    private float width = 0;
    
    float frameDur;
    int spriteIndex = 0;
    int idleSprite = 0;
//    SpriteBatch                     spriteBatch;            // #6
    TextureRegion                   currentFrame;           // #7
    
    private boolean looping = false;
    
//    private float frameDuration = 0.025f; // default
    
    float stateTime = 0;
    
    public AnimatedSimpleActor(String filePath, int frameQt, int frameCols, int frameRows, float frameDuration, int idleFrame) {
    	this(filePath, frameQt, frameCols, frameRows, frameDuration);
    	idleSprite = idleFrame;
    }
    
    public AnimatedSimpleActor(String filePath, int frameQt, int frameCols, int frameRows, float frameDuration) {
//    	super(tex);
    	super();
    	frameDur = frameDuration;
//    	sprite
    	sheet = new Texture(Gdx.files.internal(filePath));
//    	this.setb
//    	sheet.
//    	sheet = tex;
    	
		FRAME_COLS = frameCols;
		FRAME_ROWS = frameRows;
		height = sheet.getHeight() / FRAME_ROWS;
		width = sheet.getWidth() / FRAME_COLS;
		System.out.println("height = " + height);
		System.out.println("width = " + width);
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / 
				FRAME_COLS, sheet.getHeight() / FRAME_ROWS);
		
		frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		sprite = new Sprite[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			if(i != FRAME_ROWS - 1){
				for (int j = 0; j < FRAME_COLS; j++) {
					frames[index++] = tmp[i][j];
//                System.out.println("index = " + index);
					sprite[index - 1] = new Sprite(frames[index - 1]);
//                sprite[index - 1].setSize(0.5f, 0.5f);
//                sprite[index - 1].setBounds(1f, 1f, 0.1f, 0.1f);
//                frames[index-1].set
				}
			}else{
				for (int j = 0; j < FRAME_COLS - (frameQt - (FRAME_COLS * FRAME_ROWS)); j++) {
					frames[index++] = tmp[i][j];
//                System.out.println("index = " + index);
					sprite[index - 1] = new Sprite(frames[index - 1]);
//                sprite[index - 1].setSize(0.5f, 0.5f);
//                sprite[index - 1].setBounds(1f, 1f, 0.1f, 0.1f);
//                frames[index-1].set
				}
			}
//	        System.out.println(index + " - " + frames[index].getTexture().toString());
		}
		spriteTex = sprite[0].getTexture();
		animation = new Animation(frameDuration, frames);
//		animation.setPlayMode(animation.LOOP);
//		spriteBatch = new SpriteBatch();
		stateTime = 0f;
    }
    
    public void animate(){
    	this.setVisible(true);
    	this.reset();
    }
	
    //Returns a TextureRegion based on the so called state time.
    public TextureRegion getKeyFrame(float stateTime){
    	return animation.getKeyFrame(stateTime);
    }

    //Returns a TextureRegion based on the so called state time.
    public TextureRegion getKeyFrame(float stateTime, boolean looping){
    	return animation.getKeyFrame(stateTime, looping);
    }

    //Returns the current frame number.
    public int getKeyFrameIndex(float stateTime){
    	return animation.getKeyFrameIndex(stateTime);
    }
    
//    Returns the keyFrames[] array where all the TextureRegions of the animation are stored.
//    public TextureRegion[] getKeyFrames(){
//    	
//    }

    //Returns the animation play mode.
    public int getPlayMode(){
    	return animation.getPlayMode();
    }

    //Whether the animation would be finished if played without looping (PlayMode Animation#NORMAL), 
    //given the state time.
    public boolean isAnimationFinished(float stateTime){
    	return animation.isAnimationFinished(stateTime);
    }

    //Sets the animation play mode.
    public void setPlayMode(int playMode){
    	if(playMode == Animation.NORMAL || playMode == Animation.REVERSED){
    		looping = false;
    	}else{
    		looping = true;
    	}
    	animation.setPlayMode(playMode);
    }
    
    public boolean isAnimationFinished(){
    	return animation.isAnimationFinished(stateTime);
    }
    
    public boolean isLooping(){
    	return looping;
    }
    
    public void reset(){
    	stateTime = 0;
    }

	@Override
	public void act(float delta) {
//		drawable.act(delta);
		stateTime += delta;
		currentFrame = animation.getKeyFrame(stateTime, looping);
		spriteIndex = (int)(stateTime / frameDur);
		spriteIndex = spriteIndex % sprite.length;

//		System.out.println("AnimatedImageActor.act()");
		super.act(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(!looping && (stateTime / frameDur) > sprite.length)
			return;
		
//		System.out.println("AnimatedImage.draw()");
		super.draw(batch, parentAlpha);
//		batch.draw(animation.getKeyFrame(stateTime, looping), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		if(currentFrame == null){
			System.out.println("currentFrame == null");
		}
//		System.out.println("spriteIndex = " + spriteIndex);
//		sprite = new Sprite(currentFrame);
//		sprite.
//		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight()/FRAME_ROWS);
//		batch.begin();
//		batch.draw(currentFrame, this.getX(), this.getY());
//		sprite[spriteIndex].setSize(0.2f, 0.2f);
//		sprite[spriteIndex].setBounds(1, 1, 1, 1);
		currentSprite = sprite[spriteIndex];
//		sprite[spriteIndex].draw(batch);
//		currentSprite.rotate(45);
//		currentSprite.flip(false, true);
//		currentSprite.
//		batch.draw(currentSprite, this.getX(), this.getY(), width/10, height/10);
		batch.draw(currentSprite, this.getX(), this.getY(), 0, 0, parentAlpha, parentAlpha, 
				height*this.getScaleX(), width*this.getScaleY(), this.getRotation(), false);
//		currentSprite.draw(batch);
//		batch.draw
//		batch.draw(spriteTex, this.getX(), this.getY(), width/10, height/10);
//		System.out.println("spriteIndex = " + spriteIndex);
//		batch.draw(sprite[spriteIndex].getTexture(), this.getX(), this.getY(), 0, 0, 
//		batch.draw(spriteTex, this.getX(), this.getY(), 0, 0, 
//				parentAlpha, parentAlpha, width/10, height/10, 0, 
//				Math.round(this.getX()), Math.round(this.getY()), Math.round(width/10), Math.round(height/10), 
//				10, 10, 100, 10, 
//				false, false);
//		batch.end();
	}
	
	
	
	public void dispose(){
		sheet.dispose();
//		currentFrame.getTexture().dispose();
//		for(TextureRegion f : frames){
//			f.getTexture().dispose();
//		}
	}
}

