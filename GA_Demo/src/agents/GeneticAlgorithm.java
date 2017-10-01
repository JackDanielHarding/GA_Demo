package agents;

import entities.Entity;
import map.TileType;

public class GeneticAlgorithm extends Agent {

	private static final int POPULATION_SIZE = 10;

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Entity[] entities) {
		for (Entity entity : entities) {
			moveEntity(entity);
		}
	}

	public void moveEntity(Entity entity) {

		TileType[] entitySight = entity.getSight();

		entity.setNextMovement(movementVector);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
}
