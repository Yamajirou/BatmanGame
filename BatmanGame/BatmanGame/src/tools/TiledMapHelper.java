package tools;

/**
 *   Copyright 2011 David Kirchner dpk@dpk.net
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 * TiledMapHelper can simplify your game's tiled map operations. You can find
 * some sample code using this class at my blog:
 * 
 * http://dpk.net/2011/05/08/libgdx-box2d-tiled-maps-full-working-example-part-2/
 * 
 * Note: This code does have some limitations. It only supports single-layered
 * maps.
 * 
 * This code is based on TiledMapTest.java found at:
 * http://code.google.com/p/libgdx/
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
//import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
//import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import data.GameOverData;
import data.GeneralUserData;
import data.PlataformaData;
import data.SupportData;

public class TiledMapHelper {
	private static final int[] layersList = { 0 };
	
	private FileHandle packFileDirectory;

	private OrthographicCamera camera;

	private float scale = 1;
	
//	private TileAtlas tileAtlas;
	private OrthogonalTiledMapRenderer tileMapRenderer;

	private TiledMap map;
	
	public TiledMapHelper(Camera cam, String packerDir, String tmxFile, World world, float scale){
		camera = (OrthographicCamera) cam;
		this.scale = scale;
		setPackerDirectory(packerDir);
		loadMap(tmxFile);
//		tileMapRenderer.setView(camera);
		loadCollisions(world);
//		Vector3 tmp = new Vector3();
//		tmp.set(0, 0, 0);
//		camera.unproject(tmp);
	}
	
	public TiledMapHelper(){}
	
	/**
	 * Renders the part of the map that should be visible to the user.
	 */
	public void render() {
//		tileMapRenderer.getProjectionMatrix().set(camera.combined);
		tileMapRenderer.setView(camera);
//		tileMapRenderer.setView((OrthographicCamera)cam);

		Vector3 tmp = new Vector3();
		tmp.set(0, 0, 0);
		camera.unproject(tmp);
//		cam.unproject(tmp);

//		camera.unproject(camera.position);
		
//		tileMapRenderer.render((int) tmp.x, (int) tmp.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), layersList);
		tileMapRenderer.render(layersList);
	}

	/**
	 * Get the height of the map in pixels
	 * 
	 * @return y
	 */
	public int getHeight() {
		return (Integer)map.getProperties().get("height") * (Integer)map.getProperties().get("tileheight");
	}

	/**
	 * Get the width of the map in pixels
	 * 
	 * @return x
	 */
	public int getWidth() {
		System.out.println("getting width");
		if(map == null){
			System.out.println("map = null");
		}
		if(map.getProperties() == null){
			System.out.println("map.properties = null");
		}
		if(!map.getProperties().containsKey("width")){
			System.out.println("nao tem key: width");
		}
		if(!map.getProperties().containsKey("tilewidth")){
			System.out.println("nao tem key: tilewidth");
		}
		
		return (Integer)map.getProperties().get("width") * (Integer)map.getProperties().get("tilewidth");
	}

	/**
	 * Get the map, useful for iterating over the set of tiles found within
	 * 
	 * @return TiledMap
	 */
