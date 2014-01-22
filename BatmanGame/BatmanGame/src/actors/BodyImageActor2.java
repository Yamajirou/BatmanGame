/**
 * project:		DemoDraft
 * file:		BodyImageActor.java
 * author:		codejie(codejie@gmail.com)
 * update:		May 25, 2011
 */
package actors;

import java.util.ArrayList;

import variaveis.GLOBAL;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import data.GeneralUserData;

public class BodyImageActor2 {

	private Body body = null;
	private Image image = null;
//	private Stage stage = null;
	
	private MouseJoint mouseJoint = null;
	
	private boolean direction = true; //true == direita, false == esq
	
//	public BodyImageActor2(String name, TextureRegion texture, World world, BodyDef def, Shape shape, float density) {
//		image = new Image(texture);
//		image.setName(name);
//		body = world.createBody(def);
//		body.createFixture(shape, density);
//		body.setUserData(this);
//	}
//	
//	public BodyImageActor2(String name, TextureRegion texture, World world, BodyDef def, FixtureDef fixturedef) {
//		image = new Image(texture);
//		System.out.println("creating BodyImageActor: " + name);
//		image.setName(name);
//		body = world.createBody(def);
//		body.createFixture(fixturedef);	
//		body.setUserData(this);
//	}
	
	private float scale = 16;
	
	public BodyImageActor2(String name, TextureRegion texture, World world, BodyDef def, FixtureDef fixturedef, GeneralUserData data, Stage stage) {
		image = new Image(texture);
		System.out.println("creating BodyImageActor: " + name);
		image.setName(name);
		image.setBounds(1, 1, 1, 1);
		stage.addActor(image);
		

		body = world.createBody(def);
		body.createFixture(fixturedef);	
		body.setUserData(data);
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
	
	public Vector2 getBodyPosition(){
		return body.getPosition();
	}
	
	public float getX(){
		return image.getX();
	}
	
	public float getY(){
		return image.getY();
	}
	
	public void refreshImagePosition(){
		image.setPosition(body.getPosition().x - (image.getWidth()/2), body.getPosition().y - (image.getHeight()/2));
	}
	
	public Vector2 getLinearVelocity(){
		return body.getLinearVelocity();
	}
	
	public void applyLinearImpulse(float x, float y){
		body.applyLinearImpulse(x, y, body.getPosition().x, body.getPosition().y, true);
	}
	
	public void applyLinearImpulseX(float x){
		body.applyLinearImpulse(x, 0, body.getPosition().x, body.getPosition().y, true);
	}

	public void applyLinearImpulseY(float y){
		body.applyLinearImpulse(0, y, body.getPosition().x, body.getPosition().y, true);
	}
	
	public void applyForceToCenter(Vector2 force){
		body.applyForceToCenter(force, true);
	}

	public void applyForceToCenter(float forceX, float forceY){
		body.applyForceToCenter(forceX, forceY, true);
	}
	
	public void applyForceToCenter(float forceX){
		body.applyForceToCenter(forceX, 0, true);
	}
	
	public Vector2 getWorldCenter(){
		return body.getWorldCenter();
	}
	
	public boolean hasJoint(){
		return !(body.getJointList().isEmpty());
	}
	
	public ArrayList<JointEdge> getJointList(){
		return body.getJointList();
	}
	
	public boolean getDirection(){
		return direction;
	}
	
	public void setDirection(boolean dir){
		direction = dir;
	}
	
	public float getHeigth(){
		return image.getHeight();
	}
	
	public float getWidth(){
		return image.getWidth();
	}
	
	public Body getLinkedBody(){
		return body.getJointList().get(0).other;
	}
}