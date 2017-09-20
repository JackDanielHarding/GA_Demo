package entities;
import chromesomes.PriorityChromesome;
import chromesomes.ReactionChromesome;

public class Entity {
	
	int x;
	int y;
	PriorityChromesome pChromesome;
	ReactionChromesome rChromesome;
	
	public Entity(int x, int y){
		this.x = x;
		this.y = y;
		pChromesome = new PriorityChromesome();
		rChromesome = new ReactionChromesome();
	}
	
	public Entity(Entity parent1, Entity parent2){
		
	}
}
