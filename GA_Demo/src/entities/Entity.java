package entities;
import chromesomes.PriorityChromesome;
import chromesomes.ReactionChromesome;
import map.TileMap;

public class Entity {
	
	private int x;
	private int y;
	private PriorityChromesome pChromesome;
	private ReactionChromesome rChromesome;
	private int fitness;
	
	public Entity(int x, int y){
		this.x = x;
		this.y = y;
		pChromesome = new PriorityChromesome();
		rChromesome = new ReactionChromesome();
	}
	
	public Entity(Entity parent1, Entity parent2){
		
	}
	
	public void update(TileMap map){
		
	}
	
	public int getFitness(){
		return fitness;
	}
}
