package stage;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import tools.TiledMapHelper;
import variaveis.GLOBAL;
import actors.AnimatedActorBase;
import actors.AnimatedActorHero;
import actors.AnimatedSimpleActor;
import actors.BodyImageActor2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import data.GeneralUserData;
import data.HeroData;

public class ActorBox2DStageBase{
	
//	public Image hero;
	public BodyImageActor2 hero;
	
	private ArrayMap<String, Texture> textures;
	private ArrayMap<String, TextureRegion> texturesRegions;
	
//	private TextureAtlas atlas;
	private TextureRegion texRegion;
//	private TextureRegion cordaTex;
//	private Texture cordaAni;
	
	OrthographicCamera camera;
	Box2DDebugRenderer renderer;
	
	private World world;
	
	private Body hitBody;
	
	private Stage actorStage;
	
	private Image corda;
	private AnimatedSimpleActor fallingRope;
	private AnimatedActorBase fallingRope2;
//	private Texture ropeTexture;
	public AnimatedActorHero testHero;
//	private boolean canThrowRope = true;
	
//	private Array<BodyImageActor2> actors;
	
//	private TiledMap map;
	
	private float scale = 32;
	
	private TiledMapHelper tiledMapHelper;
	
	DecimalFormat df;
	
	public ActorBox2DStageBase() {
//		System.out.println("TESTING ---------------");
//		
//		int a[][];
//		a = new int[5][2];
//		System.out.println("a.lenght = " + a.length);
//		System.out.println("a[0].lenght = " + a[0].length);
//		
//		System.out.println("--------------- TESTING");
		
		df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.DOWN);
		
		Texture.setEnforcePotImages(false);
		actorStage = new Stage(GLOBAL.SCREEN_WIDTH, GLOBAL.SCREEN_HEIGHT, true);
		actorStage.setViewport(GLOBAL.SCREEN_WIDTH/scale, GLOBAL.SCREEN_HEIGHT/scale, true);
		camera = new OrthographicCamera(GLOBAL.SCREEN_WIDTH/scale, GLOBAL.SCREEN_HEIGHT/scale);
		camera.position.set(0, 0, 0);
//		camera.zoom = 1/scale;
		actorStage.setCamera(camera);
//		this.setViewport(GLOBAL.SCREEN_WIDTH/2, GLOBAL.SCREEN_HEIGHT/2, true);
		
//		atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages.atlas" ) );
		
		world = new World(GLOBAL.WORLD_GRAVITY, true);
		
//		actors = new Array<BodyImageActor2>();
		
//		this.camera = camera;
//		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        image2 = new TextureRegion(new Texture(Gdx.files.internal("data/badlogic.jpg")));
		textures.put("toad", new Texture(Gdx.files.internal("data/Toad.png")));
		textures.put("cordaAni", new Texture(Gdx.files.internal("data/cordaAni.png")));
		textures.put("corda1", new Texture(Gdx.files.internal("data/corda1.jpg")));
		
		texturesRegions.put("hero", new TextureRegion(textures.get("toad")));
		texturesRegions.put("corda1", new TextureRegion(textures.get("corda1")));
		
//		cordaAni = new Texture(Gdx.files.internal("data/cordaAni.png"));
//		cordaTex = new TextureRegion(new Texture(Gdx.files.internal("data/corda1.jpg")));
		
//		texRegion = new TextureRegion(textures.get("toad"));
//		Drawable drawable = new TextureRegionDrawable(texRegion);
//        ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
//        Gdx.input.setInputProcessor(this);

//        root = new Table();
//        ui.addActor(root);
//        root.debug();

		// create the debug renderer
		renderer = new Box2DDebugRenderer(true, true, true, true, true, true);
//		camera = new OrthographicCamera(48, 32);
//		renderer.
		
		
//		hero = new Image(drawable);
//        hero = new Image(texRegion);
		
//        image.setScaling(Scaling.fill);
//        root.add(image).width(160).height(100);
        
//        this.addActor(hero);
		createHero(0, 20);
//		createStaticSquare(200, 2, 10, 10);
//		createStaticSquare(2, 2, 10, 20);
		
		
//		cordaAni.
//		fallingRope = new AnimatedImageActor(cordaAni, 1, 3, 1);
//		fallingRope = new AnimatedSimpleActor("data/cordaAni.png", 3, 1, 3, 1);
//		fallingRope.setBounds(1, 1, 0.1f, 0.1f);
//		fallingRope.setScale(0.1f, 0.1f);

		int cols = 1;
		int rows = 3;
//		fallingRope2 = new AnimatedActorBase(1, TextureRegion.split(ropeTexture, ropeTexture.getWidth()/cols, ropeTexture.getHeight()/rows));
//		fallingRope2.setBounds(1, 1, 0.1f, 0.1f);
//		fallingRope2.setScale(0.1f, 0.1f);
//		fallingRope2.setPlayMode(Animation.LOOP);
		
