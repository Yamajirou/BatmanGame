/**
 * project:		DemoDraft
 * file:		BodyImageActor.java
 * author:		codejie(codejie@gmail.com)
 * update:		May 25, 2011
 */
package actors;

import variaveis.GLOBAL;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BodyImageActor extends Image {

	private Body body = null;
	
	private MouseJoint mouseJoint = null;
	
	public BodyImageActor(String name, TextureRegion texture, World world, BodyDef def, Shape shape, float density) {
		super(texture);
		setName(name);
		body = world.createBody(def);
		body.createFixture(shape, density);
		body.setUserData(this);
	}
	
	public BodyImageActor(String name, TextureRegion texture, World world, BodyDef def, FixtureDef fixturedef) {
		super(texture);
		System.out.println("creating BodyImageActor: " + name);
		setName(name);
		body = world.createBody(def);
		body.createFixture(fixturedef);	
		body.setUserData(this);
	}
	
	public BodyImageActor(String name, TextureRegion texture, World world, BodyDef def, FixtureDef fixturedef, Stage stage) {
		super(texture);
		System.out.println("creating BodyImageActor: " + name);
		setName(name);
		stage.addActor(this);
		//TODO - quando adiciona o actor antes de criar o body, ele cria a imagem, mas se adicionar o body ele destroi a imagem e fica com o body
		// se adicionar o actor dps de criar o body ele perde o body mas fica com a imagem... q merda!!
		body = world.createBody(def);
		body.createFixture(fixturedef);	
		body.setUserData(this);
	}
	
	public void destroyBody() {
		if (body != null) {
			World world = body.getWorld();
			world.destroyBody(body);
			body = null;
		}
	}
	
	public Body getBody() {
		return body;
	}
	
	public boolean remove() {
		this.destroyBody();
		return super.remove();
	}
	
	public void applyForce(Vector2 force, Vector2 point) {
		if (body != null) {
			body.applyForce(force, point, true);//Bugs? should use World coordinate?
		}
	}
	
	public float getBodyMass() {
		if (body != null) {
			return body.getMass();
		}
		else {
			return 0.0f;
		}
	}
	
	public boolean isBodyDestroy() {
		return ((body == null) ? true : false);
	}
	
	public void setBodyActive(boolean active) {
		if (body != null) {
			body.setActive(active);
		}
	}
	
	public boolean isBodyActive() {
		if (body != null) {
			return body.isActive();
		}
		return false;
	}
	
	public void makeMouseJoint(BodyImageActor other, Vector2 target) {
		if (body != null) {
			World world = body.getWorld();

			if (mouseJoint != null) {
				world.destroyJoint(mouseJoint);
			}
			MouseJointDef def = new MouseJointDef();
			def.bodyA = other.getBody();
			def.bodyB = body;
			def.target.set(target);
			def.maxForce = 10.0f * GLOBAL.WORLD_GRAVITY.y;
			
			mouseJoint = (MouseJoint)world.createJoint(def);
			
			body.setAwake(true);			
		}		
	}
	
	public void clearMouseJoint() {
		if (body != null) {
			if (mouseJoint != null) {
				body.getWorld().destroyJoint(mouseJoint);
				mouseJoint = null;
			}
		}
	}
	
	public void refreshMouseJoint(Vector2 target) {
		if (mouseJoint != null) {
			mouseJoint.setTarget(target);
		}
	}
	
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (body != null) {
			this.setX(body.getPosition().x * GLOBAL.WORLD_SCALE - this.getWidth() / 2);
			this.setY( body.getPosition().y * GLOBAL.WORLD_SCALE - this.getHeight() / 2);
		
			this.setRotation(MathUtils.radiansToDegrees * body.getAngle());
		}

		if(GLOBAL.DEBUG == false) {
			super.draw(batch, parentAlpha);
		}
	}
	
}