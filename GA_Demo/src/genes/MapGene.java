package genes;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import logging.Logger;
import logging.Logger.Category;

public class MapGene<K extends Enum<K>, V extends Enum<V>> extends Gene<Map<K, V>> {

	private Map<K, V> valueMap;
	private Class<K> keyType;
	private Class<V> valueType;

	public MapGene(String name, Class<K> keyType, Class<V> valueType) {
		super(name);
		this.keyType = keyType;
		this.valueType = valueType;
		valueMap = new EnumMap<>(keyType);
		V[] values = valueType.getEnumConstants();
		K[] keys = keyType.getEnumConstants();

		Random rand = new Random();
		for (int i = 0; i < keys.length; i++) {
			valueMap.put(keys[i], values[rand.nextInt(values.length)]);
		}

		Logger.debug(toString(), Category.CHROMESOMES);
	}

	public MapGene(MapGene<K, V> mapGene) {
		super(mapGene.getName());
		keyType = mapGene.getKeyType();
		valueType = mapGene.getValueType();
		valueMap = new EnumMap<>(keyType);
		valueMap.putAll(mapGene.getValue());
	}

	public MapGene(MapGene<K, V> parent1, MapGene<K, V> parent2) {
		super(parent1.name);
		keyType = parent1.getKeyType();
		valueType = parent1.getValueType();
		valueMap = new EnumMap<>(keyType);
		Random rand = new Random();
		if (rand.nextBoolean()) {
			combineReactions(parent1, parent2);
		} else {
			combineReactions(parent2, parent1);
		}
	}

	private void combineReactions(MapGene<K, V> parent1, MapGene<K, V> parent2) {
		K[] tiles = keyType.getEnumConstants();
		valueMap = parent1.getValue();
		valueMap.put(tiles[0], parent2.getValue().get(tiles[0]));
		valueMap.put(tiles[1], parent2.getValue().get(tiles[1]));
	}

	public Class<K> getKeyType() {
		return keyType;
	}

	public Class<V> getValueType() {
		return valueType;
	}

	@Override
	public void mutate() {
		Random rand = new Random();
		V[] actions = valueType.getEnumConstants();
		K[] tiles = keyType.getEnumConstants();

		valueMap.put(tiles[rand.nextInt(tiles.length)], actions[rand.nextInt(actions.length)]);
	}

	@Override
	public Map<K, V> getValue() {
		return valueMap;
	}

	public V getSmallValue(K tile) {
		return valueMap.get(tile);
	}

	@Override
	public String toString() {
		StringBuilder outputString = new StringBuilder();
		outputString.append(name + ": ");
		for (Entry<K, V> entry : valueMap.entrySet()) {
			outputString.append(", [" + entry.getKey() + ", " + entry.getValue() + "]");
		}
		return outputString.toString();
	}
}
