package stage;

import tools.TiledMapHelper;
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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import data.GeneralUserData;
import data.HeroData;

public class ActorBox2DStage{
	
//	public Image hero;
	public BodyImageActor2 hero;
//	private TextureAtlas atlas;
	private TextureRegion texRegion;
	
	OrthographicCamera camera;
	Box2DDebugRenderer renderer;
	
	private World world;
	
	private Body hitBody;
	
	private Stage actorStage;
	
	private Array<BodyImageActor2> actors;
	
//	private TiledMap map;
	
	private float scale = 16;
	
	private TiledMapHelper tiledMapHelper;
	
	public ActorBox2DStage() {
		actorStage = new Stage(GLOBAL.SCREEN_WIDTH, GLOBAL.SCREEN_HEIGHT, true);
		actorStage.setViewport(GLOBAL.SCREEN_WIDTH/scale, GLOBAL.SCREEN_HEIGHT/scale, true);
		camera = new OrthographicCamera(GLOBAL.SCREEN_WIDTH/scale, GLOBAL.SCREEN_HEIGHT/scale);
		camera.position.set(0, 0, 0);
//		camera.zoom = 1/scale;
		actorStage.setCamera(camera);
//		this.setViewport(GLOBAL.SCREEN_WIDTH/2, GLOBAL.SCREEN_HEIGHT/2, true);
		
//		atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages.atlas" ) );
		
		world = new World(GLOBAL.WORLD_GRAVITY, true);
		
		actors = new Array<BodyImageActor2>();
		
//		this.camera = camera;
//		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        image2 = new TextureRegion(new Texture(Gdx.files.internal("data/badlogic.jpg")));
		texRegion = new TextureRegion(new Texture(Gdx.files.internal("data/Toad.png")));
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
		shape3.setAsBox(1f, 1f);
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
		
		hero = new BodyImageActor2("hero", texRegion, world, bd, fd, new HeroData(), actorStage);
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
		
	public void dispose() {
		texRegion.getTexture().dispose();
		actorStage.dispose();
		world.dispose();
//		ui.dispose();
//        skin.dispose();
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
		renderer.render(world, actorStage.getCamera().combined);
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
		
//		moveCamera();
		moveCameraFollowHero();
		
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
	
	public Array<BodyImageActor2> getActors(){
		return actors;
	}
	
	public void draw(){
		actorStage.draw();
	}
	
	private void createContactListener(World world){
		world.setContactListener(new ContactListener() {
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}
			@Override
			public void endContact(Contact contact) {}
			@Override
			public void beginContact(Contact contact) {
//				Gdx.app.log("ContactListener", "beginContact()");
				
				Body obj1 = contact.getFixtureA().getBody();
				Body obj2 = contact.getFixtureB().getBody();
				
//				Gdx.app.log("Obj1", obj1.toString());
//				Gdx.app.log("Obj2", obj2.toString());
				
//				Gdx.app.log("ContactListener", "Obj1.class = " + obj1.getClass().getName());
//				Gdx.app.log("ContactListener", "Obj2.class = " + obj2.getClass().getName());
				
				GeneralUserData data1 = (GeneralUserData)obj1.getUserData();
				GeneralUserData data2 = (GeneralUserData)obj2.getUserData();
				
				if(data1 == null || data2 == null){
					if(data1 != null)
						Gdx.app.log("data1", data1.nome);
					else
						Gdx.app.log("data1", "null");
					if(data2 != null)
						Gdx.app.log("data2", data2.nome);
					else
						Gdx.app.log("data2", "null");
					return;
				}
				
//				Gdx.app.log("data1", "" + data1.nome);
//				Gdx.app.log("data2", "" + data2.nome);
				
				Vector2 normal = contact.getWorldManifold().getNormal();
				
				if(data1.type == GeneralUserData.GALHO || data2.type == GeneralUserData.GALHO){
					System.out.println("galhoooo");
//					Gdx.app.log("obj1.data", "galho");
//					Gdx.app.log("world.isLocked()" , "" + obj1.getWorld().isLocked());
//					createRope(obj1, normal);
//					throwRope();
				}
				
				if(data1.type == GeneralUserData.GROUND || data2.type == GeneralUserData.GROUND){
//					jumping = false;
					System.out.println("GRound!!!");
				}
				if(data1.type == GeneralUserData.PLATAFORMA || data2.type == GeneralUserData.PLATAFORMA){
//					jumping = false;
					System.out.println("Plataforma");
				}
				if(data1.type == GeneralUserData.GAMEOVER || data2.type == GeneralUserData.GAMEOVER ){
					System.out.println("Gameover!!!!");
					//restart
//					hero.getPosition().set(2, 20);
//					hero.localPoint2.set(2, 20);
//					hero.getWorldCenter().set(2,20);
//					hero.setTransform(2, 20, hero.getAngle());
//					hero.
					
					
//					recreateHero();
				}
				
				
//				if(data1.type == GeneralUserData.BULLET && data2.type == GeneralUserData.GROUND){
////					Gdx.app.log("obj1.type", "bullet");
////					obj1.getWorld().destroyBody(obj1);
//					garbage.add(obj1);
//				}
//				if(data2.type == GeneralUserData.BULLET && data1.type == GeneralUserData.GROUND){
////					Gdx.app.log("obj1.type", "ground");
////					obj2.getWorld().destroyBody(obj2);
//					garbage.add(obj2);
//				}
				
				
				
//				if(obj1.getType() == BodyType.StaticBody){
////					Gdx.app.log("obj1", "static");
////					createRope(obj1, normal);
//				}else{
//					if(obj2.getType() == BodyType.StaticBody){
////						Gdx.app.log("obj2", "static");
////						createRope(obj2, normal);
//					}
//				}
			}
		});
	}
	
	private void createRope(Body where, Vector2 joint){
		DistanceJointDef dj = new DistanceJointDef();
		dj.initialize(hero.getBody(), where, hero.getWorldCenter(), new Vector2(joint.x,joint.y));
		world.createJoint(dj);
	}
	
	public void throwRope(){
		if(hero.hasJoint())
			while(hero.hasJoint()){
				world.destroyJoint(hero.getJointList().remove(0).joint);
			}
		else{
			Gdx.app.log("checkRopePlace()", "" + checkRopePlace());
		}
	}
	
	QueryCallback callback = new QueryCallback() {
		@Override
		public boolean reportFixture (Fixture fixture) {
			// if the hit point is inside the fixture of the body
			// we report it
			Gdx.app.log("reportFixture()", ""+fixture.getBody().getWorldCenter());
//			float x, y;
//			x = hero.getPosition().x + 2f;
//			y = hero.getPosition().y + 4;
//			for(int i = 0; i < 8; i++){
//				Gdx.app.log("hero.Pos.x", ""+x);
//				Gdx.app.log("hero.Pos.y", ""+y);
//				if (fixture.testPoint(x, y)) {
//					Gdx.app.log("reportFixture() - if", "hitBody");
//					hitBody = fixture.getBody();
//					return true;
//				}
//				x += 0.5f;
//				y += 0.5f;
//			}
//			return false;
			
			
			// outra ideia
//			if(((GeneralUserData)fixture.getBody().getUserData()).type == GeneralUserData.GALHO){
			if(((GeneralUserData)fixture.getBody().getUserData()).support){
				hitBody = fixture.getBody();
				return true;
			}
			return false;
		}
	};
	
	private boolean checkRopePlace(){
		hitBody = null;
		float direction = hero.getDirection() ?  1 : -1;
		float distance = 1;
		world.QueryAABB(callback, hero.getBodyPosition().x + (2f * distance * direction), hero.getBodyPosition().y + (4 * distance), hero.getBodyPosition().x + (6f * direction), hero.getBodyPosition().y + (8 * distance));
		if(hitBody == null)
			return false;
		createRope(hitBody, hitBody.getWorldCenter());
		return true;
	}
	
}
