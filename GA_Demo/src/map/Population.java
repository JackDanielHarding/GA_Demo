package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.Entity;
import logging.Logger;
import logging.Logger.Category;

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
		Entity bestEntity;
		Collections.sort(entities);
		bestEntity = entities.get(0);

		return bestEntity;
	}

	public List<Entity> getFittestArray() {
		List<Entity> fittest = new ArrayList<>();
		Collections.sort(entities);
		int halfPopulationSize = entities.size() / 2;
		for (int i = 0; i < halfPopulationSize; i++) {
			fittest.add(entities.remove(0));
		}
		return fittest;
	}

	public void breed() {
		int currentPopulationSize = entities.size();
		for (int i = 0; i < currentPopulationSize - 1; i++) {
			entities.add(new Entity(entities.get(i), entities.get(i + 1)));
		}
		entities.add(new Entity(entities.get(currentPopulationSize - 1), entities.get(0)));

		for (Entity entity : entities) {
			entity.reset();
		}
		Logger.debug("Entities bred. new entities size is: " + entities.size(), Category.ENTITIES);
	}

	public float averageFitness() {
		float averageFitness = 0.0f;
		for (Entity entity : entities) {
			averageFitness += entity.getFitness();
		}
		averageFitness = averageFitness / entities.size();
		return averageFitness;
	}

	public int getSize() {
		return size;
	}

	public List<Entity> getEntities() {
		return entities;
	}
}
