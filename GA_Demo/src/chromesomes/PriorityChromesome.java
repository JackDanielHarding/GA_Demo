package chromesomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logging.Logger;
import logging.Logger.Category;
import map.TileType;

public class PriorityChromesome {

	private List<TileType> priorities;

	public PriorityChromesome() {
		priorities = new ArrayList<>();

		Random rand = new Random();
		TileType[] tiles = TileType.values();
		TileType temp;

		for (int i = 0; i < tiles.length; i++) {
			do {
				temp = tiles[rand.nextInt(tiles.length)];
			} while (priorities.contains(temp));
			priorities.add(temp);
		}

		Logger.debug(toString(), Category.CHROMESOMES);
	}

	public PriorityChromesome(PriorityChromesome parent1, PriorityChromesome parent2) {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			priorities = parent1.getPriorities();
		} else {
			priorities = parent2.getPriorities();
		}
	}

	public void mutate() {
		switchPriorities();
	}

	public void switchPriorities() {
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		TileType temp = priorities.get(index1);
		int index2;
		if (index1 == priorities.size() - 1) {
			index2 = 0;
		} else {
			index2 = index1 + 1;
		}
		priorities.set(index1, priorities.get(index2));
		priorities.set(index2, temp);
	}

	public void switchRandomPriorities() {
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		int index2;
		do {
			index2 = rand.nextInt(4);
		} while (index2 == index1);
		TileType temp = priorities.get(index1);
		priorities.set(index1, priorities.get(index2));
		priorities.set(index2, temp);
	}

	public int getPriority(TileType tile) {
		return priorities.indexOf(tile);
	}

	public List<TileType> getPriorities() {
		return priorities;
	}

	@Override
	public String toString() {
		StringBuilder outputString = new StringBuilder();
		for (TileType tile : priorities) {
			outputString.append(", " + tile);
		}
		return outputString.toString();
	}
}
