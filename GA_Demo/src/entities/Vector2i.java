package entities;

public class Vector2i {
	private int x;
	private int y;
	
	public Vector2i(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2i flip(){
		x = x * -1;
		y = y * -1;
		return this;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
