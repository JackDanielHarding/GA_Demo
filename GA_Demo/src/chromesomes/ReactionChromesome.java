package chromesomes;

import java.util.EnumMap;
import java.util.Map;

import actions.Actions;
import map.TileTypes;

public class ReactionChromesome {
	
	private Map<TileTypes, Actions> reactions;
	
	public ReactionChromesome(){
		reactions = new EnumMap<>(TileTypes.class);
	}
	
	public ReactionChromesome(ReactionChromesome parent1, ReactionChromesome parent2){
		
	}
}
