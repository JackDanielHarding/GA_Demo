package chromesomes;
import java.util.Random;

import map.TileTypes;

public class PriorityChromesome implements Chromesome{
	
	private TileTypes[] values;
	private static final int NUM_VALUES = 4;
	
	public PriorityChromesome(){
		values = new TileTypes[NUM_VALUES];
	}
	
	public void switchPriorities(){
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		TileTypes temp = values[index1];
		int index2;
		if(index1 == values.length - 1){
			index2 = 0;
		} else {
			index2 = index1 + 1;
		}
		values[index1] = values[index2];
		values[index2] = temp;
	}
	
	public void switchRandomPriorities(){
		Random rand = new Random();
		int index1 = rand.nextInt(4);
		int index2;
		do {
			index2 = rand.nextInt(4);
		} while (index2 == index1);
		TileTypes temp = values[index1];
		values[index1] = values[index2];
		values[index2] = temp;
	}
}
