
package map;

import display.Window;
import entities.Entity;
import logging.Logger;

public class Village {

	private int maxFood;

	private Population population;

	private int generation = 1;

	private TileMap map;

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
		if (!map.getEntities().isEmpty()) {
			if (moveCounter <= 0) {
				moveEntities();
				moveCounter = MOVE_DELAY;
			}

			moveCounter--;
		} else {
			Logger.info("Best Entity Chromesomes: ");
			createNextGeneration();
		}
	}

	public void createNextGeneration() {
		generation++;
		Logger.info("Generation: " + generation);
		population = new Population(population);
		map.spawnPopulation(population);
	}

	public void moveEntities() {
		for (Entity entity : map.getEntities()) {
			entity.move(map);
		}
	}
}
