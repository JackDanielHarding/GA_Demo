package genetics.genes;

import java.util.Random;

public class BooleanGene {

	private String name;
	private boolean value;

	public BooleanGene(String name) {
		this.name = name;
		Random rand = new Random();
		value = rand.nextBoolean();
	}

	public BooleanGene(BooleanGene gene) {
		name = gene.getName();
		value = gene.getValue();
	}

	public BooleanGene(BooleanGene parent1, BooleanGene parent2) {
		name = parent1.getName();
		Random rand = new Random();
		if (rand.nextBoolean()) {
			value = parent1.getValue();
		} else {
			value = parent2.getValue();
		}
	}

	public Boolean getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Aggression: " + value;
	}
}
