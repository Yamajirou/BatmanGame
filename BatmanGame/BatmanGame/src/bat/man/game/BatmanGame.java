package bat.man.game;

import screen.SplashScreen;
import screen.TestScreen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class BatmanGame extends Game {
	
	// constant useful for logging
    public static final String LOG = BatmanGame.class.getSimpleName();

    // whether we are in development mode
    public static final boolean DEV_MODE = false;
	
//	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	@Override
	public void create() {	
//		camera = new OrthographicCamera(48, 32);
//		camera.
//		camera.position.set(0, 15, 0);
//		camera.setToOrtho(true, 48, 32);
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
//		
//		camera = new OrthographicCamera(1, h/w);
//		batch = new SpriteBatch();
//		
//		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		
//		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
//		
//		sprite = new Sprite(region);
//		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
//		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
//		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		setScreen(new TestScreen());
//		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
//		batch.dispose();
//		texture.dispose();
		super.dispose();
		System.out.println("BatmanGame: disposing game");
	}

	@Override
	public void render() {	
		super.render();
		
//		Gdx.gl.glClearColor(1, 1, 1, 1);
//		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//		
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		sprite.draw(batch);
//		batch.end();
		
	}
	
	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
        System.out.println("Setting screen: " + screen.getClass().getSimpleName());
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
