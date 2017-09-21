package chromesomes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import map.TileType;

public class PriorityChromesome{
	
	private List<TileType> priorities;
	
	public PriorityChromesome(){
		priorities = new ArrayList<>();
		
		Random rand = new Random();
		TileType[] tiles = TileType.values();
		TileType temp;
		
		for(int i = 0; i < tiles.length; i++){
			do{
				temp = tiles[rand.nextInt(tiles.length)];
			} while (priorities.contains(temp));
			priorities.add(temp);
		}
	}
	
	public PriorityChromesome(PriorityChromesome parent1, PriorityChromesome parent2){
		
	}
	
	public void switchPriorities(){
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		TileType temp = priorities.get(index1);
		int index2;
		if(index1 == priorities.size() - 1){
			index2 = 0;
		} else {
			index2 = index1 + 1;
		}
		priorities.set(index1, priorities.get(index2));
		priorities.set(index2, temp);
	}
	
	public void switchRandomPriorities(){
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		int index2;
		do {
			index2 = rand.nextInt(4);
		} while (index2 == index1);
		TileType temp = priorities.get(index1);
		priorities.set(index1, priorities.get(index2));
		priorities.set(index2, temp);
	}
	
	public int getPriority(TileType tile){
		return priorities.indexOf(tile);
	}
}
