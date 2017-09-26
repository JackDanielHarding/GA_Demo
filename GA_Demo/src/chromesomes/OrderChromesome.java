package chromesomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logging.Logger;
import logging.Logger.Category;

public class OrderChromesome<T extends Enum<T>> {

	private String name;
	private List<T> list = new ArrayList<>();

	public OrderChromesome(String name, Class<T> enumType) {
		this.name = name;
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

	public OrderChromesome(OrderChromesome<T> orderChromesome) {
		name = orderChromesome.getName();
		list.addAll(orderChromesome.getList());
	}

	public OrderChromesome(OrderChromesome<T> parent1, OrderChromesome<T> parent2) {
		name = parent1.getName();
		Random rand = new Random();
		do {
			if (rand.nextBoolean()) {
				addUniqueValueFromParent(parent1);
			} else {
				addUniqueValueFromParent(parent2);
			}
		} while (list.size() < parent1.getList().size());
	}

	public void addUniqueValueFromParent(OrderChromesome<T> parent) {
		T next;
		int index = 0;
		do {
			next = parent.getList().get(index++);
		} while (list.contains(next));
		list.add(next);
	}

	public void mutate() {
		switchPriorities();
	}

	public void switchPriorities() {
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		T temp = list.get(index1);
		int index2;
		if (index1 == list.size() - 1) {
			index2 = 0;
		} else {
			index2 = index1 + 1;
		}
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}

	public void switchRandomPriorities() {
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		int index2;
		do {
			index2 = rand.nextInt(4);
		} while (index2 == index1);
		T temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}

	public int getPriority(T value) {
		return list.indexOf(value);
	}

	public List<T> getList() {
		return list;
	}

	public String getName() {
		return name;
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
