package genes;

public abstract class Gene<T> {

	protected String name;

	public Gene(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void mutate();

	public abstract T getValue();

	@Override
	public abstract String toString();
}
