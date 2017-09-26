package genes;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import actions.Action;
import logging.Logger;
import logging.Logger.Category;
import map.TileType;

public class ReactionGene {

	private Map<TileType, Action> reactions = new EnumMap<>(TileType.class);

	public ReactionGene() {
		Action[] actions = Action.values();
		TileType[] tiles = TileType.values();
		Random rand = new Random();
		for (int i = 0; i < tiles.length; i++) {
			reactions.put(tiles[i], actions[rand.nextInt(actions.length)]);
		}

		Logger.debug(toString(), Category.CHROMESOMES);
	}

	public ReactionGene(ReactionGene reactionChromesome) {
		reactions.putAll(reactionChromesome.getReactions());
	}

	public ReactionGene(ReactionGene parent1, ReactionGene parent2) {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			combineReactions(parent1, parent2);
		} else {
			combineReactions(parent2, parent1);
		}
	}

	private void combineReactions(ReactionGene parent1, ReactionGene parent2) {
		TileType[] tiles = TileType.values();
		reactions = parent1.getReactions();
		reactions.put(tiles[0], parent2.getReactions().get(tiles[0]));
		reactions.put(tiles[1], parent2.getReactions().get(tiles[1]));
	}

	public void mutate() {
		Random rand = new Random();
		Action[] actions = Action.values();
		TileType[] tiles = TileType.values();

		reactions.put(tiles[rand.nextInt(tiles.length)], actions[rand.nextInt(actions.length)]);
	}

	public Map<TileType, Action> getReactions() {
		return reactions;
	}

	public Action getReaction(TileType tile) {
		return reactions.get(tile);
	}

	@Override
	public String toString() {
		StringBuilder outputString = new StringBuilder();
		outputString.append("Reaction Chromesome: ");
		for (Entry<TileType, Action> entry : reactions.entrySet()) {
			outputString.append(", [" + entry.getKey() + ", " + entry.getValue() + "]");
		}
		return outputString.toString();
	}
}
