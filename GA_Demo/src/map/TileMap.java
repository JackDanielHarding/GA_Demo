package map;

import java.util.Random;

import display.Window;
import entities.Vector2i;

public class TileMap {

	private TileType[][] tiles;
	private int size;

	public TileMap(int size) {
		this.size = size;
		tiles = new TileType[size][size];
	}

	public void setWalls() {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if ((x == 0 || x == size - 1) || (y == 0 || y == size - 1)) {
					tiles[x][y] = TileType.WALL;
				} else {
					tiles[x][y] = TileType.EMPTY;
				}
			}
		}
	}

	public Vector2i getEmptyTile() {
		Random rand = new Random();
		int x;
		int y;

		do {
			x = rand.nextInt(size);
			y = rand.nextInt(size);
		} while (!getTile(x, y).equals(TileType.EMPTY));
		return new Vector2i(x, y);
	}

	public void setEmptyTile(TileType type) {
		Vector2i emptyTile = getEmptyTile();
		tiles[emptyTile.getX()][emptyTile.getY()] = type;
	}

	public TileType getTile(int x, int y) {
		return tiles[x][y];
	}

	public void setTile(int x, int y, TileType type) {
		tiles[x][y] = type;
	}

	public void setTile(Vector2i tile, TileType type) {
		setTile(tile.getX(), tile.getY(), type);
	}

	public TileType[][] getTiles() {
		return tiles;
	}

	public void render(Window w) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				w.renderTile(x, y, size, tiles[x][y]);
			}
		}
	}
}
