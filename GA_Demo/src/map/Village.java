
package map;

import display.Window;
import entities.Entity;

public class Village {

	private int maxFood;

	private Population population;

	private int generation = 1;
	private Entity bestEntity;

	TileMap map;

	private int moveCounter = 0;
	private static final int MOVE_DELAY = 60;

	public Village(int populationSize, int mapSize, int maxFood) {
		this.maxFood = maxFood;
		map = new TileMap(mapSize);
		population = new Population(populationSize);
	}

	public void setUp() {
		map.setWalls();

		population.init();
		map.spawnPopulation(population);

		createInitialFood();
	}

	public void createInitialFood() {
		for (int i = 0; i < maxFood; i++) {
			map.setEmptyTile(TileType.FOOD);
		}
	}

	public void render(Window w) {
		map.render(w);
	}

	public void update() {
		if (moveCounter <= 0) {
			moveEntities();
			bestEntity = population.getFittest();
			moveCounter = MOVE_DELAY;
		}

		moveCounter--;
	}

	public void createNextGeneration() {
		generation++;
	}

	public void moveEntities() {
		for (Entity entity : population.getEntities()) {
			if (!entity.isDead()) {
				entity.move(map);
			}
		}
	}
}
