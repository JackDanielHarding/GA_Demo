
package map;

import display.Window;
import logging.Logger;
import logging.Logger.Category;

public class Village {

	private int maxFood;

	private Population population;
	private int fittestEntity = 0;

	private int generation = 1;
	private int time = 0;

	private TileMap map;

	private int moveCounter = 0;
	private static final int MOVE_DELAY = 5;

	public Village(int populationSize, int mapSize, int maxFood) {
		this.maxFood = maxFood;
		map = new TileMap(mapSize);
		population = new Population(populationSize);
	}

	public void setUp() {
		map.reset();

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
				time++;
				Logger.debug("Time: " + time, Category.SYSTEM);
				map.moveEntities();
				moveCounter = MOVE_DELAY;
			}

			moveCounter--;
		} else {
			createNextGeneration();
		}
	}

	public void createNextGeneration() {
		int fittest = population.getFittest().getFitness();
		if (fittest > fittestEntity) {
			fittestEntity = fittest;
		}
		Logger.info("Fittest Entity of Generation: " + population.getFittest().getFitness());
		Logger.info("Fittest Entity of all time: " + population.getFittest().getFitness());
		time = 0;
		generation++;
		Logger.info("Generation: " + generation);
		population = new Population(population);
		map.reset();
		createInitialFood();
		map.spawnPopulation(population);
	}
}
