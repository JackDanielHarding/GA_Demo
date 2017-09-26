package genes;

public abstract class Gene {

	protected String name;

	public Gene(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void mutate();

	@Override
	public abstract String toString();
}
