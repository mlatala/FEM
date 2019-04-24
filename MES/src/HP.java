
public class HP {

    double[][] HGlobalMatrix;
    double[][] HLocalMatrix;
    double[] PGlobalMatrix;
    double[] PLocalMatrix;
    double[] temperatureGlobal;
    double temperature;
    double[][] CMatrix;
    
    Jacoby J;
    UniversalElement uEL;
    Node[] node;
    Element elem;
    Grid grid;
    GlobalData data;
    
    public HP(Grid grid, GlobalData data, Jacoby J){
        HGlobalMatrix = new double[data.getnn()][data.getnn()];
        HLocalMatrix = new double[4][4];
        PGlobalMatrix = new double[data.getnn()];
        PLocalMatrix = new double[4];
        temperatureGlobal = new double[data.getnn()];
        temperature = 0;
        CMatrix = new double[4][4];
        
        this.grid = grid;
        this.data = data;        
		uEL = new UniversalElement();
		this.J =J;
        node = new Node[4];
        elem = new Element();
        
        
    }
    
    private void fillLocal(){
        for(int i = 0; i < 4; i++){
            temperature = 0;
            for(int j = 0; j < 4; j++){
                temperature += uEL.shapeFunctionMatrix[i][j]*node[j].getTemperature();
                
            }
            for(int j = 0; j < 4; j++){
                for(int n = 0; n < 4; n++){
                	CMatrix[i][n] = uEL.shapeFunctionMatrix[i][j]*uEL.shapeFunctionMatrix[i][n]*J.jacobiesDet[i]*data.c*data.r;                	
                	HLocalMatrix[j][n] += (((J.dNdx[i][j]*J.dNdx[i][n])+(J.dNdy[i][j]*J.dNdy[i][n]))*data.k*J.jacobiesDet[i])+CMatrix[i][n]/data.timeStep;                    
                	PLocalMatrix[j] +=CMatrix[i][n]/data.timeStep*temperature;
                }
            }
        }


        double jacobyDetPoint = 0;
        for(int i = 0 ; i < 4; i++){
            if(elem.getSurface_status(i+1) == 2 || elem.getSurface_status(i+1) == 3 || elem.getSurface_status(i+1) == 4){ 
            	if(i==0) 
                	jacobyDetPoint = Math.sqrt(Math.pow(node[0].getX()-node[3].getX(),2)+Math.pow(node[0].getY()-node[3].getY(),2))/2.0;
                if(i==1) 
                	jacobyDetPoint = Math.sqrt(Math.pow(node[1].getX()-node[0].getX(),2)+Math.pow(node[1].getY()-node[0].getY(),2))/2.0;
                if(i==2) 
                	jacobyDetPoint = Math.sqrt(Math.pow(node[2].getX()-node[1].getX(),2)+Math.pow(node[2].getY()-node[1].getY(),2))/2.0;
                if(i==3) 
                	jacobyDetPoint = Math.sqrt(Math.pow(node[3].getX()-node[2].getX(),2)+Math.pow(node[3].getY()-node[2].getY(),2))/2.0;
               
                for(int j = 0; j < 2; j++){
                    for(int k = 0; k < 4; k++){
                        for(int l = 0; l < 4; l++){
                            HLocalMatrix[k][l] += data.alfa*jacobyDetPoint*uEL.shapeFunctiononPoint[i][j][k]*uEL.shapeFunctiononPoint[i][j][l];                           
                        }
                        PLocalMatrix[k] += data.alfa*data.t_otoczenia*jacobyDetPoint*uEL.shapeFunctiononPoint[i][j][k];
                    }
                }
            }else if(elem.getSurface_status(i+1) == 1){ 
            	if(i==0) 
                	jacobyDetPoint = Math.sqrt(Math.pow(node[0].getX()-node[3].getX(),2)+Math.pow(node[0].getY()-node[3].getY(),2))/2.0;
                if(i==1) 
                	jacobyDetPoint = Math.sqrt(Math.pow(node[1].getX()-node[0].getX(),2)+Math.pow(node[1].getY()-node[0].getY(),2))/2.0;
                if(i==2) 
                	jacobyDetPoint = Math.sqrt(Math.pow(node[2].getX()-node[1].getX(),2)+Math.pow(node[2].getY()-node[1].getY(),2))/2.0;
                if(i==3) 
                	jacobyDetPoint = Math.sqrt(Math.pow(node[3].getX()-node[2].getX(),2)+Math.pow(node[3].getY()-node[2].getY(),2))/2.0;
               
                for(int j = 0; j < 2; j++){
                    for(int k = 0; k < 4; k++){
                        for(int l = 0; l < 4; l++){
                            HLocalMatrix[k][l] += data.alfa*jacobyDetPoint*uEL.shapeFunctiononPoint[i][j][k]*uEL.shapeFunctiononPoint[i][j][l];                           
                        }
                        PLocalMatrix[k] += data.alfa*data.t_otoczenia_2*jacobyDetPoint*uEL.shapeFunctiononPoint[i][j][k];
                    }
                }
            }
        }
       
    }
    
