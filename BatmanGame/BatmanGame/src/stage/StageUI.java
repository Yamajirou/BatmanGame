package stage;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import tools.PrintTimer;
import variaveis.GLOBAL;
import actors.AnimatedActorHero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class StageUI extends Stage{
	ActorBox2DStage mainStage;
	PrintTimer pt;
	int counter = 0;
	
//	usar Button ------  Table || Window?
	Image botao; //maximo de 3 botoes na dir, 2 skills e 1 pra lancar e soltar a corda
	
	Button but1;
	Button but2;
	Button but3;
	Button but4;
	
	TextField textBody;
	TextField textImage;
	TextField textDebug;
	DecimalFormat df;
	
	Skin uiSkin;
	
	Skin touchpadSkin;
	private TouchpadStyle touchpadStyle;
	private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed = 2;
	
	private TextureAtlas atlas;
	private TextureRegion texRegion;
	private TextureRegion texRegion2;
	
	Touchpad touchpad;
	
	
	public StageUI(ActorBox2DStage stage){
//		super(GLOBAL.SCREEN_WIDTH, GLOBAL.SCREEN_HEIGHT, true);
		Texture.setEnforcePotImages(false);
		
		df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.DOWN);
		
		this.mainStage = stage;
		if(mainStage.hero !=null){
			System.out.println("mainStage.hero != null");
//			System.out.println("hero.getName() = " + mainStage.hero.getName());
			System.out.println("hero.getBody = " + mainStage.hero.getBody());
//			System.out.println("hero." + mainStage.hero.);
			if(stage.hero.getBody() == null){
				System.out.println("StageUI() - hero.getBody() == null");
			}
		}
		
		pt = new PrintTimer();
		
		
		texRegion = new TextureRegion(new Texture(Gdx.files.internal("data/button.jpg")));
		Drawable drawable = new TextureRegionDrawable(texRegion);

		texRegion2 = new TextureRegion(new Texture(Gdx.files.internal("data/button.jpg")));
		Drawable drawable2 = new TextureRegionDrawable(texRegion2);
		
		uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		textBody = new TextField("", uiSkin);
		textImage = new TextField("", uiSkin);
		textDebug = new TextField("", uiSkin);
//		text.set
		
		but1 = new Button(drawable);
		but2 = new Button(drawable2);
		but3 = new Button(drawable2);
		but4 = new Button(drawable2);
		
		but1.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("jump");
				mainStage.applyLinearImpulseY(blockSpeed*5);
			}
		});
		but2.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("but2");
				mainStage.throwRope();
//				mainStage.testHero.setStandBy(AnimatedActorHero.STANDBY_RIGHT);
//				mainStage.hero.getBody().setTransform(mainStage.hero.getX() + 1, mainStage.hero.getY() + 1, 0);
			}
		});
		but3.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("but3");
				mainStage.hero.getBody().setTransform(0, 20, 0);
			}
		});
		
		
		
//		botao = new Image(drawable);
		
//		botao.addListener(new InputListener(){
//			@Override
//			public boolean touchDown(InputEvent event, float x, float y,
//					int pointer, int button) {
//				System.out.println("ImageListe.touchDown()");
////				System.out.println("listenerActor.name = " + event.getListenerActor().getName());
////				System.out.println("getTarget.getName = " + event.getTarget().getName());
////				event.
//				
//				
//				event.stop();
//				return super.touchDown(event, x, y, pointer, button);
//			}
//		});
//		this.addActor(botao);
//		botao.setPosition(10, 200);
		
//		this.addListener(new Liste());
		
//		but1.setPosition(10, 200);
		
		//buttons
		Table table = new Table(uiSkin);
		table.debug();
		table.setFillParent(true);
		
		//touchpad
		Table table2 = new Table(uiSkin);
		table2.debug();
		table2.setFillParent(true);
		
		//debugger - textfield
		Table table3 = new Table(uiSkin);
		table3.debug();
		table3.setFillParent(true);
		
//		table.setSize(100, 100);
//		table.setBounds(10, 10, 200, 200);
//		table.
//		table.set
//		table.setPosition(10, 10);
//		table.row();
		
//		touchpad = new Touchpad(20, uiSkin);
//		touchpad.setBounds(15, 15, 100, 100);
//		table.addActor(touchpad);
		
		createTouchPad();
//		table2.scale(0,10);
//		table2.setScale(10, 10);
//		table2.setSize(40, 40);
		table2.left().bottom().padBottom(10).padLeft(10);
		table2.add(touchpad).left();
		
		
		table.right().bottom().padBottom(10).padRight(10);
//		table.
//		table.add("primeiro");
//		table.add("segundo");
//		table.row();
//		table.add("terceiro");
//		table.add("quarto");
		
		table.add(but1).space(10);
		table.row();
		table.add(but2).space(10);
		table.row();
		table.add(but3);
		
//		table.;
		
//		table.add(but3).expandX().right().padRight(10);
//		table.row().colspan(2);
//		table.add(but4);
		
//		table.add(but1).spaceLeft(10);
//		table.
		
