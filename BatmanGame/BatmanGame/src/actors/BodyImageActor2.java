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

public class BodyImageActor2 {

	private Body body = null;
	private Image image = null;
//	private Stage stage = null;
	
	private MouseJoint mouseJoint = null;
	
//	public BodyImageActor2(String name, TextureRegion texture, World world, BodyDef def, Shape shape, float density) {
//		image = new Image(texture);
//		image.setName(name);
//		body = world.createBody(def);
//		body.createFixture(shape, density);
//		body.setUserData(this);
//	}
//	
	public BodyImageActor2(String name, TextureRegion texture, World world, BodyDef def, FixtureDef fixturedef) {
		image = new Image(texture);
		System.out.println("creating BodyImageActor: " + name);
		image.setName(name);
		body = world.createBody(def);
		body.createFixture(fixturedef);	
		body.setUserData(this);
	}
	
	public BodyImageActor2(String name, TextureRegion texture, World world, BodyDef def, FixtureDef fixturedef, Stage stage) {
		image = new Image(texture);
		System.out.println("creating BodyImageActor: " + name);
		image.setName(name);
		stage.addActor(image);

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
	
	public Image getImage(){
		return image;
	}
	
	public boolean remove() {
		this.destroyBody();
		return image.remove();
	}
	
	public void applyForce(Vector2 force, Vector2 point) {
		if (body != null) {
			body.applyForce(force, point, true);//Bugs? should use World coordinate?
		}else{
			System.out.println("applyForce() - body == null");
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
	
	public void makeMouseJoint(BodyImageActor2 other, Vector2 target) {
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
			image.setX(body.getPosition().x * GLOBAL.WORLD_SCALE - image.getWidth() / 2);
			image.setY( body.getPosition().y * GLOBAL.WORLD_SCALE - image.getHeight() / 2);
		
			image.setRotation(MathUtils.radiansToDegrees * body.getAngle());
		}

		if(GLOBAL.DEBUG == false) {
			image.draw(batch, parentAlpha);
		}
	}
	
	public float getX(){
		return image.getX();
	}
	
	public float getY(){
		return image.getY();
	}
	
	public void setPosition(Vector2 pos){
		image.setPosition(pos.x, pos.y);
	}
	
	public void setPosition(float x, float y){
		image.setPosition(x, y);
	}
	
//	public void setBodyPosition(Vector2 pos){
//		body.set
//		
//	}
	
	public Vector2 getLinearVelocity(){
		return body.getLinearVelocity();
	}
	
	public void applyLinearImpulse(Vector2 force){
		body.applyLinearImpulse(force.x, 0, body.getPosition().x, body.getPosition().y, true);
		
	}
	
	public void applyLinearImpulseX(float x){
		body.applyLinearImpulse(x, 0, body.getPosition().x, body.getPosition().y, true);
		
	}
}