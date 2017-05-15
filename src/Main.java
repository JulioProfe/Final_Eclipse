import processing.core.PApplet;

public class Main extends PApplet {
	
	private Logica log;

	public static void main(String[] args) {
		PApplet.main("Main");

	}
	
	public void settings() {
		size(1200,700);
		// TODO Auto-generated method stub

	}
	
	@Override
	public void setup() {
		log = new Logica(this);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw() {
		background(255);
		// TODO Auto-generated method stub
		
	}
	

}