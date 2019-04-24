public class UniversalElement {
	
	public double[][] dShapeFunctiondKsiMatrix = new double[4][4];
	public double[][] dShapeFunctiondEtaMatrix = new double[4][4];
	public double[][] shapeFunctionMatrix = new double[4][4];
	public double shapeFunction;
	double [][][] shapeFunctiononPoint = new double[4][2][4];
	
	double ksi;
	double eta;
	
	public UniversalElement()
	{
		fillMatrix();
	}
	
	void fillMatrix()
	{
		ksi=0;
		eta=0;

		for(int i =0; i< 4; i++)
		{
			if(i ==0)
			{
				ksi= -1.0/(Math.sqrt(3.0));
				eta = -1.0/(Math.sqrt(3.0));
			}
			else if(i==1)
			{
				ksi = 1.0/(Math.sqrt(3.0));
				eta = -1.0/(Math.sqrt(3.0));
			}
			else if(i==2)
			{
				ksi = 1.0/(Math.sqrt(3.0));
				eta = 1.0/(Math.sqrt(3.0));
			}
			else if(i==3)
			{
				ksi = -1.0/(Math.sqrt(3.0));
				eta = 1.0/(Math.sqrt(3.0));
			}
			
			shapeFunctionMatrix[i][0] = 0.25 * (1-ksi) * (1-eta);
			shapeFunctionMatrix[i][1] = 0.25 * (1+ksi) * (1-eta);
			shapeFunctionMatrix[i][2] = 0.25 * (1+ksi) * (1+eta);
			shapeFunctionMatrix[i][3] = 0.25 * (1-ksi) * (1+eta);
			
			dShapeFunctiondKsiMatrix[i][0] = -0.25 * (1-eta);
			dShapeFunctiondKsiMatrix[i][1] = 0.25 * (1-eta);
			dShapeFunctiondKsiMatrix[i][2] = 0.25 * (1+eta);
			dShapeFunctiondKsiMatrix[i][3] = -0.25 * (1+eta);

			dShapeFunctiondEtaMatrix[i][0] = -0.25 * (1-ksi);
			dShapeFunctiondEtaMatrix[i][1] = -0.25 * (1+ksi);
			dShapeFunctiondEtaMatrix[i][2] = 0.25 * (1+ksi);
			dShapeFunctiondEtaMatrix[i][3] = 0.25 * (1-ksi);
			
			fillShapeFunctiononPoint(i);
			
		}
	}
	
	double shapeFunction(int functionNumber, double ksi, double eta){
		if(functionNumber ==1)
		{
			shapeFunction = 0.25 * (1-ksi) * (1-eta);
		}
		else if(functionNumber==2)
		{
			shapeFunction = 0.25 * (1+ksi) * (1-eta);
		}
		else if(functionNumber==3)
		{
			shapeFunction = 0.25 * (1+ksi) * (1+eta);
		}
		else if(functionNumber==4)
		{
			shapeFunction = 0.25 * (1-ksi) * (1+eta);
		}
		
		return shapeFunction;
	}
	
    public void fillShapeFunctiononPoint(int j){
    	 	if (j==0){
    		for (int i = 0;i<4;i++){
    			shapeFunctiononPoint[0][1][i] = shapeFunction((i+1),-1.0, eta);
    			shapeFunctiononPoint[1][0][i] = shapeFunction((i+1),ksi,-1.0);
    		}
    	}	else if (j==1){
    		for (int i = 0;i<4;i++){
    			shapeFunctiononPoint[2][0][i] = shapeFunction((i+1),1.0, eta);
    			shapeFunctiononPoint[1][1][i] = shapeFunction((i+1),ksi,-1.0);
    		}
    	}else if (j==2){
    		for (int i = 0;i<4;i++){
    			shapeFunctiononPoint[2][1][i] = shapeFunction((i+1),1.0, eta);
    			shapeFunctiononPoint[3][0][i] = shapeFunction((i+1),ksi,1.0);
    		}
    	}else if (j==3){
    		for (int i = 0;i<4;i++){
    			shapeFunctiononPoint[0][0][i] = shapeFunction((i+1),-1.0, eta);
    			shapeFunctiononPoint[3][1][i] = shapeFunction((i+1),ksi,1.0);
    		}
    	}
    }

    
}
