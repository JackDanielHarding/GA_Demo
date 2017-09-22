
package map;

import display.Window;
import entities.Entity;
import entities.Vector2i;

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

		createInitialPopulation();
		createInitialFood();
	}

	public void createInitialPopulation() {
		for (int i = 0; i < population.getSize(); i++) {
			Vector2i emptyTile = map.getEmptyTile();
			population.addEntity(new Entity(emptyTile));
			map.setTile(emptyTile, TileType.ENTITY);
		}
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
			for (Entity entity : population.getEntities()) {
				Vector2i entityPosition = entity.getPosition();
				map.setTile(entityPosition.getX(), entityPosition.getY(), TileType.EMPTY);
				if (!entity.isDead()) {
					Vector2i entityMovement = entity.move(map);
					map.setTile(entityMovement.getX(), entityMovement.getY(), TileType.ENTITY);
				}
				if (bestEntity == null || entity.getFitness() > bestEntity.getFitness()) {
					bestEntity = entity;
				}
			}

			bestEntity = population.getFittest();

			moveCounter = MOVE_DELAY;
		}

		moveCounter--;
	}

	public void createNextGeneration() {
		generation++;
	}
}
