package stage;

import variaveis.GLOBAL;
import actors.BodyImageActor;
import actors.BodyImageActor2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import data.HeroData;

public class ActorStage extends Stage{
	
//	public Image hero;
	public BodyImageActor2 hero;
	private TextureAtlas atlas;
	private TextureRegion texRegion;
	
//	OrthographicCamera camera = null;
	Box2DDebugRenderer renderer;
	
	private World world;
	
	public ActorStage() {
		super(GLOBAL.SCREEN_WIDTH, GLOBAL.SCREEN_HEIGHT, true);
//		this.setViewport(GLOBAL.SCREEN_WIDTH/2, GLOBAL.SCREEN_HEIGHT/2, true);
		
//		atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages.atlas" ) );
		
		world = new World(new Vector2(0, -10), true);
		
//		this.camera = camera;
//		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        image2 = new TextureRegion(new Texture(Gdx.files.internal("data/badlogic.jpg")));
		texRegion = new TextureRegion(new Texture(Gdx.files.internal("data/Toad.png")));
		Drawable drawable = new TextureRegionDrawable(texRegion);
//        ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
//        Gdx.input.setInputProcessor(this);

//        root = new Table();
//        ui.addActor(root);
//        root.debug();

		// create the debug renderer
		renderer = new Box2DDebugRenderer(true, true, true, true, true, true);
//		renderer.
		
		
//		hero = new Image(drawable);
//        hero = new Image(texRegion);
		
//        image.setScaling(Scaling.fill);
//        root.add(image).width(160).height(100);
        
//        this.addActor(hero);
		createHero(10, 120);
		createStaticSquare(200, 2, 10, 10);
		createStaticSquare(2, 2, 10, 20);
	}
	
	public void applyForceOnHero(Vector2 force){
		if(hero.getBody() == null){
			System.out.println("null");
		}else{
			hero.applyForce(force, hero.getBody().getWorldCenter());
		}
//		hero.getBody().ap
	}
	
	public void applyLinearImpulse(float x, float y){
		if(hero.getLinearVelocity().x <= GLOBAL.MAX_VELOCITY){
			hero.applyLinearImpulse(x, y);
		}
	}
	
	public void applyLinearImpulseX(float x){
		if(Math.abs(hero.getLinearVelocity().x) <= GLOBAL.MAX_VELOCITY){
			hero.applyLinearImpulseX(x);
		}
	}
	
//	public void moveHero(Vector2 pos){
//		if(hero != null){
//			hero.setPosition(pos);
//		}
//	}
//	public void moveHero(float x, float y){
//		if(hero != null){
//			hero.setPosition(x, y);
//		}
//	}
	
	private void createHero(float x, float y){
		System.out.println("ActorStage.createHero()");
		PolygonShape shape3 = new PolygonShape();
		shape3.setAsBox(1f, 2f);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape3;
		fd.density = 1.0f;
		fd.friction = 0.2f;
//		fd3.friction = 100f;
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
//		bd.type = BodyType.StaticBody;
//		bd.position.set(0f, 20);
		bd.position.set(x, y);
		
		hero = new BodyImageActor2("hero", texRegion, world, bd, fd, new HeroData(), this);
//		this.addActor(hero);
		
		
		
		if(hero.getBody() == null){
			System.out.println("body null");
		}else{
			System.out.println("createHero() - hero.getBody != null");
		}
		
		shape3.dispose();
	}
	
	private void createStaticSquare(float width, float height, float x, float y){
		System.out.println("createStaticSquare()");
		PolygonShape shape3 = new PolygonShape();
		shape3.setAsBox(width, height);
		FixtureDef fd3 = new FixtureDef();
		fd3.shape = shape3;
		fd3.density = 1.0f;
		fd3.friction = 0.2f;
		
		BodyDef bd3 = new BodyDef();
		bd3.type = BodyType.StaticBody;
		bd3.position.set(x, y);
		Body plataforma = world.createBody(bd3);
		
		plataforma.createFixture(fd3);
//		plataforma.setUserData(new SupportData());
		
		shape3.dispose();
	}
	
	@Override
	public void addActor(Actor actor) {
		super.addActor(actor);
		System.out.println("Adding actor: " + actor);
	}
	
	public void addObject(){
		
	}
		
	@Override
	public void dispose() {
		super.dispose();
		
//		ui.dispose();
//        skin.dispose();
        texRegion.getTexture().dispose();
	}
	
	@Override
	public void act(float delta) {
//		System.out.println("ActorStage.act()");
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		super.act(delta);
//		hero.setPosition(hero.getBody().getPosition());
		hero.refreshImagePosition();
//		camera.
		world.step(delta, 3, 3);
		
//		this.getCamera().position.set(hero.getX(), hero.getY(), 0);
//		this.getCamera().update();
		
		moveCamera();
		
//		camera.position.set(new Vector3(hero.getX(), hero.getY(), 0));
//		camera.update();
//		this.getCamera().update();
		renderer.render(world, this.getCamera().combined);
//		this.draw();
//		this.getCamera().
//		renderer.
	}
	
	public void moveCamera(){
		if(hero.getX() > GLOBAL.SCREEN_WIDTH/2 ){
			this.getCamera().position.set(hero.getX(), this.getCamera().position.y, 0);
		}
		if(hero.getY() > GLOBAL.SCREEN_HEIGHT/2){
			this.getCamera().position.set(this.getCamera().position.x, hero.getY(), 0);
		}
		
		this.getCamera().update();
	}
}
