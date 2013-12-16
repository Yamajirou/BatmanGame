package stage;

import variaveis.GLOBAL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.me.box2dTests.data.SupportData;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Box2DStage {
	protected World world;
	protected Box2DDebugRenderer renderer;
	
	private Stage stage;
	Image image;
	TextureRegion texture;
	
	OrthographicCamera camera;
	
//	protected Body groundBody;
	
//	public Box2DStage(Camera camera){
//		this.camera = (OrthographicCamera) camera;
	public Box2DStage(){
		camera = new OrthographicCamera(48, 32);
		
		stage = new Stage(GLOBAL.SCREEN_WIDTH, GLOBAL.SCREEN_HEIGHT, true);
//		stage.setCamera(this.camera);
		
		texture = new TextureRegion(new Texture(Gdx.files.internal("data/Toad.png")));
		image = new Image(texture);
		image.setPosition(0, 0);
		stage.addActor(image);
		renderer = new Box2DDebugRenderer(true, true, true, true, true, true);
		
//		renderer.set
		// create the world
		world = new World(new Vector2(0, -10), true);
//		BodyDef bodyDef = new BodyDef();
//		groundBody = world.createBody(bodyDef);
		
		createStaticSquare(2, 2, 0, 0);
		
		// call abstract method to populate the world
//		createWorld(world);
	}
	
	public void render(float delta){
		world.step(delta, 3, 3);
		// clear the screen and setup the projection matrix
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				camera.update();

				// render the world using the debug renderer
				renderer.render(world, camera.combined);
//				renderer.
		stage.act(delta);
		stage.draw();
	}
	
	
	private void createStaticSquare(float width, float height, float x, float y){
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
	
	public void dispose(){
		world.dispose();
		renderer.dispose();
		stage.dispose();
		texture.getTexture().dispose();
	}
}
