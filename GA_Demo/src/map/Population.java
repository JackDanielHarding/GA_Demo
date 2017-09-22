package map;

import entities.Entity;

public class Population {

	private Entity[] entities;
	private int index = 0;

	public Population(int populationSize) {
		entities = new Entity[populationSize];
	}

	public void addEntity(Entity entity) {
		if (index < entities.length) {
			entities[index] = entity;
			index++;
		}
	}

	public Entity getFittest() {
		Entity bestEntity = null;

		for (Entity entity : entities) {
			if (bestEntity == null || entity.getFitness() > bestEntity.getFitness()) {
				bestEntity = entity;
			}
		}

		return bestEntity;
	}

	public int getSize() {
		return entities.length;
	}

	public Entity[] getEntities() {
		return entities;
	}
}
