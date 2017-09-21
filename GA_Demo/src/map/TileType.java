package map;
import org.joml.Vector3f;
public enum TileType {
	EMPTY(0.0f,0.0f,0.0f), WALL(0.0f,0.5f,0.0f), ENTITY(0.0f,0.0f,0.5f), FOOD(0.5f,0.0f,0.0f);
	
	private final Vector3f color;
	
	TileType(final float r,final float g,final float b) {
		color = new Vector3f(r,g,b);
	}
	
	public Vector3f getColor() {
		return color;
	}
}
