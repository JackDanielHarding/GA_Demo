package genetics.chromesomes;

public abstract class Chromosome {

	protected String name;

	public Chromosome(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void mutate();

	@Override
	public abstract String toString();
}
