package agents;

import entities.Entity;

public abstract class Agent {

	public abstract void init();

	public abstract void move(Entity[] entities);

	public abstract void reset();
}
