
public class Jacoby {

    double ElementId;

    double [] dxdKsi = new double[4],dxdEta = new double[4],dydKsi = new double[4],dydEta = new double[4];
    double x1,x2,x3,x4;
    double y1,y2,y3,y4;
    double [][][] jacobies=new double[4][2][2];
    double [][][] jacobiesReversed=new double[4][2][2];
    double [] jacobiesDet=new double[4];
    double [][] dNdx=new double[4][4],dNdy=new double[4][4];

    double [] xArray = new double[4];
    double [] yArray = new double[4];
/*
    public Jacoby(double xE, double xN, double yE, double yN) {
        this.xKsi = xE;
        this.xEta = xN;
        this.yKsi = yE;
        this.yEta = yN;   }
*/
    public void countJacoby(UniversalElement uEL, Element EL) {
        for(int i=0 ; i<4; i++){
        	dxdKsi[i] = 0;
        	dxdEta[i] = 0;
        	dydKsi[i] = 0;
        	dydEta[i] = 0;
        }
        xArray[0] = EL.getNode(1).getX();
        xArray[1] = EL.getNode(2).getX();
        xArray[2] = EL.getNode(3).getX();
        xArray[3] = EL.getNode(4).getX();

        yArray[0] = EL.getNode(1).getY();
        yArray[1] = EL.getNode(2).getY();
        yArray[2] = EL.getNode(3).getY();
        yArray[3] = EL.getNode(4).getY();

        ElementId = EL.getId();
        for (int j = 0;j<4;j++ ){
        if(j == 0 ) {
            for (int i = 0; i < 4; i++) {
                dxdKsi[j] += uEL.dShapeFunctiondKsiMatrix[0][i] * xArray[i];
                dydKsi[j] += uEL.dShapeFunctiondKsiMatrix[0][i] * yArray[i];
                dxdEta[j] += uEL.dShapeFunctiondEtaMatrix[0][i] * xArray[i];
                dydEta[j] += uEL.dShapeFunctiondEtaMatrix[0][i] * yArray[i];
            }

        }

       else if(j == 1)
        {
            for (int i = 0; i < 4; i++) {
                dxdKsi[j] += uEL.dShapeFunctiondKsiMatrix[1][i] * xArray[i];
                dydKsi[j] += uEL.dShapeFunctiondKsiMatrix[1][i] * yArray[i];
                dxdEta[j] += uEL.dShapeFunctiondEtaMatrix[1][i] * xArray[i];
                dydEta[j] += uEL.dShapeFunctiondEtaMatrix[1][i] * yArray[i];
            }

        }

       else if(j == 2)
        {
            for (int i = 0; i < 4; i++) {
                dxdKsi[j] += uEL.dShapeFunctiondKsiMatrix[2][i] * xArray[i];
                dydKsi[j] += uEL.dShapeFunctiondKsiMatrix[2][i] * yArray[i];
                dxdEta[j] += uEL.dShapeFunctiondEtaMatrix[2][i] * xArray[i];
                dydEta[j] += uEL.dShapeFunctiondEtaMatrix[2][i] * yArray[i];
            }

        }

       else if(j == 3)
        {
            for (int i = 0; i < 4; i++) {
                dxdKsi[j] += uEL.dShapeFunctiondKsiMatrix[3][i] * xArray[i];
                dydKsi[j] += uEL.dShapeFunctiondKsiMatrix[3][i] * yArray[i];
                dxdEta[j] += uEL.dShapeFunctiondEtaMatrix[3][i] * xArray[i];
                dydEta[j] += uEL.dShapeFunctiondEtaMatrix[3][i] * yArray[i];
            }
            
        }
       }
        
       for (int j =0;j<4;j++){
        for (int i = 0;i<4;i++){
            if(i==0){
     	   	jacobies[j][0][0] = dxdKsi[i];
     	   	jacobiesReversed[j][1][1] = dxdKsi[i];
            }
            if(i==1){
    	   		jacobies[j][0][1] = dydKsi[i];
    	   		jacobiesReversed[j][0][1] = -dydKsi[i];
            }
            if(i==2){
    	   		jacobies[j][1][0] = dxdEta[i];
    	   		jacobiesReversed[j][1][0] = -dxdEta[i];
            }
            if(i==3){
    	   		jacobies[j][1][1] = dydEta[i];
    	   		jacobiesReversed[j][0][0] = dydEta[i];
            }
        	}
        
        	jacobiesDet[j]= (dxdKsi[j]* dydEta[j]) - (dydKsi[j]*dxdEta[j]);
         for(int i = 0; i < 4; i++){
                dNdx[i][j]=(jacobiesReversed[i][0][0]*uEL.dShapeFunctiondKsiMatrix[i][j]+jacobiesReversed[i][0][1]*uEL.dShapeFunctiondEtaMatrix[i][j])/jacobiesDet[i];
                dNdy[i][j]=(jacobiesReversed[i][1][0]*uEL.dShapeFunctiondKsiMatrix[i][j]+jacobiesReversed[i][1][1]*uEL.dShapeFunctiondEtaMatrix[i][j])/jacobiesDet[i];
         }
       }
        
    }

    public static void printJakobian(Jacoby J)
    {	
    	for(int i=0; i<4; i++){
    		System.out.print("Punkt Ca³kowania = "+ (i+1));
    		System.out.println(" ElementID = " +J.ElementId+ "\n" + J.jacobies[i][0][0] +"\t"+ J.jacobies[i][0][1] +"\n" + J.jacobies[i][1][0]+"\t" +J.jacobies[i][1][1]+"\n");
    	}
    }
    
}
