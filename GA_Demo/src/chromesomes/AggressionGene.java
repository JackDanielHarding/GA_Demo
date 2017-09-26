package chromesomes;

import java.util.Random;

public class AggressionGene {

	private boolean aggression;

	public AggressionGene() {
		Random rand = new Random();
		aggression = rand.nextBoolean();
	}

	public AggressionGene(AggressionGene aggressionGene) {
		aggression = aggressionGene.getAggression();
	}

	public AggressionGene(AggressionGene parent1, AggressionGene parent2) {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			aggression = parent1.getAggression();
		} else {
			aggression = parent2.getAggression();
		}
	}

	public Boolean getAggression() {
		return aggression;
	}

	@Override
	public String toString() {
		return "Aggression: " + aggression;
	}
}
