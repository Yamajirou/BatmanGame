package screen;

import stage.ActorBox2DStage;
import stage.ActorStage;
import stage.Box2DStage;
import stage.StageUI;
import stage.StageUITest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import bat.man.game.BatmanGame;

public class TestScreen implements Screen{
	
	
	private ActorBox2DStage actorStage;
	private StageUI ui;
	
	private Box2DStage box2dStage;
	private StageUITest uiTest;
	
	private OrthographicCamera camera;
	
	public TestScreen() {
//		this.camera = camera;
//		actorStage.
//		actorStage.addListener(new InputListener(){
//			
//			@Override
//			public boolean touchDown(InputEvent event, float x, float y,
//					int pointer, int button) {
//				System.out.println("StageUI - touchDown");
//				actorStage.hero.setPosition(actorStage.hero.getX()+1, actorStage.hero.getY()+1);
//				
//				return super.touchDown(event, x, y, pointer, button);
//			}
//		});
		
//		camera = new OrthographicCamera();
//		camera = new OrthographicCamera(48, 32);
//		camera.position.set(0, 15, 0);

		actorStage = new ActorBox2DStage();
		ui = new StageUI(actorStage);
		Gdx.input.setInputProcessor(ui);
		
		
//		ui = new StageUI();
		
		
//		box2dStage = new Box2DStage(this.camera);
//		box2dStage = new Box2DStage();
//		uiTest = new StageUITest(box2dStage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		
//		camera.position.set(actorStage.hero.getX(), actorStage.hero.getY(), 0);
//		camera.update();
		
        
//        uiTest.act();
//        box2dStage.render(delta);
//        uiTest.draw();
//        Table.drawDebug(uiTest);

        ui.act(delta);
        actorStage.act(delta);
        // draw the actors
        ui.draw();
        actorStage.draw();
        Table.drawDebug(ui);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
//		actorStage.
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		actorStage.dispose();
		ui.dispose();
		
//		box2dStage.dispose();
//		uiTest.dispose();
	}

}
