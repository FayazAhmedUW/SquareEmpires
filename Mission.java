
public class Mission {
	private int type; //0 - capture city, 1 - attacking capital, 2 - gather resources, 3 - movement; 
	private int weight;
	private int[] target = new int[2];
	//418,498
	Mission (int type, int weight, int[] target){
		this.type = type;
		this.weight = weight;
		this.target = target;
	}

}
