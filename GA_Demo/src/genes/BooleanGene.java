package genes;

import java.util.Random;

public class BooleanGene extends Gene {

	private boolean value;

	public BooleanGene(String name) {
		super(name);
		Random rand = new Random();
		value = rand.nextBoolean();
	}

	public BooleanGene(BooleanGene gene) {
		super(gene.getName());
		value = gene.getValue();
	}

	public BooleanGene(BooleanGene parent1, BooleanGene parent2) {
		super(parent1.getName());
		Random rand = new Random();
		if (rand.nextBoolean()) {
			value = parent1.getValue();
		} else {
			value = parent2.getValue();
		}
	}

	@Override
	public void mutate() {
		value = !value;
	}

	public Boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return name + ": " + value;
	}
}