//		Table table2 = new Table(uiSkin);
//		table2.debug();
//		table2.setFillParent(true);
////		table2.setBounds(10, 10, 200, 200);
//		table2.right().bottom().padRight(10).padBottom(10);
//		table2.add(but3);
		
		table3.left().top().pad(10);
		table3.add(textBody);
		table3.add(textImage);
		table3.add(textDebug);
		
		this.addActor(table);
		this.addActor(table2);
		this.addActor(table3);
		
	}
	
	private void createTouchPad(){
		//Create a touchpad skin    
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground100x100.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob36x36.png"));
        
        //Create TouchPad Style
        touchpadStyle = new TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(5, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);
        
		
//        touchpad.setScale(30);
//        touchpad.setSize(40, 40);
        
        //Create block sprite
//        blockTexture = new Texture(Gdx.files.internal("data/block.png"));
//        blockSprite = new Sprite(blockTexture);
//        //Set position to centre of the screen
//        blockSprite.setPosition(Gdx.graphics.getWidth()/2-blockSprite.getWidth()/2, Gdx.graphics.getHeight()/2-blockSprite.getHeight()/2);
        
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
//		mainStage.act(delta);
//		String s = "Actors:\n";
//		System.out.println(mainStage.getActors().items.length);
//		for(Actor a : mainStage.getActors().items){
//			s = s + a + "\n";
//			//System.out.println(s);
//		}
		
		
		if(mainStage.hero == null)
			return;
		if(mainStage.hero.getBody() == null){
			System.out.println("null");
		}else{
//			float x = mainStage.hero.getX() + touchpad.getKnobPercentX() * blockSpeed;
//			float y = mainStage.hero.getY() + touchpad.getKnobPercentY() * blockSpeed;
			float forceX = touchpad.getKnobPercentX() * blockSpeed;
			if(touchpad.getKnobPercentX() == 0){
//				System.out.println("standBy == true");
				mainStage.hero.getAnimatedActor().setStandBy(true);
			}
			else{
//				System.out.println("standBy == false");
				mainStage.hero.getAnimatedActor().setStandBy(false);
				if(touchpad.getKnobPercentX() > 0){
					mainStage.hero.setDirection(AnimatedActorHero.RIGHT);
				}else{
					mainStage.hero.setDirection(AnimatedActorHero.LEFT);
				}
			}
//			float forceY = touchpad.getKnobPercentY() * blockSpeed;
//			mainStage.hero.applyForce(new Vector2(x,y), mainStage.hero.getBody().getWorldCenter());
//			System.out.println("forceX = " + forceX);
//			System.out.println("forceY = " + forceY);
//			mainStage.applyForceOnHero(new Vector2(forceX, forceY));
			if(!mainStage.hero.hasJoint()){
				if(forceX == 0){
					if(mainStage.hero.getLinearVelocity().x != 0){
		//				forceX = mainStage.hero.getLinearVelocity().x / 2; 
	//					forceX = blockSpeed / (mainStage.hero.getLinearVelocity().x);
						forceX = blockSpeed * (mainStage.hero.getLinearVelocity().x / GLOBAL.MAX_VELOCITY) * 3;
		//				Gdx.app.log("break", ""+force);
						//hero.applyLinearImpulse(new Vector2(-force, 0f), hero.getWorldCenter(), false);
						forceX = -forceX;
					}
				}
			}
//			System.out.println("applying force: " + forceX);
			mainStage.applyLinearImpulseX(forceX);
//			mainStage.applyForceToCenterX(forceX);
//			mainStage.hero.setPosition(x, y);
//			mainStage.moveHero(x, y);
//			mainStage.act(delta);
			textBody.setText("position: " + (df.format(mainStage.hero.getBody().getPosition().x)) + ", " + (df.format(mainStage.hero.getBody().getPosition().y)));
//			textImage.setText("velocity: " + (df.format(mainStage.hero.getLinearVelocity().x)));
			textImage.setText(mainStage.toPrint2());
			textDebug.setText(mainStage.toPrint());
		}
		//Move blockSprite with TouchPad
//        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX()*blockSpeed);
//        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY()*blockSpeed);
        
//        mainStage.hero.
        
		//pt.print("StageUI - " + s, 1);
//		System.out.println("StageUI - acting");
		
//		batch.begin();
		//font.draw(batch, "fps:" + Gdx.graphics.getFramesPerSecond() + ", update: " + updateTime + ", render: " + renderTime, 0, 20);
		
//		batch.end();
	}
	
	private class Liste extends InputListener{
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			System.out.println("StageUI - touchDown");
			System.out.println("button = " + button);
			System.out.println("pointer = " + pointer);
			System.out.println("listenerActor.name = " + event.getListenerActor().getName());
			System.out.println("getTarget.getName = " + event.getTarget().getName());
//			event.
//			this.
//			mainStage.hero.setPosition(mainStage.hero.getX()+1, mainStage.hero.getY()+1);
			
			return super.touchDown(event, x, y, pointer, button);
		}
		
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer) {
			// TODO Auto-generated method stub
//			event.getTarget().getName()
			super.touchDragged(event, x, y, pointer);
		}
		
	}
	
	@Override
	public void dispose() {
		texRegion.getTexture().dispose();
		texRegion2.getTexture().dispose();
		uiSkin.dispose();
		touchpadSkin.dispose();
		super.dispose();
	}
}
