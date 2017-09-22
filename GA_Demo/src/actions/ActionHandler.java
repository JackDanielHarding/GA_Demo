package actions;

import entities.Vector2i;

public class ActionHandler {

	public static Vector2i useAction(Action action, Vector2i direction){
		switch(action){
		case RANDOM:
		case TOWARDS:
			return direction;
		case AWAY:
			return direction.flip();
		default:
			return new Vector2i(0, 0);
		}
	}
}