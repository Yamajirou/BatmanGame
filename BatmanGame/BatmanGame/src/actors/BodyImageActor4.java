/**
 * project:		DemoDraft
 * file:		BodyImageActor.java
 * author:		codejie(codejie@gmail.com)
 * update:		May 25, 2011
 */
package actors;

import java.util.ArrayList;

import variaveis.GLOBAL;

import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.Array;

import data.GeneralUserData;

public class BodyImageActor4 {

	private Body body = null;
	private AnimatedActorHero4Directions animatedActor = null;
	private float defaultFrameDuration = 0.25f;
//	private Stage stage = null;
	
	public BodyImageActor4(World world, BodyDef def, FixtureDef fixturedef, GeneralUserData data, Stage stage, Texture texture, int rows, int cols, float frameDuration) {
		animatedActor = new AnimatedActorHero4Directions(frameDuration, texture, cols, rows);
		this.createActor(stage, world, def, fixturedef, data);
	}
	
	public BodyImageActor4(World world, BodyDef def, FixtureDef fixturedef, GeneralUserData data, Stage stage, Texture texture, int rows, int cols, int frameQtd, int directions, float frameDuration) {
		animatedActor = new AnimatedActorHero4Directions(frameDuration, texture, cols, rows, frameQtd, directions);
		this.createActor(stage, world, def, fixturedef, data);
	}
	
	public BodyImageActor4(World world, BodyDef def, FixtureDef fixturedef, GeneralUserData data, Stage stage, Texture texture, int rows, int cols, int frameQtd, int directions) {
		animatedActor = new AnimatedActorHero4Directions(defaultFrameDuration, texture, cols, rows, frameQtd, directions);
		this.createActor(stage, world, def, fixturedef, data);
	}
	
	public BodyImageActor4(World world, BodyDef def, FixtureDef fixturedef, GeneralUserData data, Stage stage, Texture texture, int rows, int cols) {
		animatedActor = new AnimatedActorHero4Directions(defaultFrameDuration, texture, cols, rows);
		this.createActor(stage, world, def, fixturedef, data);
	}
	
	public BodyImageActor4(World world, BodyDef def, FixtureDef fixturedef, GeneralUserData data, Stage stage, Array<? extends TextureRegion> keyFrames) {
		animatedActor = new AnimatedActorHero4Directions(defaultFrameDuration, keyFrames);
		this.createActor(stage, world, def, fixturedef, data);
	}
	
	public BodyImageActor4(World world, BodyDef def, FixtureDef fixturedef, GeneralUserData data, Stage stage, TextureRegion... textures) {
		animatedActor = new AnimatedActorHero4Directions(defaultFrameDuration, textures);
		this.createActor(stage, world, def, fixturedef, data);
	}
	
	public BodyImageActor4(World world, BodyDef def, FixtureDef fixturedef, GeneralUserData data, Stage stage, TextureRegion [][] textures) {
		animatedActor = new AnimatedActorHero4Directions(defaultFrameDuration, textures);
		this.createActor(stage, world, def, fixturedef, data);
	}
	
	private void createActor(Stage stage, World world, BodyDef def, FixtureDef fixtureDef, GeneralUserData data){
		stage.addActor(animatedActor);
		
		body = world.createBody(def);
		body.createFixture(fixtureDef);
		body.setUserData(data);
	}
	
	public void destroyBody() {
		if (body != null) {
			World world = body.getWorld();
			world.destroyBody(body);
			body = null;
		}
	}
	
	public void setName(String name){
		animatedActor.setName(name);
	}
	
	public void setBounds(float x, float y, float width, float height){
		animatedActor.setBounds(x, y, width, height);
	}
	
	public void setScale(float x, float y){
		animatedActor.setScale(x, y);
	}
	
	public String getName(){
		return animatedActor.getName();
	}
	
	public Body getBody() {
		return body;
	}
	
	public AnimatedActorHero4Directions getAnimatedActor(){
		return animatedActor;
	}
	
	public boolean remove() {
		this.destroyBody();
		return animatedActor.remove();
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
	
	
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (body != null) {
			animatedActor.setX(body.getPosition().x * GLOBAL.WORLD_SCALE - animatedActor.getWidth() / 2);
			animatedActor.setY( body.getPosition().y * GLOBAL.WORLD_SCALE - animatedActor.getHeight() / 2);
		
			animatedActor.setRotation(MathUtils.radiansToDegrees * body.getAngle());
		}

		if(GLOBAL.DEBUG == false) {
			animatedActor.draw(batch, parentAlpha);
		}
	}
	
	public Vector2 getBodyPosition(){
		return body.getPosition();
	}
	
	public float getX(){
		return animatedActor.getX();
	}
	
	public float getY(){
		return animatedActor.getY();
	}
	
	public void refreshImagePosition(){
//		animatedActor.setPosition(body.getPosition().x - (animatedActor.getWidth()/2), body.getPosition().y - (animatedActor.getHeight()/2));
//		System.out.println("refreshing position!!! -----------");
//		System.out.println("body.size (" + );
		animatedActor.setPosition(body.getPosition().x, body.getPosition().y);
//		animatedActor.spriteFrame.setPosition(body.getPosition().x - animatedActor.spriteFrame.getScaleX()/4, body.getPosition().y - animatedActor.spriteFrame.getScaleY()/4);
//		System.out.println("spriteFrame.position (" + animatedActor.spriteFrame.getX() + ", " + animatedActor.spriteFrame.getY() + ")");
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
	
	public int getDirection(){
		return animatedActor.getDirection();
	}
	
	public void setDirection(int direction){
		animatedActor.setDirection(direction);
	}
	
	public float getHeigth(){
		return animatedActor.getHeight();
	}
	
	public float getWidth(){
		return animatedActor.getWidth();
	}
	
	public Body getLinkedBody(){
		return body.getJointList().get(0).other;
	}
}