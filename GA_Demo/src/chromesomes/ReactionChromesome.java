package chromesomes;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import actions.Actions;
import map.TileTypes;

public class ReactionChromesome {
	
	private Map<TileTypes, Actions> reactions;
	
	public ReactionChromesome(){
		reactions = new EnumMap<>(TileTypes.class);
		
		Actions[] actions = Actions.values();
		TileTypes[] tiles = TileTypes.values();
		Random rand = new Random();
		for(int i = 0; i < tiles.length; i++){
			reactions.put(tiles[i], actions[rand.nextInt(actions.length)]);
		}
	}
	
	public ReactionChromesome(ReactionChromesome parent1, ReactionChromesome parent2){
		
	}
}
