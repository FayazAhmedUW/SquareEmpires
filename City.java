
public class City {
	
	private int[] location = new int[2];
	private int owner;
	private boolean isPort;
	private boolean isCapital;
	
	public City(int[] loc, int ow, boolean iP, boolean iC){
		this.location = loc;
		this.owner = ow;
		this.isPort = iP;
		this.isCapital = iC;
	} 
	
	public int[] getLoc(){
		return this.location;
	}
	
	public int getOwner(){
		return this.owner;
	}
	
	public boolean getPort(){
		return this.isPort;
	}
	
	public boolean getCapital(){
		return this.isCapital;
	}
	
	public void setOwner (int ow){
		this.owner = ow;
	}
	
	public void setLoc (int[] loc){
		this.location = loc;
	}
	
	
}
