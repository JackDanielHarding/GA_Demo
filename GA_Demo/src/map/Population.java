package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.Entity;

public class Population {

	private List<Entity> entities;
	private int size;

	public Population(int size) {
		this.size = size;
		entities = new ArrayList<>(size);
	}

	public Population(Population population) {
		this.size = population.size;
		entities = new ArrayList<>(size);
		entities.addAll(population.getFittestArray());
		breed();
	}

	public void init() {
		for (int i = 0; i < size; i++) {
			entities.add(new Entity());
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

	public List<Entity> getFittestArray() {
		List<Entity> fittest = new ArrayList<>();
		Collections.sort(fittest);
		for (int i = 0; i < 4; i++) {
			fittest.add(entities.remove(0));
		}
		return fittest;
	}

	public void breed() {
		for (Entity entity : entities) {
			entity.reset();
		}
		// TODO
	}

	public int getSize() {
		return size;
	}

	public List<Entity> getEntities() {
		return entities;
	}
}
