package Test;

public class Test {
	
	private int number;
	
	public Test() {
		this.number = 0;
	}
	
	public Test(int number) {
		this.number = number;
	}
	
	public int getNumber(){
		return this.number;
	}
	
	public boolean setNumber(int number) {
		this.number = number;
		return true;
	}
}