//	public TiledMap getMap() {
		public TiledMap getMap() {
		return map;
	}

	/**
	 * Calls dispose on all disposable resources held by this object.
	 */
	public void dispose() {
//		tileAtlas.dispose();
		tileMapRenderer.dispose();
	}

	/**
	 * Sets the directory that holds the game's pack files and tile sets.
	 * 
	 * @param packDirectory
	 */
	public void setPackerDirectory(String packDirectory) {
		packFileDirectory = Gdx.files.internal(packDirectory);
	}

	/**
	 * Loads the requested tmx map file in to the helper.
	 * 
	 * @param tmxFile
	 */
	public void loadMap(String tmxFile) {
		if (packFileDirectory == null) {
			throw new IllegalStateException("loadMap() called out of sequence");
		}

//		map = TiledLoader.createMap(Gdx.files.internal(tmxFile));
		map = new TmxMapLoader().load(tmxFile);
//		tileAtlas = new TileAtlas(map, packFileDirectory);
//		tileMapRenderer = new TileMapRenderer(map, tileAtlas, 16, 16);
		tileMapRenderer = new OrthogonalTiledMapRenderer(map, 1/scale);
	}
	
	public void loadCollisions(World world){
		TiledMapTileLayer tileMap = ((TiledMapTileLayer)getMap().getLayers().get(0));
		loadLines(world);
		loadSupports(world);
		loadRectangles(world);
		
		
		
		
	}
	
	private void loadRectangles(World world){
		System.out.println("LoadingRectangles");
		Array <RectangleMapObject> objects = getMap().getLayers().get("collision1").getObjects().getByType(RectangleMapObject.class);
		if(objects != null && objects.size != 0){
			for(int j = 0; j < objects.size; j++){
				RectangleMapObject obj = objects.get(j);
//				Iterator it = obj.getProperties().getKeys();
//				while(it.hasNext()){
//					System.out.println(it.next());
//				}
//				System.out.println("rectangleObject = " + obj.getProperties().get("gameover"));
				GeneralUserData data;
				if(obj.getProperties().containsKey("gameover")){
					data = new GameOverData();
				}else{
					data = new PlataformaData();
				}
				
				createRectangle(world, obj.getRectangle().height/scale, obj.getRectangle().width/scale, 
//									obj.getRectangle().x/16, obj.getRectangle().y/1);
									obj.getRectangle().x/scale, obj.getRectangle().y/scale, data);
			}
		}
		
	}
	
	private void loadLines(World world){
		System.out.println("LoadingLines");
		Array <PolylineMapObject> objects = getMap().getLayers().get("collision1").getObjects().getByType(PolylineMapObject.class);
//		System.out.println("printing array");
//		System.out.println(objects.size);
		if(objects != null && objects.size != 0){
			for(int j = 0; j < objects.size; j++){
				PolylineMapObject poly = objects.get(j);
				float x = (Integer)(poly.getProperties().get("x"));
				float y = (Integer)(poly.getProperties().get("y"));
				x /= 16;
				y /= 16;
//				System.out.println("x = " + x );
//				System.out.println("y = " + y );
				
				Polyline p = poly.getPolyline();
				float vertices[] = p.getVertices();
	//			float vertices[] = new float[]{0,0, 20,0};
	
//				System.out.println("vertices:");
				for(int i = 0; i < vertices.length; i++){
//					System.out.println(vertices[i]);
					vertices[i] = (Float)((vertices[i] / scale) + x);
//					System.out.println(vertices[i]);
					i++;
//					System.out.println(vertices[i]);
					vertices[i] = (Float)((vertices[i] / scale) + y);
//					System.out.println(vertices[i]);
				}
				createPolygon(world, vertices);
			}
		}
		
	}
	
	private void loadSupports(World world){
		System.out.println("LoadingSupports");
//		Array <CircleMapObject> circles = getMap().getLayers().get("collision1").getObjects().getByType(CircleMapObject.class);
		Array <EllipseMapObject> circles = getMap().getLayers().get("collision1").getObjects().getByType(EllipseMapObject.class);
		System.out.println("circles: " + circles.size);
		if(circles != null && circles.size != 0){
			for(int i = 0; i < circles.size; i++){
				
			
	//			CircleMapObject circle = circles.first();
				EllipseMapObject circle = circles.get(i);
	//			circle.getEllipse().
//				Iterator it = circle.getProperties().getKeys();
//				while(it.hasNext()){
//					System.out.println(it.next());
//				}
				
	//			circle.getCircle().
				float x = circle.getEllipse().x;
				float y = circle.getEllipse().y;
				float radius = circle.getEllipse().width;
				
//				System.out.println("radius: " + radius);
				
				radius /= 32;
				x /= scale;
				x += radius;
				y /= scale;
				y += radius;
				
				
//				System.out.println("x = " + x);
//				System.out.println("y = " + y);
//				System.out.println("radius = " + radius);
				
				createCircle(world, x, y, radius);
			}
		}
		
	}
	
	private void createPolygon(World world, float vertices[]){
		Body ground;
		BodyDef bd = new BodyDef();
		
		ground = world.createBody(bd);
		
		ChainShape cShape = new ChainShape();
		cShape.createChain(vertices);
		
		ground.createFixture(cShape, 0.0f);
		cShape.dispose();
	}
	
	private void createCircle(World world, float x, float y, float radius){
		Body circle;
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(x, y);
		circle = world.createBody(bd);
//		circle.getMassData().center = new Vector2(x,y);
		circle.setUserData(new SupportData());
		CircleShape shape = new CircleShape();
//		System.out.println("circleCenter = " + circle.getWorldCenter());
//		shape.setPosition(new Vector2(x,y));
		shape.setRadius(radius);
		
		circle.createFixture(shape, 0.0f);
		shape.dispose();
	
	}
	
