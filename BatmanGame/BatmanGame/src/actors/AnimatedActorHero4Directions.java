package actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class AnimatedActorHero4Directions extends Actor{
	
	protected TextureRegion currentFrame;
	public Sprite spriteFrame;
	public float rot = 0;
	
	protected float stateTimeRight = 0;
	protected float stateTimeLeft = 0;
	protected float stateTimeDown = 0;
	protected float stateTimeUp = 0;
	
	private TextureRegion textures[][];
	
	private boolean standBy = false;
	public int currentIndex = 0;

//	private int frameIndexRight = 0;
//	private int frameIndexLeft = 0;
//	private int frameIndexUp = 0;
//	private int frameIndexDown = 0;
	
	private int rowRight = 0;
	private int rowLeft = 0;
	private int rowDown = 0;
	private int rowUp = 0;
	
	private int standByIndexRight = 0;
	private int standByIndexLeft = 0;
	private int standByIndexUp = 0;
	private int standByIndexDown = 0;
	
	private boolean useFlippedTextureFromRight = false;
	private boolean useFlippedTextureFromLeft = false;
	
	public float frameDuration = 0.25f;
//	public float animationDuration;
	
//	private int directions; //quantas direcoes tem
	
	private int direction = -1;
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
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
		spriteFrame = new Sprite(textures[0][0]);
		this.setHeight(textures[0][0].getRegionHeight());
		this.setWidth(textures[0][0].getRegionWidth());
	}
	
	public AnimatedActorHero4Directions(float frameDuration, TextureRegion... frames) {
		this.frameDuration = frameDuration;
		textures = new TextureRegion[1][frames.length];
		for(int i = 0; i < frames.length; i++){
			textures[0][i] = frames[i];
		}
		spriteFrame = new Sprite(textures[0][0]);
		this.setHeight(textures[0][0].getRegionHeight());
		this.setWidth(textures[0][0].getRegionWidth());
	}
	
	private void fillTextures(TextureRegion from[][], int cols, int rows){
		textures = new TextureRegion[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				textures[i][j] = from[i][j];
			}
		}
		spriteFrame = new Sprite(textures[0][0]);
		this.setHeight(textures[0][0].getRegionHeight());
		this.setWidth(textures[0][0].getRegionWidth());
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
			spriteFrame = new Sprite(textures[0][0]); 
			this.setHeight(textures[0][0].getRegionHeight());
			this.setWidth(textures[0][0].getRegionWidth());
		}
	}
	
	
	public void setOrigin(float x, float y){
		spriteFrame.setOrigin(x, y);
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
			this.rowLeft = this.rowRight;
			this.standByIndexLeft = this.standByIndexRight;
		}
	}
	
	public boolean isUseFlippedTextureFromLeft() {
		return useFlippedTextureFromLeft;
	}

	public void setUseFlippedTextureFromLeft(boolean useFlippedTextureFromLeft) {
		this.useFlippedTextureFromLeft = useFlippedTextureFromLeft;
		if(useFlippedTextureFromLeft){
			this.rowRight = this.rowLeft;
			this.standByIndexRight = this.standByIndexLeft;
		}
	}
	
	public int getRowRight() {
		return rowRight;
	}

	public void setRowRight(int rowRight) {
		this.rowRight = rowRight;
		if(this.useFlippedTextureFromRight){
			this.rowLeft = this.rowRight;
			this.standByIndexLeft = this.standByIndexRight;
		}
	}

	public int getRowLeft() {
		return rowLeft;
	}

	public void setRowLeft(int rowLeft) {
		this.rowLeft = rowLeft;
		if(this.useFlippedTextureFromLeft){
			this.rowRight = this.rowLeft;
			this.standByIndexRight = this.standByIndexLeft;
		}
	}

	public int getRowDown() {
		return rowDown;
	}

	public void setRowDown(int rowDown) {
		this.rowDown = rowDown;
	}

	public int getRowUp() {
		return rowUp;
	}

	public void setRowUp(int rowUp) {
		this.rowUp = rowUp;
	}

	public int getStandByIndexRight() {
		return standByIndexRight;
	}

	public void setStandByIndexRight(int standByIndexRight) {
		this.standByIndexRight = standByIndexRight;
		if(this.useFlippedTextureFromRight){
			this.standByIndexLeft = this.standByIndexRight;
		}
	}

	public int getStandByIndexLeft() {
		return standByIndexLeft;
	}

	public void setStandByIndexLeft(int standByIndexLeft) {
		this.standByIndexLeft = standByIndexLeft;
		if(this.useFlippedTextureFromLeft){
			this.standByIndexRight = this.standByIndexLeft;
		}
	}

	public int getStandByIndexUp() {
		return standByIndexUp;
	}

	public void setStandByIndexUp(int standByIndexUp) {
		this.standByIndexUp = standByIndexUp;
	}

	public int getStandByIndexDown() {
		return standByIndexDown;
	}

	public void setStandByIndexDown(int standByIndexDown) {
		this.standByIndexDown = standByIndexDown;
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
					index = (int) ((stateTimeRight % (textures[rowRight].length * frameDuration)) / frameDuration);
					this.currentFrame = textures[rowRight][index];
					currentIndex = index;
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
					index = (int) ((stateTimeLeft % (textures[rowLeft].length * frameDuration)) / frameDuration);
					this.currentFrame = textures[rowLeft][index];
					if(this.currentFrame == null){
						System.out.println("currentFrame == null!!!!!");
					}
					currentIndex = index;
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
					index = (int) ((stateTimeUp % (textures[rowUp].length * frameDuration)) / frameDuration);
					this.currentFrame = textures[rowUp][index];
					break;
				case AnimatedActorHero4Directions.DOWN:
					stateTimeLeft = 0;
					stateTimeDown += delta;
					stateTimeRight = 0;
					stateTimeUp = 0;
					index = (int) ((stateTimeDown % (textures[rowDown].length * frameDuration)) / frameDuration);
					this.currentFrame = textures[rowDown][index];
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
		spriteFrame.setRegion(currentFrame);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
//		currentFrame.
//		spriteFrame.setRegion(currentFrame);
//		spriteFrame.setOrigin(0, 0);
//		spriteFrame.
//		spriteFrame.setBounds(1, 1, 1, 1);
//		spriteFrame.setScale(this.getScaleX(), this.getScaleY());
//		spriteFrame.setPosition(this.getX(), this.getY());
//		spriteFrame.set
//		batch.draw(currentFrame, this.getX(), this.getY(), 
////				this.getOriginX()/2, this.getOriginY()/2, 
//				0, 0,
//				parentAlpha, parentAlpha, 
//				this.getHeight()*this.getScaleX(), this.getWidth()*this.getScaleY(), 
//				this.getRotation());
		spriteFrame.draw(batch);
//		spriteFrame.rotate(1);
//		System.out.println("-----------drawing--------------");
//		System.out.println("alpha = " + parentAlpha);
//		System.out.println(this.getName() + "(" + getX() + ", " + getY() + ")");
//		System.out.println("origin (" + ((this.getOriginX()/2)*this.getScaleX()) + ", " + ((this.getOriginY()/2)*this.getScaleY()) + ")");
//		System.out.println("heigth = " + this.getHeight());
//		System.out.println("ScaleX = " + (this.getHeight() * this.getScaleX()));
//		System.out.println("width = " + this.getWidth());
//		System.out.println("scaleY = " + (this.getWidth() * this.getScaleY()));
//		System.out.println("rotation = " + this.getRotation());
//		
//		System.out.println("----------------spriteframe-------------");
//		System.out.println("position(" + spriteFrame.getX() + ", " + spriteFrame.getY() + ")");
//		System.out.println("origin (" + spriteFrame.getOriginX() + ", " + spriteFrame.getOriginY() + ")");
	}
	
	
}
