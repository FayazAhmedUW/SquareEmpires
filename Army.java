
public class Army {
	private int manpower;
	private int morale;
	private int location[] = new int[2];
	private boolean isOnBoat;
	
	public Army(int mp, int mo, int[] loc, boolean iob){
		this.manpower = mp;
		this.morale = mo;
		this.location = loc;
		this.isOnBoat = iob;
	}
	
	public void changeManpower (int mp){
		this.manpower = this.manpower + mp;
	}
	
	public void changeMorale (int mo){
		this.morale = this.morale + mo;
	}
	
	public void move (int[] loc){
		this.location[0] = loc[0];
		this.location[1] = loc[1];
	}
	
	public void setOnBoat (boolean iob){
		this.isOnBoat = iob;
	}
	
	public int getManpower(){
		return this.manpower;
	}
	
	public int getMorale(){
		return this.morale;
	}
	
	
	public int[] getLoc(){
		return this.location;
	}
	
	public boolean getOnBoat(){
		return this.isOnBoat;
	}

}
