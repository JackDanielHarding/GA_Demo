package chromesomes;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import actions.Action;
import map.TileType;

public class ReactionChromesome {
	
	private Map<TileType, Action> reactions;
	
	public ReactionChromesome(){
		reactions = new EnumMap<>(TileType.class);
		
		Action[] actions = Action.values();
		TileType[] tiles = TileType.values();
		Random rand = new Random();
		for(int i = 0; i < tiles.length; i++){
			reactions.put(tiles[i], actions[rand.nextInt(actions.length)]);
		}
	}
	
	public ReactionChromesome(ReactionChromesome parent1, ReactionChromesome parent2){
		
	}
	
	public Action getReaction(TileType tile){
		return reactions.get(tile);
	}
}
