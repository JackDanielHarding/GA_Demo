
public class Map {
	
	private boolean[][] tiles;
	
	public Map(int size){
		tiles = new boolean[size][size];
	}
	
	public boolean tileFilled(int x, int y){
		return tiles[x][y];
	}
	
	public void changeTileState(int x, int y, boolean state){
		tiles[x][y] = state;
	}
	
	public boolean[][] getTiles(){
		return tiles;
	}
}