		testHero = new AnimatedActorHero(1f, textures.get("cordaAni"), cols, rows);
		testHero.setBounds(1, 1, 0.1f, 0.1f);
		testHero.setScale(0.1f, 0.1f);
		testHero.setPlayMode(Animation.LOOP);
		testHero.setStandByTextureRight(0);
		
//		fallingRope.setRotation(-45);
//		fallingRope.setPlayMode(Animation.NORMAL);
//		actorStage.addActor(fallingRope);
//		actorStage.addActor(fallingRope2);
		actorStage.addActor(testHero);
//		fallingRope.setVisible(false);
		createTiledMapHelper();
		
		createContactListener(world);
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

	public void applyLinearImpulseY(float y){
//		if(Math.abs(hero.getLinearVelocity().x) <= GLOBAL.MAX_VELOCITY){
			//check if hero is on the ground
			hero.applyLinearImpulseY(y);
//		}
	}
	
	public void applyForceToCenterX(float x){
		if(Math.abs(hero.getLinearVelocity().x) <= GLOBAL.MAX_VELOCITY){
			hero.applyForceToCenter(x);
		}
	}
	
	
	private void createTiledMapHelper(){
//		map = new TmxMapLoader().load("maps/map2.tmx");
		float scale = 16; //1/16f
//		tiledMapHelper = new TiledMapHelper(actorStage.getCamera(), "maps/packer", "maps/map2.tmx", world);
		tiledMapHelper = new TiledMapHelper(camera, "maps/packer", "maps/map2.tmx", world, scale);
		
//		tiledMapHelper = new TiledMapHelper();
//
//		tiledMapHelper.setPackerDirectory("maps/packer");
//
//		tiledMapHelper.loadMap("maps/map2.tmx");

//		tiledMapHelper.prepareCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
//		tiledMapHelper.loadCollisions("data/collisions.txt", world,
//				PIXELS_PER_METER);
//		tiledMapHelper.loadCollisions(world);
	}
	
	private void createHero(float x, float y){
		System.out.println("ActorStage.createHero()");
		PolygonShape shape3 = new PolygonShape();
		shape3.setAsBox(0.5f, 0.5f);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape3;
		fd.density = 1f;
		fd.friction = 1f;
//		fd3.friction = 100f;
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
//		bd.type = BodyType.StaticBody;
//		bd.position.set(0f, 20);
		bd.position.set(x, y);
		bd.fixedRotation = true;
		
		hero = new BodyImageActor2("hero", texturesRegions.get("hero"), world, bd, fd, new HeroData(), actorStage);
//		this.addActor(hero);
		
		
		
		if(hero.getBody() == null){
			System.out.println("body null");
		}else{
			System.out.println("createHero() - hero.getBody != null");
		}
		
		shape3.dispose();
	}
	
	public void createActor(String name, TextureRegion texture, BodyDef bd, FixtureDef fd){
		
	}
	
	public void addActor(BodyImageActor2 actor){
		addActor(actor.getImage());
		
	}
	
	private void addActor(Actor actor) {
		actorStage.addActor(actor);
		System.out.println("Adding actor: " + actor);
	}
	
//	private void addBody(Body body){
//		world.create
//	}
	
	public void addObject(){
		
	}
		
	
	
	public void act(float delta) {
//		System.out.println("ActorStage.act()");
		world.step(delta, 3, 3);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//		hero.setPosition(hero.getBody().getPosition());
//		camera.
		
//		this.getCamera().position.set(hero.getX(), hero.getY(), 0);
//		this.getCamera().update();
//		camera.update();
//		renderer.render(world, actorStage.getCamera().combined);
		tiledMapHelper.render();
		actorStage.act(delta);
		actorStage.draw();
		hero.refreshImagePosition();
		if(hero.getLinearVelocity().x > 0){
			hero.setDirection(true);
		}else{
			if(hero.getLinearVelocity().x < 0){
				hero.setDirection(false);
			}
		}
//		fallingRope.
//		corda.
//		moveCamera();
		moveCameraFollowHero();
		
//		if(fallingRope != null)
//			print = "rope = " + fallingRope.isAnimationFinished(delta);
		
//		camera.position.set(new Vector3(hero.getX(), hero.getY(), 0));
//		camera.update();
//		this.getCamera().update();
//		this.draw();
//		this.getCamera().
//		renderer.
	}
	
	public void moveCameraFollowHero(){
		actorStage.getCamera().position.set(hero.getX(), hero.getY(), 0);
		actorStage.getCamera().update();
	}
	
	public void moveCamera(){
		if(hero.getX() > GLOBAL.SCREEN_WIDTH/2 ){
			actorStage.getCamera().position.set(hero.getX(), actorStage.getCamera().position.y, 0);
		}
		if(hero.getY() > GLOBAL.SCREEN_HEIGHT/2){
			actorStage.getCamera().position.set(actorStage.getCamera().position.x, hero.getY(), 0);
		}
		actorStage.getCamera().update();
	}
	
	public void draw(){
		actorStage.draw();
	}
	
	protected void createContactListener(World world){
		System.out.println("Override this you noob!!");
	}
	
	
	public void dispose() {
		for(int i = 0; i < textures.size; i++){
			textures.values[i].dispose();
			textures.removeIndex(i);
		}
		
//		texRegion.getTexture().dispose();
		actorStage.dispose();
		world.dispose();
//		cordaAni.dispose();
//		ropeTexture.dispose();
//		ui.dispose();
//        skin.dispose();
	}
	
}