    void elementLoop(){
    	for (int i = 0; i < grid.EL.length; i++){
    	
    		for(int x = 0; x < 4; x++){
                for(int  y = 0; y < 4; y++){
                    HLocalMatrix[x][y] = 0;
                }
            }
            for(int x = 0; x < 4; x++){
                PLocalMatrix[x] = 0;
            }
            for(int x = 0; x < 4; x++) {
                node[x] = grid.EL[i].getNode(x+1);
                elem = grid.EL[i];
            }

            fillLocal();
            for(int x = 0; x < 4; x++){
                for(int y = 0; y < 4; y++){
                    HGlobalMatrix[grid.EL[i].getSurface_id(x+1)-1][grid.EL[i].getSurface_id(y+1)-1] += HLocalMatrix[x][y];
                }
                PGlobalMatrix[grid.EL[i].getSurface_id(x+1)-1] += PLocalMatrix[x];
            }
        }
    	
    }
    void loopTime(){
        for(int i=0; i < data.time; i+=data.timeStep) {        	
        	for(int x = 0; x < data.getnn(); x++){
                for(int y = 0; y < data.getnn(); y++){
                    HGlobalMatrix[x][y] = 0;
                }
            }
            for(int x = 0; x < data.getnn(); x++){
                PGlobalMatrix[x] = 0;
                temperatureGlobal[x] = 0;
            }
               elementLoop();
               Gauss gauss = new Gauss(HGlobalMatrix, PGlobalMatrix, data.getnn());
               temperatureGlobal = gauss.gaussElimination();
               
               for (int x = 0; x < grid.EL.length; x++){
            	   for(int y=0; y<4; y++)
           			grid.EL[x].getNode(y+1).setTemperature(temperatureGlobal[grid.EL[x].getNode(y+1).getId()-1]);;
           	   }
               double max = temperatureGlobal[0];
               double min = temperatureGlobal[0];
               for(int j = 1; j < data.getnn(); j++){
                   if(temperatureGlobal[j] > max) max = temperatureGlobal[j];
                   if(temperatureGlobal[j] < min) min = temperatureGlobal[j];
               }
               show((int) (i+data.timeStep), min, max);
           }
    }
    public void show(int time, double min, double max){
        System.out.println("\n__________________________________Time: " + time+"__________________________________");
        System.out.println("__________________________________Global Matrix H__________________________________");
        for(int i = 0; i < data.getnn(); i++){
            for(int j = 0; j < data.getnn(); j++){
                System.out.print(HGlobalMatrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("\n__________________________________Global Vector P__________________________________");

        for(int i = 0; i < data.getnn(); i++){
            System.out.print(PGlobalMatrix[i]+" ");
        }
        System.out.println("\n\n__________________________________Global Vector T__________________________________");
        for(int i = 0; i < data.getnn(); i++){
            System.out.print(temperatureGlobal[i]+" ");
        }

        System.out.println("\n\nMin: "+min+ "\tMax: "+max);

    }

}
