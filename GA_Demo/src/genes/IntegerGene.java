package genes;

import java.util.Random;

public class IntegerGene {

	private String name;
	private int value;
	private int valueCap;

	public IntegerGene(String name, int valueCap) {
		this.name = name;
		this.valueCap = valueCap;

		Random rand = new Random();
		value = rand.nextInt(valueCap);
	}

	public IntegerGene(IntegerGene gene) {
		name = gene.name;
		value = gene.value;
		valueCap = gene.valueCap;
	}

	public IntegerGene(IntegerGene parent1, IntegerGene parent2) {
		name = parent1.name;
		valueCap = parent1.valueCap;
		int midpoint = Math.abs(parent1.value - parent2.value) / 2;
		if (parent1.value < parent2.value) {
			value = parent1.value + midpoint;
		} else {
			value = parent2.value + midpoint;
		}
	}

	public void mutate() {
		if (value == 0) {
			value++;
		} else if (value == valueCap) {
			value--;
		} else {
			Random rand = new Random();
			if (rand.nextBoolean()) {
				value++;
			} else {
				value--;
			}
		}
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name + ": " + value;
	}
}
