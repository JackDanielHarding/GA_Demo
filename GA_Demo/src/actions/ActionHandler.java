package actions;

import org.joml.Vector2i;

public abstract class ActionHandler {

	public static Vector2i useAction(Action action, Vector2i direction) {
		switch (action) {
		case RANDOM:
		case TOWARDS:
			return direction;
		case AWAY:
			return direction.negate();
		default:
			return new Vector2i(0, 0);
		}
	}
}
