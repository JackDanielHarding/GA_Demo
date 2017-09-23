package chromesomes;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import actions.Action;
import logging.Logger;
import logging.Logger.Category;
import map.TileType;

public class ReactionChromesome {

	private Map<TileType, Action> reactions;

	public ReactionChromesome() {
		reactions = new EnumMap<>(TileType.class);

		Action[] actions = Action.values();
		TileType[] tiles = TileType.values();
		Random rand = new Random();
		for (int i = 0; i < tiles.length; i++) {
			reactions.put(tiles[i], actions[rand.nextInt(actions.length)]);
		}

		Logger.debug(toString(), Category.CHROMESOMES);
	}

	public ReactionChromesome(ReactionChromesome parent1, ReactionChromesome parent2) {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			combineReactions(parent1, parent2);
		} else {
			combineReactions(parent2, parent1);
		}
	}

	private void combineReactions(ReactionChromesome parent1, ReactionChromesome parent2) {
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
		for (Entry<TileType, Action> entry : reactions.entrySet()) {
			outputString.append(", [" + entry.getKey() + ", " + entry.getValue() + "]");
		}
		return outputString.toString();
	}
}
