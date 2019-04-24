
public class Main {
	
	public static void main(String[] args) {
		
		GlobalData gd = new GlobalData();
		Grid grid = new Grid(gd);
		
		gd.printData();
		grid.printElements();
		grid.printNodes();

		UniversalElement uEL = new UniversalElement();
		Jacoby J = new Jacoby();
		for(int i =0; i< grid.EL.length; i++) {
		J.countJacoby(uEL,grid.EL[i]);
		Jacoby.printJakobian(J);
		}
		
		HP hp = new HP(grid,gd,J);
		hp.loopTime();
	}

}
