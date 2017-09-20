package map;
public class TileMap {
	
	private TileTypes[][] tiles;
	
	public TileMap(int size){
		tiles = new TileTypes[size][size];
		
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if((x == 0 || x == size -1) || (y == 0 || y == size -1)){
					tiles[x][y] = TileTypes.WALL;
				} else {
					tiles[x][y] = TileTypes.EMPTY;
				}
			}
		}
	}
	
	public TileTypes tileFilled(int x, int y){
		return tiles[x][y];
	}
	
	public void changeTileState(int x, int y, TileTypes type){
		tiles[x][y] = type;
	}
	
	public TileTypes[][] getTiles(){
		return tiles;
	}
}
