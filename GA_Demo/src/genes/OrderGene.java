package genes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import logging.Logger;
import logging.Logger.Category;

public class OrderGene<T extends Enum<T>> extends Gene<List<T>> {

	private List<T> list = new ArrayList<>();

	public OrderGene(String name, Class<T> enumType) {
		super(name);
		Random rand = new Random();
		T[] enumValues = enumType.getEnumConstants();
		T temp;

		for (int i = 0; i < enumValues.length; i++) {
			do {
				temp = enumValues[rand.nextInt(enumValues.length)];
			} while (list.contains(temp));
			list.add(temp);
		}

		Logger.debug(toString(), Category.CHROMESOMES);
	}

	public OrderGene(OrderGene<T> orderChromesome) {
		super(orderChromesome.getName());
		list.addAll(orderChromesome.getValue());
	}

	public OrderGene(OrderGene<T> parent1, OrderGene<T> parent2) {
		super(parent1.getName());
		Random rand = new Random();
		do {
			if (rand.nextBoolean()) {
				addUniqueValueFromParent(parent1);
			} else {
				addUniqueValueFromParent(parent2);
			}
		} while (list.size() < parent1.getValue().size());
	}

	public void addUniqueValueFromParent(OrderGene<T> parent) {
		T next;
		int index = 0;
		do {
			next = parent.getValue().get(index++);
		} while (list.contains(next));
		list.add(next);
	}

	public void switchPriorities() {
		Random rand = new Random();
		int index = rand.nextInt(4);
		if (index == list.size() - 1) {
			Collections.swap(list, index, 0);
		} else {
			Collections.swap(list, index, index + 1);
		}
	}

	public void switchRandomPriorities() {
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		int index2;
		do {
			index2 = rand.nextInt(4);
		} while (index2 == index1);
		Collections.swap(list, index1, index2);
	}

	public int getPriority(T value) {
		return list.indexOf(value);
	}

	@Override
	public List<T> getValue() {
		return list;
	}

	@Override
	public void mutate() {
		switchPriorities();
	}

	@Override
	public String toString() {
		StringBuilder outputString = new StringBuilder();
		outputString.append(name + ": ");
		for (T tile : list) {
			outputString.append(", " + tile);
		}
		return outputString.toString();
	}
}