//	private void createRectangle(World world, float height, float width, float x, float y){
	private void createRectangle(World world, float height, float width, float x, float y, GeneralUserData data){
		Body body;
		BodyDef bd = new BodyDef();
		bd.position.set(x + (width/2), y + (height/2));
		body = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		System.out.println("setting shape");
//		shape.set(new float[]{x, y, x, y + height, x+ width, y + height, x+ width, y});
//		shape.set(new float[]{x, y, x + width, y, x+ width, y + height, x, y + height});
//		shape.setAsBox(width, height);
		shape.setAsBox(width/2, height/2);
		
		System.out.println("creating fixture");
		body.createFixture(shape, 0.0f);
//		ground.setUserData(data);
		
		body.setUserData(data);
		shape.dispose();
		
	}
	
	private void createLine(float x1, float y1, float x2, float y2, World world){
		Body ground;
		BodyDef bd = new BodyDef();
		ground = world.createBody(bd);

		
		EdgeShape shape = new EdgeShape();
		shape.set(new Vector2(x1, y1), new Vector2(x2, y2));
		
		ground.createFixture(shape, 0.0f);
//		ground.setUserData(data);
		
//		ground.setUserData(new PlataformaData())d;
		shape.dispose();
		
	}

	/**
	 * Reads a file describing the collision boundaries that should be set
	 * per-tile and adds static bodies to the boxd world.
	 * 
	 * @param collisionsFile
	 * @param world
	 * @param pixelsPerMeter
	 *            the pixels per meter scale used for this world
	 */
	public void loadCollisions(String collisionsFile, World world,
			float pixelsPerMeter) {
		/**
		 * Detect the tiles and dynamically create a representation of the map
		 * layout, for collision detection. Each tile has its own collision
		 * rules stored in an associated file.
		 * 
		 * The file contains lines in this format (one line per type of tile):
		 * tileNumber XxY,XxY XxY,XxY
		 * 
		 * Ex:
		 * 
		 * 3 0x0,31x0 ... 4 0x0,29x0 29x0,29x31
		 * 
		 * For a 32x32 tileset, the above describes one line segment for tile #3
		 * and two for tile #4. Tile #3 has a line segment across the top. Tile
		 * #1 has a line segment across most of the top and a line segment from
		 * the top to the bottom, 30 pixels in.
		 */

		FileHandle fh = Gdx.files.internal(collisionsFile);
		String collisionFile = fh.readString();
		String lines[] = collisionFile.split("\\r?\\n");

		HashMap<Integer, ArrayList<LineSegment>> tileCollisionJoints = new HashMap<Integer, ArrayList<LineSegment>>();

		/**
		 * Some locations on the map (perhaps most locations) are "undefined",
		 * empty space, and will have the tile type 0. This code adds an empty
		 * list of line segments for this "default" tile.
		 */
		tileCollisionJoints.put(Integer.valueOf(0),
				new ArrayList<LineSegment>());

		for (int n = 0; n < lines.length; n++) {
			String cols[] = lines[n].split(" ");
			int tileNo = Integer.parseInt(cols[0]);

			ArrayList<LineSegment> tmp = new ArrayList<LineSegment>();

			for (int m = 1; m < cols.length; m++) {
				String coords[] = cols[m].split(",");

				String start[] = coords[0].split("x");
				String end[] = coords[1].split("x");

				tmp.add(new LineSegment(Integer.parseInt(start[0]), Integer
						.parseInt(start[1]), Integer.parseInt(end[0]), Integer
						.parseInt(end[1])));
			}

			tileCollisionJoints.put(Integer.valueOf(tileNo), tmp);
		}

		ArrayList<LineSegment> collisionLineSegments = new ArrayList<LineSegment>();
		
//		Iterator it = tileCollisionJoints.values().iterator();
//		
//		while(it.hasNext()){
//			ArrayList<LineSegment> array = (ArrayList<LineSegment>)it.next();
//			for(LineSegment ls : array){
//				System.out.println(ls.toString());
//			}
//		}
//		
//		System.out.println("tileCollisionJoints.size: " + tileCollisionJoints.size());
//		
//		System.out.println("obj count:" + getMap().getLayers().get(1).getObjects().getCount());
//		MapObject mapobj = getMap().getLayers().get(1).getObjects().get(0);
//		((TiledMapTileLayer)getMap().getLayers().get(0)).getCell(0, 0).getTile().getId();

		Iterator keys = getMap().getProperties().getKeys();
		System.out.println("Keys! :");
		while(keys.hasNext()){
			System.out.println(keys.next());
		}
		System.out.println();
		TiledMapTileLayer tileMap = ((TiledMapTileLayer)getMap().getLayers().get(0));
//		tileMap.
		
		
		for (int y = 0; y < (Integer)getMap().getProperties().get("height"); y++) {
			for (int x = 0; x < (Integer)getMap().getProperties().get("width"); x++) {
//				int tileType = getMap().layers.get(0).tiles[(getMap().height - 1) - y][x];
//				int tileType = getMap().getProperties().tiles[(getMap().height - 1) - y][x];

				Cell cel = tileMap.getCell(x, ((Integer)getMap().getProperties().get("height") - 1) - y);
				System.out.println("cell(" + x + ", " + (((Integer)getMap().getProperties().get("height") - 1) - y) + ")");
				
				if(cel == null){
					System.out.println("cel == null");
					continue;
				}
				
				TiledMapTile celTile = cel.getTile();
				
				int tileType = celTile.getId();
				
				for (int n = 0; n < tileCollisionJoints.get(
						Integer.valueOf(tileType)).size(); n++) {
					LineSegment lineSeg = tileCollisionJoints.get(
							Integer.valueOf(tileType)).get(n);

					addOrExtendCollisionLineSegment(x * (Integer)getMap().getProperties().get("tilewidth")
							+ lineSeg.start().x, y * (Integer)getMap().getProperties().get("tileheight")
							- lineSeg.start().y + 32, x * (Integer)getMap().getProperties().get("tilewidth")
							+ lineSeg.end().x, y * (Integer)getMap().getProperties().get("tileheight")
							- lineSeg.end().y + 32, collisionLineSegments);
				}
			}
		}

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		Body groundBody = world.createBody(groundBodyDef);
		for (LineSegment lineSegment : collisionLineSegments) {
			EdgeShape environmentShape = new EdgeShape();
			
			environmentShape.set(
					lineSegment.start().mul(1 / pixelsPerMeter), lineSegment
							.end().mul(1 / pixelsPerMeter));
			groundBody.createFixture(environmentShape, 0);
			environmentShape.dispose();
		}

		/**
		 * Drawing a boundary around the entire map. We can't use a box because
		 * then the world objects would be inside and the physics engine would
		 * try to push them out.
		 */

		EdgeShape mapBounds = new EdgeShape();
		mapBounds.set(new Vector2(0.0f, 0.0f), new Vector2(getWidth()
				/ pixelsPerMeter, 0.0f));
		groundBody.createFixture(mapBounds, 0);

		mapBounds.set(new Vector2(0.0f, getHeight() / pixelsPerMeter),
				new Vector2(getWidth() / pixelsPerMeter, getHeight()
						/ pixelsPerMeter));
		groundBody.createFixture(mapBounds, 0);

		mapBounds.set(new Vector2(0.0f, 0.0f), new Vector2(0.0f,
				getHeight() / pixelsPerMeter));
		groundBody.createFixture(mapBounds, 0);

		mapBounds.set(new Vector2(getWidth() / pixelsPerMeter, 0.0f),
				new Vector2(getWidth() / pixelsPerMeter, getHeight()
						/ pixelsPerMeter));
		groundBody.createFixture(mapBounds, 0);

		mapBounds.dispose();
	}

	/**
	 * This is a helper function that makes calls that will attempt to extend
	 * one of the line segments already tracked by TiledMapHelper, if possible.
	 * The goal is to have as few line segments as possible.
	 * 
	 * Ex: If you have a line segment in the system that is from 1x1 to 3x3 and
	 * this function is called for a line that is 4x4 to 9x9, rather than add a
	 * whole new line segment to the list, the 1x1,3x3 line will be extended to
	 * 1x1,9x9. See also: LineSegment.extendIfPossible.
	 * 
	 * @param lsx1
	 *            starting x of the new line segment
	 * @param lsy1
	 *            starting y of the new line segment
	 * @param lsx2
	 *            ending x of the new line segment
	 * @param lsy2
	 *            ending y of the new line segment
	 * @param collisionLineSegments
	 *            the current list of line segments
	 */
	private void addOrExtendCollisionLineSegment(float lsx1, float lsy1,
			float lsx2, float lsy2, ArrayList<LineSegment> collisionLineSegments) {
		LineSegment line = new LineSegment(lsx1, lsy1, lsx2, lsy2);

		boolean didextend = false;

		for (LineSegment test : collisionLineSegments) {
			if (test.extendIfPossible(line)) {
				didextend = true;
				break;
			}
		}

		if (!didextend) {
			collisionLineSegments.add(line);
		}
	}

	/**
	 * Prepares the helper's camera object for use.
	 * 
	 * @param screenWidth
	 * @param screenHeight
	 */
	public void prepareCamera(int screenWidth, int screenHeight) {
		camera = new OrthographicCamera(screenWidth, screenHeight);

		camera.position.set(0, 0, 0);
	}

	/**
	 * Returns the camera object created for viewing the loaded map.
	 * 
	 * @return OrthographicCamera
	 */
	public OrthographicCamera getCamera() {
		if (camera == null) {
			throw new IllegalStateException(
					"getCamera() called out of sequence");
		}
		return camera;
	}

	/**
	 * Describes the start and end points of a line segment and contains a
	 * helper method useful for extending line segments.
	 */
	private class LineSegment {
		private Vector2 start = new Vector2();
		private Vector2 end = new Vector2();

		/**
		 * Construct a new LineSegment with the specified coordinates.
		 * 
		 * @param x1
		 * @param y1
		 * @param x2
		 * @param y2
		 */
		public LineSegment(float x1, float y1, float x2, float y2) {
			start = new Vector2(x1, y1);
			end = new Vector2(x2, y2);
		}

		/**
		 * The "start" of the line. Start and end are misnomers, this is just
		 * one end of the line.
		 * 
		 * @return Vector2
		 */
		public Vector2 start() {
			return start;
		}

		/**
		 * The "end" of the line. Start and end are misnomers, this is just one
		 * end of the line.
		 * 
		 * @return Vector2
		 */
		public Vector2 end() {
			return end;
		}

		/**
		 * Determine if the requested line could be tacked on to the end of this
		 * line with no kinks or gaps. If it can, the current LineSegment will
		 * be extended by the length of the passed LineSegment.
		 * 
		 * @param lineSegment
		 * @return boolean true if line was extended, false if not.
		 */
		public boolean extendIfPossible(LineSegment lineSegment) {
			/**
			 * First, let's see if the slopes of the two segments are the same.
			 */
			double slope1 = Math.atan2(end.y - start.y, end.x - start.x);
			double slope2 = Math.atan2(lineSegment.end.y - lineSegment.start.y,
					lineSegment.end.x - lineSegment.start.x);

			if (Math.abs(slope1 - slope2) > 1e-9) {
				return false;
			}

			/**
			 * Second, check if either end of this line segment is adjacent to
			 * the requested line segment. So, 1 pixel away up through sqrt(2)
			 * away.
			 * 
			 * Whichever two points are within the right range will be "merged"
			 * so that the two outer points will describe the line segment.
			 */
			if (start.dst(lineSegment.start) <= Math.sqrt(2) + 1e-9) {
				start.set(lineSegment.end);
				return true;
			} else if (end.dst(lineSegment.start) <= Math.sqrt(2) + 1e-9) {
				end.set(lineSegment.end);
				return true;
			} else if (end.dst(lineSegment.end) <= Math.sqrt(2) + 1e-9) {
				end.set(lineSegment.start);
				return true;
			} else if (start.dst(lineSegment.end) <= Math.sqrt(2) + 1e-9) {
				start.set(lineSegment.start);
				return true;
			}

			return false;
		}

		/**
		 * Returns a pretty description of the LineSegment.
		 * 
		 * @return String
		 */
		@Override
		public String toString() {
			return "[" + start.x + "x" + start.y + "] -> [" + end.x + "x"
					+ end.y + "]";
		}
	}


}
